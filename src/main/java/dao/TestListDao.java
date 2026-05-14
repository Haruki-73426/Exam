package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.TestListStudent;
import bean.TestListSubject;

public class TestListDao extends Dao {

    public List<TestListSubject> filterBySubject(int entYear, String classNum, Subject subject, School school)
            throws Exception {

        List<TestListSubject> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(
                "select "
                + "s.ent_year, "
                + "s.class_num, "
                + "s.no as student_no, "
                + "s.name as student_name, "
                + "t1.point as point1, "
                + "t2.point as point2 "
                + "from student s "
                + "left join test t1 on "
                + "s.no = t1.student_no "
                + "and t1.school_cd = ? "
                + "and t1.subject_cd = ? "
                + "and t1.no = 1 "
                + "left join test t2 on "
                + "s.no = t2.student_no "
                + "and t2.school_cd = ? "
                + "and t2.subject_cd = ? "
                + "and t2.no = 2 "
                + "where s.school_cd = ? "
                + "and s.ent_year = ? "
                + "and s.class_num = ? "
                + "and s.is_attend = true "
                + "order by s.no asc"
            );

            statement.setString(1, school.getCd());
            statement.setString(2, subject.getCd());
            statement.setString(3, school.getCd());
            statement.setString(4, subject.getCd());
            statement.setString(5, school.getCd());
            statement.setInt(6, entYear);
            statement.setString(7, classNum);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                TestListSubject test = new TestListSubject();

                test.setEntYear(resultSet.getInt("ent_year"));
                test.setClassNum(resultSet.getString("class_num"));
                test.setStudentNo(resultSet.getString("student_no"));
                test.setStudentName(resultSet.getString("student_name"));

                Object point1 = resultSet.getObject("point1");
                Object point2 = resultSet.getObject("point2");

                if (point1 != null) {
                    test.setPoint1(resultSet.getInt("point1"));
                }

                if (point2 != null) {
                    test.setPoint2(resultSet.getInt("point2"));
                }

                list.add(test);
            }

        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw e;
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw e;
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

        return list;
    }

    public List<TestListStudent> filterByStudent(Student student, School school) throws Exception {

        List<TestListStudent> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(
                "select "
                + "sub.cd as subject_cd, "
                + "sub.name as subject_name, "
                + "t.no as test_no, "
                + "t.point as point "
                + "from test t "
                + "join subject sub on "
                + "t.subject_cd = sub.cd "
                + "and t.school_cd = sub.school_cd "
                + "where t.student_no = ? "
                + "and t.school_cd = ? "
                + "order by sub.cd asc, t.no asc"
            );

            statement.setString(1, student.getNo());
            statement.setString(2, school.getCd());

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                TestListStudent test = new TestListStudent();

                test.setSubjectCd(resultSet.getString("subject_cd"));
                test.setSubjectName(resultSet.getString("subject_name"));
                test.setNo(resultSet.getInt("test_no"));
                test.setPoint(resultSet.getInt("point"));

                list.add(test);
            }

        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw e;
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw e;
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

        return list;
    }
}

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
import bean.Test;
import dao.Dao;

public class TestDao extends Dao {

    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Test test = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(
                "select * from test where student_no = ? and subject_cd = ? and school_cd = ? and no = ?"
            );

            statement.setString(1, student.getNo());
            statement.setString(2, subject.getCd());
            statement.setString(3, school.getCd());
            statement.setInt(4, no);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                test = new Test();
                test.setStudent(student);
                test.setSubject(subject);
                test.setSchool(school);
                test.setNo(resultSet.getInt("no"));
                test.setPoint(resultSet.getInt("point"));
                test.setClassNum(resultSet.getString("class_num"));
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        return test;
    }

    public List<Test> filter(int entYear, String classNum, Subject subject, int no, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(
                "select "
                + "student.no as student_no, "
                + "student.name as student_name, "
                + "student.ent_year as ent_year, "
                + "student.class_num as class_num, "
                + "test.point as point "
                + "from student "
                + "left join test on "
                + "student.no = test.student_no "
                + "and test.subject_cd = ? "
                + "and test.school_cd = ? "
                + "and test.no = ? "
                + "where student.school_cd = ? "
                + "and student.ent_year = ? "
                + "and student.class_num = ? "
                + "and student.is_attend = true "
                + "order by student.no asc"
            );

            statement.setString(1, subject.getCd());
            statement.setString(2, school.getCd());
            statement.setInt(3, no);
            statement.setString(4, school.getCd());
            statement.setInt(5, entYear);
            statement.setString(6, classNum);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Student student = new Student();
                student.setNo(resultSet.getString("student_no"));
                student.setName(resultSet.getString("student_name"));
                student.setEntYear(resultSet.getInt("ent_year"));
                student.setClassNum(resultSet.getString("class_num"));
                student.setAttend(true);
                student.setSchool(school);

                Test test = new Test();
                test.setStudent(student);
                test.setSubject(subject);
                test.setSchool(school);
                test.setNo(no);
                test.setClassNum(resultSet.getString("class_num"));
                test.setPoint(resultSet.getInt("point"));

                list.add(test);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            // (以下、getメソッドと同様のクローズ処理)
            if (resultSet != null) try { resultSet.close(); } catch (SQLException sqle) { throw sqle; }
            if (statement != null) try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            if (connection != null) try { connection.close(); } catch (SQLException sqle) { throw sqle; }
        }

        return list;
    }

    private boolean exists(Test test, Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(
                "select count(*) as cnt from test where student_no = ? and subject_cd = ? and school_cd = ? and no = ?"
            );

            statement.setString(1, test.getStudent().getNo());
            statement.setString(2, test.getSubject().getCd());
            statement.setString(3, test.getSchool().getCd());
            statement.setInt(4, test.getNo());

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("cnt") > 0;
            }
            return false;

        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        }
    }

    public boolean save(Test test, Connection connection) throws Exception {
        PreparedStatement statement = null;
        int count = 0;

        try {
            if (exists(test, connection)) {
                statement = connection.prepareStatement(
                    "update test set point = ?, class_num = ? where student_no = ? and subject_cd = ? and school_cd = ? and no = ?"
                );

                statement.setInt(1, test.getPoint());
                statement.setString(2, test.getClassNum());
                statement.setString(3, test.getStudent().getNo());
                statement.setString(4, test.getSubject().getCd());
                statement.setString(5, test.getSchool().getCd());
                statement.setInt(6, test.getNo());
            } else {
                statement = connection.prepareStatement(
                    "insert into test(student_no, subject_cd, school_cd, no, point, class_num) values(?, ?, ?, ?, ?, ?)"
                );

                statement.setString(1, test.getStudent().getNo());
                statement.setString(2, test.getSubject().getCd());
                statement.setString(3, test.getSchool().getCd());
                statement.setInt(4, test.getNo());
                statement.setInt(5, test.getPoint());
                statement.setString(6, test.getClassNum());
            }

            count = statement.executeUpdate();

        } finally {
            if (statement != null) statement.close();
        }

        return count > 0;
    }

    public boolean save(List<Test> list) throws Exception {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);

            for (Test test : list) {
                save(test, connection);
            }

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return true;
    }
}
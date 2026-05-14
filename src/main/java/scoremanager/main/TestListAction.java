package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        StudentDao studentDao = new StudentDao();
        TestListDao testListDao = new TestListDao();

        Map<String, String> errors = new HashMap<>();

        List<String> classNumSet = classNumDao.filter(teacher.getSchool());
        List<Subject> subjectSet = subjectDao.filter(teacher.getSchool());

        List<Integer> entYearSet = new ArrayList<>();
        int year = LocalDate.now().getYear();

        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        String f1 = req.getParameter("f1");
        String f2 = req.getParameter("f2");
        String f3 = req.getParameter("f3");
        String f4 = req.getParameter("f4");

        if (f1 != null || f2 != null || f3 != null) {
            if (f1 == null || f1.equals("0") || f2 == null || f2.equals("0") || f3 == null || f3.equals("0")) {
                errors.put("subject", "入学年度とクラスと科目を選択してください");
            } else {
                int entYear = Integer.parseInt(f1);
                Subject subject = subjectDao.get(f3, teacher.getSchool());

                List<TestListSubject> subjectResults =
                    testListDao.filterBySubject(entYear, f2, subject, teacher.getSchool());

                req.setAttribute("subject_results", subjectResults);
                req.setAttribute("subject", subject);
                req.setAttribute("search_type", "subject");
            }
        }

        if (f4 != null) {
            if (f4.isEmpty()) {
                errors.put("student", "学生番号を入力してください");
            } else {
                Student student = studentDao.get(f4);

                if (student == null || !student.getSchool().getCd().equals(teacher.getSchool().getCd())) {
                    errors.put("student", "学生が存在しません");
                } else {
                    List<TestListStudent> studentResults =
                        testListDao.filterByStudent(student, teacher.getSchool());

                    req.setAttribute("student_results", studentResults);
                    req.setAttribute("student", student);
                    req.setAttribute("search_type", "student");
                }
            }
        }

        req.setAttribute("errors", errors);
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumSet);
        req.setAttribute("subject_set", subjectSet);

        req.setAttribute("f1", f1);
        req.setAttribute("f2", f2);
        req.setAttribute("f3", f3);
        req.setAttribute("f4", f4);

        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}
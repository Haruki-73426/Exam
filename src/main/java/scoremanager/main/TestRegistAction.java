package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TestDao.TestDao;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        TestDao testDao = new TestDao();

        Map<String, String> errors = new HashMap<>();

        List<String> classNumSet = classNumDao.filter(teacher.getSchool());
        List<Subject> subjectSet = subjectDao.filter(teacher.getSchool());

        List<Integer> entYearSet = new ArrayList<>();
        int year = LocalDate.now().getYear();

        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");
        String noStr = req.getParameter("f4");

        int entYear = 0;
        int no = 0;

        List<Test> tests = null;

        if (entYearStr != null) {

            if (entYearStr.equals("0")) {
                errors.put("f1", "入学年度を選択してください");
            } else {
                entYear = Integer.parseInt(entYearStr);
            }

            if (classNum == null || classNum.equals("0")) {
                errors.put("f2", "クラスを選択してください");
            }

            if (subjectCd == null || subjectCd.equals("0")) {
                errors.put("f3", "科目を選択してください");
            }

            if (noStr == null || noStr.equals("0")) {
                errors.put("f4", "回数を選択してください");
            } else {
                no = Integer.parseInt(noStr);
            }

            if (errors.isEmpty()) {
                Subject subject = subjectDao.get(subjectCd, teacher.getSchool());
                tests = testDao.filter(entYear, classNum, subject, no, teacher.getSchool());

                req.setAttribute("tests", tests);
                req.setAttribute("subjectName", subject.getName());
            }
        }

        req.setAttribute("errors", errors);
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumSet);
        req.setAttribute("subject_set", subjectSet);

        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", no);

        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
} 
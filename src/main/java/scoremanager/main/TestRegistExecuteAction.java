package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		String classNum = req.getParameter("f2");
		String subjectCd = req.getParameter("f3");
		String noStr = req.getParameter("f4");

		int no = Integer.parseInt(noStr);

		String[] studentNos = req.getParameterValues("student_no");

		StudentDao studentDao = new StudentDao();
		SubjectDao subjectDao = new SubjectDao();
		TestDao testDao = new TestDao();

		Subject subject = subjectDao.get(subjectCd, teacher.getSchool());

		List<Test> tests = new ArrayList<>();

		if (studentNos != null) {
			for (String studentNo : studentNos) {

				String pointStr = req.getParameter("point_" + studentNo);

				if (pointStr == null || pointStr.isEmpty()) {
					continue;
				}

				int point = Integer.parseInt(pointStr);

				if (point < 0 || point > 100) {
					req.setAttribute("message", "点数は0〜100の範囲で入力してください");
					req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
					return;
				}

				Student student = studentDao.get(studentNo);

				Test test = new Test();
				test.setStudent(student);
				test.setSubject(subject);
				test.setSchool(teacher.getSchool());
				test.setNo(no);
				test.setPoint(point);
				test.setClassNum(classNum);

				tests.add(test);
			}
		}

		testDao.save(tests);

		req.setAttribute("message", "登録が完了しました");
		req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
	}
}
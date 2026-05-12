package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

<<<<<<< HEAD
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
=======
>>>>>>> branch 'suzuki' of https://github.com/Haruki-73426/Exam.git
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

<<<<<<< HEAD
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String cd = req.getParameter("cd");
        String name = req.getParameter("name");
=======
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
>>>>>>> branch 'suzuki' of https://github.com/Haruki-73426/Exam.git

<<<<<<< HEAD
        Map<String, String> errors = new HashMap<>();
=======
		String cd = req.getParameter("cd");
		String name = req.getParameter("name");
>>>>>>> branch 'suzuki' of https://github.com/Haruki-73426/Exam.git

        if (name == null || name.isEmpty()) {
            errors.put("name", "科目名を入力してください");
        }

<<<<<<< HEAD
        if (errors.isEmpty()) {
=======
		if (name == null || name.isEmpty()) {
			errors.put("name", "科目名を入力してください");
		}
>>>>>>> branch 'suzuki' of https://github.com/Haruki-73426/Exam.git

<<<<<<< HEAD
            Subject subject = new Subject();

            subject.setCd(cd);
            subject.setName(name);
            subject.setSchool(teacher.getSchool());
=======
		if (errors.isEmpty()) {
			Subject subject = new Subject();
			subject.setCd(cd);
			subject.setName(name);
			subject.setSchool(teacher.getSchool());
>>>>>>> branch 'suzuki' of https://github.com/Haruki-73426/Exam.git

<<<<<<< HEAD
            SubjectDao subjectDao = new SubjectDao();
            subjectDao.save(subject);

            req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
=======
			SubjectDao subjectDao = new SubjectDao();
			subjectDao.save(subject);
>>>>>>> branch 'suzuki' of https://github.com/Haruki-73426/Exam.git

<<<<<<< HEAD
        } else {

            req.setAttribute("errors", errors);
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);

            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
        }
    }
=======
			req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
		} else {
			req.setAttribute("errors", errors);
			req.setAttribute("cd", cd);
			req.setAttribute("name", name);

			req.getRequestDispatcher("subject_update.jsp").forward(req, res);
		}
	}
>>>>>>> branch 'suzuki' of https://github.com/Haruki-73426/Exam.git
}
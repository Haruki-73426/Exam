package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
<<<<<<< HEAD
import tool.Action;

public class SubjectCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        req.getRequestDispatcher("subject_create.jsp").forward(req, res);

    }
}
=======
import jakarta.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action {

@Override
  public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

    HttpSession session = req.getSession();
    Teacher teacher = (Teacher) session.getAttribute("user");

    String cd = req.getParameter("cd");

    SubjectDao subjectDao = new SubjectDao();
    Subject subject = subjectDao.get(cd, teacher.getSchool());

    if (subject == null) {
      req.getRequestDispatcher("SubjectList.action").forward(req, res);
      return;
    }

    req.setAttribute("cd", subject.getCd());
    req.setAttribute("name", subject.getName());

    req.getRequestDispatcher("subject_update.jsp").forward(req, res);
  }
}
>>>>>>> branch 'suzuki' of https://github.com/Haruki-73426/Exam.git

package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteAction extends Action {

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

    req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
  }
}
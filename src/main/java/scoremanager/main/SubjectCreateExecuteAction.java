package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

  @Override
  public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

    HttpSession session = req.getSession();
    Teacher teacher = (Teacher) session.getAttribute("user");

    String cd = req.getParameter("cd");
    String name = req.getParameter("name");

    SubjectDao subjectDao = new SubjectDao();
    Map<String, String> errors = new HashMap<>();

    if (cd == null || cd.isEmpty()) {
      errors.put("cd", "科目コードを入力してください");
    } else if (cd.length() != 3) {
      errors.put("cd", "科目コードは3文字で入力してください");
    } else if (subjectDao.get(cd, teacher.getSchool()) != null) {
      errors.put("cd", "科目コードが重複しています");
    }

    if (name == null || name.isEmpty()) {
      errors.put("name", "科目名を入力してください");
    }

    if (errors.isEmpty()) {
      Subject subject = new Subject();
      subject.setCd(cd);
      subject.setName(name);
      subject.setSchool(teacher.getSchool());

      subjectDao.save(subject);

      req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
    } else {
      req.setAttribute("errors", errors);
      req.setAttribute("cd", cd);
      req.setAttribute("name", name);

      req.getRequestDispatcher("subject_create.jsp").forward(req, res);
    }
  }
}

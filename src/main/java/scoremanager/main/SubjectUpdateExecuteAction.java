package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Subject;

import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();

		// ログイン中の先生情報を取得
		Teacher teacher = (Teacher) session.getAttribute("user");

		// JSPから送られてきた値を取得
		String cd = req.getParameter("cd");
		String name = req.getParameter("name");

		Map<String, String> errors = new HashMap<>();

		// 科目名の入力チェック
		if (name == null || name.isEmpty()) {
			errors.put("name", "科目名を入力してください");
		}

		// エラーがない場合
		if (errors.isEmpty()) {

			Subject subject = new Subject();
			subject.setCd(cd);
			subject.setName(name);
			subject.setSchool(teacher.getSchool());

			SubjectDao subjectDao = new SubjectDao();

			// saveは、既に存在する科目ならupdateしてくれる想定
			subjectDao.save(subject);

			req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);

		} else {

			// エラーがある場合は、入力画面に戻す
			req.setAttribute("errors", errors);
			req.setAttribute("cd", cd);
			req.setAttribute("name", name);

			req.getRequestDispatcher("subject_update.jsp").forward(req, res);
		}
	}
}
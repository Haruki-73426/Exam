package scoremanager.main;

import javax.security.auth.Subject;

import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();

		// ログイン中の先生情報を取得
		Teacher teacher = (Teacher) session.getAttribute("user");

		// 一覧画面から送られてきた科目コードを取得
		String cd = req.getParameter("cd");

		SubjectDao subjectDao = new SubjectDao();

		// 科目コードと学校情報で、変更対象の科目を取得
		Subject subject = subjectDao.get(cd, teacher.getSchool());

		// 科目が見つからなかった場合は一覧へ戻す
		if (subject == null) {
			req.getRequestDispatcher("SubjectList.action").forward(req, res);
			return;
		}

		// JSPで使う値をセット
		req.setAttribute("cd", subject.getCd());
		req.setAttribute("name", subject.getName());

		// 変更画面へ
		req.getRequestDispatcher("subject_update.jsp").forward(req, res);
	}
}
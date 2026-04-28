<%-- 科目変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section>
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>

			<form action="SubjectUpdateExecute.action" method="get">
				<div class="mx-auto py-2">
					<label for="cd">科目コード</label><br>
					<input class="border border-0 ps-3" type="text" id="cd"
						name="cd" value="${cd}" readonly />
				</div>

				<div class="mx-auto py-2">
					<label for="name">科目名</label><br>
					<input class="form-control" type="text" id="name"
						name="name" value="${name}" maxlength="20" required />
				</div>

				<div class="mt-2 text-warning">${errors.get("name")}</div>

				<div class="mx-auto py-2">
					<input class="btn btn-primary" type="submit" value="変更" />
				</div>
			</form>

			<a href="SubjectList.action">戻る</a>
		</section>
	</c:param>
</c:import>
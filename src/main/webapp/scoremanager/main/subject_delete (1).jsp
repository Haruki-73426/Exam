<%-- 科目削除確認JSP --%>
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

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                科目情報削除
            </h2>

            <p>
                「${name}」を削除してもよろしいですか？
            </p>

            <form action="SubjectDeleteExecute.action" method="get">

                <input type="hidden" name="cd" value="${cd}" />

                <div class="mx-auto py-2">
                    <input
                        class="btn btn-danger"
                        type="submit"
                        value="削除"
                    />
                </div>

            </form>

            <a href="SubjectList.action">戻る</a>

        </section>

    </c:param>

</c:import>
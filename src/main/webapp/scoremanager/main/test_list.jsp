<%-- 成績参照JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

            <form action="TestList.action" method="get">
                <div class="row border mx-3 mb-3 py-2 align-items-center rounded">
                    <div class="col-2">
                        <label class="form-label">入学年度</label>
                        <select class="form-select" name="f1">
                            <option value="0">--------</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-2">
                        <label class="form-label">クラス</label>
                        <select class="form-select" name="f2">
                            <option value="0">--------</option>
                            <c:forEach var="num" items="${class_num_set}">
                                <option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-3">
                        <label class="form-label">科目</label>
                        <select class="form-select" name="f3">
                            <option value="0">--------</option>
                            <c:forEach var="subject" items="${subject_set}">
                                <option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>${subject.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-2 text-center">
                        <button class="btn btn-secondary" type="submit">検索</button>
                    </div>

                    <div class="text-warning mt-2">${errors["subject"]}</div>
                </div>
            </form>

            <form action="TestList.action" method="get">
                <div class="row border mx-3 mb-3 py-2 align-items-center rounded">
                    <div class="col-5">
                        <label class="form-label">学生番号</label>
                        <input class="form-control" type="text" name="f4" value="${f4}" placeholder="学生番号を入力してください">
                    </div>

                    <div class="col-2 text-center">
                        <button class="btn btn-secondary" type="submit">検索</button>
                    </div>

                    <div class="text-warning mt-2">${errors["student"]}</div>
                </div>
            </form>

            <c:if test="${search_type == 'subject'}">
                <div class="mx-3 mb-3">
                    科目：${subject.name}
                </div>

                <c:choose>
                    <c:when test="${subject_results.size() > 0}">
                        <table class="table table-hover">
                            <tr>
                                <th>入学年度</th>
                                <th>クラス</th>
                                <th>学生番号</th>
                                <th>氏名</th>
                                <th>1回</th>
                                <th>2回</th>
                            </tr>

                            <c:forEach var="result" items="${subject_results}">
                                <tr>
                                    <td>${result.entYear}</td>
                                    <td>${result.classNum}</td>
                                    <td>${result.studentNo}</td>
                                    <td>${result.studentName}</td>
                                    <td>${result.point1}</td>
                                    <td>${result.point2}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:when>

                    <c:otherwise>
                        <div class="mx-3">学生情報が存在しませんでした。</div>
                    </c:otherwise>
                </c:choose>
            </c:if>

            <c:if test="${search_type == 'student'}">
                <div class="mx-3 mb-3">
                    氏名：${student.name}
                </div>

                <c:choose>
                    <c:when test="${student_results.size() > 0}">
                        <table class="table table-hover">
                            <tr>
                                <th>科目コード</th>
                                <th>科目名</th>
                                <th>回数</th>
                                <th>点数</th>
                            </tr>

                            <c:forEach var="result" items="${student_results}">
                                <tr>
                                    <td>${result.subjectCd}</td>
                                    <td>${result.subjectName}</td>
                                    <td>${result.no}</td>
                                    <td>${result.point}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:when>

                    <c:otherwise>
                        <div class="mx-3">成績情報が存在しませんでした。</div>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </section>
    </c:param>
</c:import>
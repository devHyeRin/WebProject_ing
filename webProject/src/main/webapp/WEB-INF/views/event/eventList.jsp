<%@page import="event.Event"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>조회하기</title>
</head>
<body>
<%List<Event> eventList = (List<Event>)request.getAttribute("eventList"); %>

<div class="sidebar">
    <h3>카테고리</h3>
    <ul>
        <li><a href="${pageContext.request.contextPath}/letsgu/event/list">전체 보기</a></li>
        <li><a href="${pageContext.request.contextPath}/letsgu/event/list?category=1">스터디 / 모임</a></li>
        <li><a href="${pageContext.request.contextPath}/letsgu/event/list?category=2">분실물</a></li>
        <li><a href="${pageContext.request.contextPath}/letsgu/event/list?category=3">중고거래</a></li>
        <li><a href="${pageContext.request.contextPath}/letsgu/event/list?category=4">동네 소식</a></li>
    </ul>
</div>

<h2>
    <c:choose>
        <c:when test="${not empty param.category}">
            ${eventList[0].categoryName} 카테고리 이벤트 목록
        </c:when>
        <c:otherwise>
            전체 이벤트 목록
        </c:otherwise>
    </c:choose>
</h2>

<table border="1">
    <tr>
        <th>번호</th>
        <th>카테고리</th>
        <th>제목</th>
        <th>지역</th>
        <th>모집일</th>
        <th>상태</th>
    </tr>

    <c:forEach var="event" items="${eventList}">
        <tr>
            <td>${event.eventId}</td>
            <td>${event.categoryName}</td>
            <td>${event.title}</td>
            <td>${event.region}</td>
            <td>${event.eventDate}</td>
            <td>${event.status}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
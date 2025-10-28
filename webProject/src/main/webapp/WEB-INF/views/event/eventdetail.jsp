<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/eventdetail.css">
<meta charset="UTF-8">
<title>이벤트 상세보기</title>
</head>
<body>

	<!-- 공통 header -->
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<main class = "wrapper">
		<div class="main-title">
			<!-- 검색바 영역 -->
			<div class = "search-bar">
				<form action="${pageContext.request.contextPath}/letsgu/event/search" method="get">
					<input type="text" name="keyword" value="${keyword}" placeholder="관심 있는 이벤트를 검색해보세요 😊" required>
					<button type="submit">검색</button>
				</form>
			</div>
			
			<!-- 인기순, 지역 선택 버튼 -->
			<div class="filter-group">
				<!-- 인기순 버튼 -->
				<div class = populer-filter>
					<a href="${pageContext.request.contextPath}/letsgu/event/list?sort=popular"
					    	${param.sort eq 'popular' ? 'active' : ''}">
						🔥 인기순
					</a>
				</div>
				<!-- 지역 선택 버튼 -->
				<div class = "region-filter">
					<a href="${pageContext.request.contextPath}/letsgu/event/list?region=전체" class="region-btn ${param.region eq '전체' ? 'active' : ''}">전체</a>
					<c:forEach var="region" items="${regionList}">
						<a href="${pageContext.request.contextPath}/letsgu/event/list?region=${region}" class="region-btn ${param.region eq region ? 'active' : ''}">${region}</a>
					</c:forEach>
				</div>
			</div>
			
		</div>
		<section class = "event-detail-section">
			<!-- 이미지 -->
			<div class = "detail-thumb">
				<c:choose>
					<c:when test="${not empty event.uploadImg}">
						<img src="${pageContext.request.contextPath}/upload/${event.uploadImg}" alt="이벤트 이미지">
					</c:when>
					<c:otherwise>
						<div class="no-img">이미지 없음</div>
					</c:otherwise>
				</c:choose>
			</div>
			
			<!-- 제목/정보 -->
			<div class="detail-info">
				<h2 class="detail-title">${event.title}</h2>
				<p class="detail-meta">
					작성자: <span>${event.authorName}</span> |
					카테고리: <span>${event.categoryName}</span> |
					지역: <span>${event.region}</span> |
					작성일: <fmt:formatDate value="${event.createdAt}" pattern="yyyy-MM-dd HH:mm"/>
				</p>
		
				<!-- 모집 정보 -->
				<c:if test="${not empty event.eventDate || event.capacity > 0}">
					<div class="detail-recruit">
						<c:if test="${not empty event.eventDate}">
							<p>모집 마감일: <fmt:formatDate value="${event.eventDate}" pattern="yyyy-MM-dd"/></p>
						</c:if>
						<c:if test="${event.capacity > 0}">
							<p>모집 인원: ${event.capacity}명</p>
						</c:if>
					</div>
				</c:if>
		
				<!-- 본문 내용 -->
				<div class="detail-description">
					<p>${event.description}</p>
				</div>
		
				<!-- 추천 / 비추천 -->
				<div class="like-section">
					<form action="${pageContext.request.contextPath}/letsgu/event/like" method="post" style="display:inline;">
						<input type="hidden" name="eventId" value="${event.eventId}">
						<button type="submit" class="like-btn">❤️ ${event.likeCount}</button>
					</form>
		
					<form action="${pageContext.request.contextPath}/letsgu/event/dislike" method="post" style="display:inline;">
						<input type="hidden" name="eventId" value="${event.eventId}">
						<button type="submit" class="dislike-btn">👎 ${event.dislikeCount}</button>
					</form>
				</div>
		
				<!-- 참여 버튼 (모임/스터디일 때만) -->
				<div class="participate">
					<c:choose>
						<c:when test="${event.categoryId == 2}">
							<form action="${pageContext.request.contextPath}/letsgu/event/join" method="post">
								<input type="hidden" name="eventId" value="${event.eventId}">
								<button type="submit" class="join-btn">참여하기</button>
							</form>
						</c:when>
						<c:otherwise>
							<button class="join-btn" disabled>참여 불가</button>
						</c:otherwise>
					</c:choose>
				</div>
		
				<!-- 수정 / 삭제 (작성자만) -->
				<c:if test="${LOGIN_ID != null && LOGIN_ID.userId == event.authorId}">
					<div class="edit-actions">
						<a href="${pageContext.request.contextPath}/letsgu/event/update?eventId=${event.eventId}" class="edit-btn">수정</a>
		
						<form action="${pageContext.request.contextPath}/letsgu/event/delete" method="post" class="inline-form">
							<input type="hidden" name="eventId" value="${event.eventId}">
							<button type="submit" class="delete-btn" onclick="return confirm('정말 삭제하시겠습니까?')">삭제</button>
						</form>
					</div>
				</c:if>
		
				<!-- 목록으로 -->
				<div class="back-list">
					<a href="${pageContext.request.contextPath}/letsgu/event/list">목록으로 돌아가기</a>
				</div>
			</div>
		
		</section>
	
	</main>

	<!-- 공통 footer -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>

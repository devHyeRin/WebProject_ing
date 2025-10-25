<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인페이지</title>
<script>
	
</script>
</head>
<body>
	<h1>메인페이지</h1>
	<button type="button"
		onclick="location.href='${pageContext.request.contextPath}/letsgu/login'">로그인</button>
	<button type="button"
		onclick="location.href= '${pageContext.request.contextPath}/letsgu/mypage'">마이페이지</button>
		<button type="button"
		onclick="location.href= '${pageContext.request.contextPath}/letsgu/event/list'">상품 리스트</button>
</body>
</html>

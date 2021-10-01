<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
    원래는 request.getAttribute해야 한다.
    리턴결과가 Object라서 맞는 타입으로 캐스팅도 해줘야 한다
    대신 JSP의 표현식을 쓰면 편하게 쓸 수 있다
--%>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <li>id=${member.id}</li>
    <li>username=${member.username}</li>
    <li>age=${member.age}</li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>

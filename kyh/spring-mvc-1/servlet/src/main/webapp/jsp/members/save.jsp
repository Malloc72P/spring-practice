<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%@ page import="hello.servlet.domain.member.Member" %><%--
  Created by IntelliJ IDEA.
  User: scra
  Date: 2021-10-01
  Time: 오후 12:08
  To change this template use File | Settings | File Templates.

  JSP도 결국 서블릿으로 바뀌므로, request, response객체를 사용할 수 있다
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    MemberRepository memberRepository = MemberRepository.getInstance();
    System.out.println("MemberSaveServlet.service");

    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));

    Member member = new Member(username, age);
    memberRepository.save(member);
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <ul>
        <li>id = <%=member.getId()%></li>
        <li>username = <%=member.getUsername()%></li>
        <li>age = <%=member.getAge()%></li>
    </ul>
</body>
</html>

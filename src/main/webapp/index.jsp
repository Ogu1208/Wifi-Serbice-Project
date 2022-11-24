<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1>와이파이 정보 구하기</h1>

<a href = "index.jsp"> 홈 </a>
&nbsp;|&nbsp;
<a href = "history.jsp"> 위치 히스토리 목록 </a>
&nbsp;|&nbsp;
<a href = "load_wifi.jsp"> Open API 와이파이 정보 가져오기 </a>



<%--<table>--%>
<%--    <thead>--%>
<%--    <tr>--%>
<%--        <th>회원구분</th>--%>
<%--        <th>아이디</th>--%>
<%--        <th>비밀번호</th>--%>
<%--        <th>이름</th>--%>
<%--    </tr>--%>
<%--    </thead>--%>
<%--    <tbody>--%>
<%--    <tr>--%>
<%--            <%--%>
<%--				for (Member member : memberList) {--%>
<%--				%>--%>
<%--    <tr>--%>
<%--        <td> <%=member.getMemberType()%> </td>--%>
<%--        <td>--%>
<%--            <a href="detail.jsp?memberType=<%=member.getMemberType() %>&userId=<%=member.getUserId()%>">--%>
<%--                <%=member.getUserId()%>--%>
<%--            </a>--%>
<%--        </td>--%>
<%--        <td> <%=member.getPassword()%> </td>--%>
<%--        <td> <%=member.getName()%> </td>--%>
<%--    </tr>--%>

<%--    <%--%>
<%--        }--%>
<%--    %>--%>
<%--    </tr>--%>
<%--    </tbody>--%>

</table>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>
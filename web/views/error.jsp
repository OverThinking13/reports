<%@ page import="app.model.Event" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/w3.css">
</head>

<body class="w3-light-grey">

<div class="w3-bar w3-top w3-black w3-large">
    <%if (session.getAttribute("admin") == null) {%>
    <%@include file="login.jsp" %>
    <%} else {%>
    <%@include file="logout.jsp" %>
    <%}%>
    <button class="w3-bar-item w3-hover-red w3-right w3-black" role="button" type="submit"
            onclick="location.href='${pageContext.request.contextPath}/settings'">Настройки
    </button>

</div>
<div class="w3-main" style="margin-top:43px;">
    <div class="w3-container w3-padding-16 w3-dark-gray"><h5>Ошибка</h5></div>
    <div class="w3-content w3-margin-top" style="max-width:1400px;">
        <div class="w3-panel w3-red w3-display-container w3-card-4 w3-round">
            <span onclick="this.parentElement.style.display='none'"
                  class="w3-button w3-margin-right w3-display-right w3-round-large w3-hover-red w3-border w3-border-red w3-hover-border-grey">x</span>
            <h5><% out.print(request.getAttribute("event"));%></h5>
        </div>

    </div>
</div>
<div class="w3-padding w3-padding-48"></div>
<div class="w3-black w3-large w3-padding-16 w3-container w3-opacity w3-right-align w3-padding">
    <button class="w3-button w3-hover-blue w3-black" onclick="location.href='${pageContext.request.contextPath}'">На
        главную
    </button>
</div>
<footer class="w3-container w3-black">
    <p>Powered by uszson-chuna</p>
</footer>
</body>
</html>

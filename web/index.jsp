<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Отчёты</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/w3.css">
</head>

<body class="w3-light-grey">
<div class="w3-bar w3-top w3-black w3-large">
    <%if (session.getAttribute("admin") == null) {%>
    <%@include file="views/login.jsp" %>
    <%} else {%>
    <%@include file="views/logout.jsp" %>
    <%}%>
</div>
<div class="w3-main" style="margin-top:43px;">


    <div class="w3-container w3-padding-16 w3-dark-gray"><h5>Отчёты</h5></div>

    <div class="w3-panel">
        <div class="w3-row-padding" style="margin:0 -16px">

            <div class="w3-twothird">
                <h5 class="w3-bottombar w3-border-green ">Обращения</h5>
                <p>
                    <button class="w3-btn w3-hover-green"
                            onclick="location.href='${pageContext.request.contextPath}/petitions?type=formed'">
                        В статусе сформировано
                    </button>
                </p>
                <p>
                    <button class="w3-btn w3-hover-green"
                            onclick="location.href='${pageContext.request.contextPath}/petitions?type=approved'">
                        В статусе утверждено
                    </button>
                </p>

            </div>

            <div class="w3-third">
                <h5 class="w3-bottombar w3-border-red ">Настройки</h5>
                <p>
                    <button class="w3-button w3-hover-red"
                            onclick="location.href='${pageContext.request.contextPath}/deadline'">
                        Сроков назначений
                    </button>
                </p>
                <p>
                    <button class="w3-button w3-hover-red"
                            onclick="location.href='${pageContext.request.contextPath}/holiday'">
                        Празничных дней
                    </button>
                </p>
                <p>
                    <button class="w3-button w3-hover-red"
                            onclick="location.href='${pageContext.request.contextPath}/settings'">
                        Расположений файлов
                    </button>
                </p>
            </div>

        </div>
    </div>
</div>
<div class="w3-padding w3-padding-48"></div>

<div class="w3-container w3-dark-grey w3-padding-48"></div>


<footer class="w3-container w3-black">
    <p>Powered by uszson-chuna</p>
</footer>
</body>
</html>
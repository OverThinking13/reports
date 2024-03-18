<%@ page import="app.model.Settings" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Настройки</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/w3.css">
</head>
<style>
    span {
        display: flex;
        overflow: hidden;
        padding-right: 10px;
    }

    input[type="color"] {
        width: 100%
    }
</style>
<body class="w3-light-grey">

<div class="w3-bar w3-top w3-black w3-large">
    <%@include file="logout.jsp"%>

    <button class="w3-bar-item w3-hover-red w3-right w3-black" role="button" type="submit" onclick="submitForm()">
        Сохранить
    </button>
</div>
<div class="w3-main" style="margin-top:43px;">
    <div class="w3-container w3-padding-16 w3-dark-gray"><h5>Настройки</h5></div>
    <form method="post" id="myForm">
        <div class="w3-content w3-margin-top" style="max-width:1400px;">

            <div class="w3-row-padding" style="margin:0 -16px">
                <div class="w3-twothird">

                    <div class="w3-row w3-section">
                        <label><b>Логин</b>
                            <div class="w3-rest">
                                <input id="username" autocomplete="username" required  class="w3-input w3-border" name="login" type="text"
                                       value="<%out.print(Settings.getLogin());%>"/>
                            </div>
                        </label></div>

                    <div class="w3-row w3-section">
                        <label><b>Пароль</b>
                            <div class="w3-rest">
                                <input id="password" required class="w3-input w3-border password-field" name="password"
                                       type="password"
                                       value="<%out.print(Settings.getPassword());%>"/>
                            </div>
                        </label></div>

                    <div class="w3-row w3-section">
                        <label><b>Адрес контекста</b>
                            <div class="w3-rest">
                                <input id="context" autocomplete="off" required class="w3-input w3-border" name="address" type="text"
                                       value="<%out.print(Settings.getAddress());%>"/>
                            </div>
                        </label></div>
                </div>


                <div class="w3-third">
                    <div class="w3-row w3-section">
                        <div class="w3-bar">
                            <div style="width: 50%" class="w3-left">Дни</div>
                            <div>Цвет</div>
                        </div>
                        <span>
                          <input min="0" max="1000" class="w3-input w3-border" name="redDay" id="redDay" type="number"
                                                             value="<%out.print(Settings.getRedDay());%>"/>
                          <input class="w3-border" name="redDayColor" id="redDayColor" type="color" value="<%out.print(Settings.getRedDayColor());%>"/>

                        </span>
                    </div>

                    <div class="w3-row w3-section">
                        <label><b>&nbsp;</b>
                            <span>
                             <input min="1" max="1000" class="w3-input w3-border" name="yellowDay" id="yellowDay"
                                    type="number" value="<%out.print(Settings.getYellowDay());%>"/>
                             <input class="w3-border" name="yellowDayColor" id="yellowDayColor" type="color"
                                    value="<%out.print(Settings.getYellowDayColor());%>"/></span>
                        </label>
                    </div>
                    <div class="w3-row w3-section">

                        <label><b>&nbsp;</b>
                        <span>
                             <input class="w3-border" name="greenDayColor" id="greenDayColor" type="color"
                                    value="<%out.print(Settings.getGreenDayColor());%>"/></span>
                        </label>
                    </div>
                </div>
            </div>

            <div class="w3-row w3-section">
                <label><b>Путь к файлу с обращениями в статусе сформировано</b>
                    <div class="w3-rest">
                        <input id="petitionsFormed" required class="w3-input w3-border" name="pathPetitionsFormed"
                               type="text"
                               value="<%out.print(Settings.getInstance().getFormed().getPathPetitions());%>"/>
                    </div>
                </label></div>
            <div class="w3-row w3-section">
                <label><b>Путь к файлу с обращениями в статусе утверждено</b>
                    <div class="w3-rest">
                        <input id="petitionsApproved" required class="w3-input w3-border" name="pathPetitionsApproved"
                               type="text"
                               value="<%out.print(Settings.getInstance().getApproved().getPathPetitions());%>"/>
                    </div>
                </label></div>
            <div class="w3-row w3-section">
                <label><b>Путь к файлу с праздничными днями</b>
                    <div class="w3-rest">
                        <input id="holidays" required class="w3-input w3-border" name="pathHolidays" type="text"
                               value="<%out.print(Settings.getPathHolidays());%>"/>
                    </div>
                </label></div>
            <div class="w3-row w3-section">
                <label><b>Путь к файлу со сроками назначений</b>
                    <div class="w3-rest">
                        <input id="deadline" required class="w3-input w3-border" name="pathCatalogs" type="text"
                               value="<%out.print(Settings.getPathCatalogs());%>"/>
                    </div>
                </label></div>
            <div class="w3-row w3-section">
                <label><b>Время обновления файлов в минутах</b>
                    <div class="w3-rest">
                        <input id="updateFrequency" required class="w3-input w3-border" name="updateFrequency"
                               type="number"
                               min="0" max="1000"
                               value="<%out.print(Settings.getUpdateFrequency());%>"/>
                    </div>
                </label></div>
        </div>
    </form>

</div>
<script>
    function submitForm() {
        document.getElementById("myForm").submit();
    }
</script>
<div class="w3-padding w3-padding-48"></div>
<div class="w3-black w3-large w3-padding-16 w3-container w3-opacity w3-right-align w3-padding">
    <button class="w3-button w3-hover-blue w3-black" onclick="location.href='/reports/'">На главную</button>
</div>
<footer class="w3-container w3-black">
    <p>Powered by uszson-chuna</p>
</footer>
</body>
</html>

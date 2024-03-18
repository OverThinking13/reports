<%@ page import="java.util.Calendar" %>
<%@ page import="app.entities.Holiday" %>
<%@ page import="app.model.Model" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Праздничные и выходные дни</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/w3.css">
</head>

<body class="w3-light-grey">
<div class="w3-bar w3-top w3-black w3-large">
    <%@include file="logout.jsp" %>
    <button class="w3-bar-item w3-hover-red w3-right w3-black" role="button" type="submit" onclick="submitForm()">
        Сохранить
    </button>
</div>
<style>
    label {
        display: block;
        text-align: center;
        cursor: pointer;
    }

    input {
        display: none;
    }

    input:checked ~ label {
        background-color: #f44336;
    }
</style>

<div class="w3-main" style="margin-top:43px;">
    <div class="w3-container w3-padding-16 w3-dark-gray "><h5>Праздничные и выходные дни</h5></div>
    <div class="w3-content w3-margin-top" style="max-width:1400px;">
        <form method="post" id="myForm">
            <%
                Set<Holiday> holidays = Model.getHolidays();

                String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
                Calendar calendar = Calendar.getInstance();
                calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);

                int nextYear = calendar.get(Calendar.YEAR) + 1;
                int row = 0;
                while (calendar.get(Calendar.YEAR) != nextYear) {
                    if (row++ == 0)
                        out.println("<div class=\"w3-row\">");

                    out.println("<div class=\"w3-container w3-third\">");
                    switch (row) {
                        case 1:
                            out.println("<h5 class=\"w3-bottombar w3-border-green\">" + months[calendar.get(Calendar.MONTH)] + "</h5>");
                            break;
                        case 2:
                            out.println("<h5 class=\"w3-bottombar w3-border-red\">" + months[calendar.get(Calendar.MONTH)] + "</h5>");
                            break;
                        case 3:
                            out.println("<h5 class=\"w3-bottombar w3-border-orange\">" + months[calendar.get(Calendar.MONTH)] + "</h5>");
                            break;
                    }
            %>
            <table class="w3-table w3-white w3-border">
                <tbody>
                <tr class="w3-light-grey w3-border">
                    <th>Пн</th>
                    <th>Вт</th>
                    <th>Ср</th>
                    <th>Чт</th>
                    <th>Пт</th>
                    <th>Сб</th>
                    <th>Вс</th>
                </tr>


                    <%
                    out.println("<tr>");
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    if (dayOfWeek == 1) {
                        dayOfWeek = 7;
                    } else {
                        dayOfWeek--;
                    }

                    for (int i = 1; i < dayOfWeek; i++) {
                        out.print("<td></td>");
                    }

                    int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    for (int i = 0; i < maxDays; i++) {

                        Holiday holiday = new Holiday(calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
                        String checked = "";
                        if (holidays.contains(holiday))
                            checked = "checked";

                        out.print(String.format("<td><input type=\"checkbox\" %s id=\"%s\" name=\"day\" value=\"%s\">" +
                                        "<label class=\"w3-hover-light-gray\" for=\"%s\" >%s</label></td>", checked,
                                holiday, holiday, holiday, calendar.get(Calendar.DAY_OF_MONTH)));

                        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                            out.println("</tr>");

                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                    }

                    out.println("</tbody></table></div>");
                    if (row == 3) {
                        row = 0;
                        out.println("</div>");
                    }
                }
            %>
        </form>
    </div>
</div>
<script>
    function submitForm() {
        document.getElementById("myForm").submit();
    }
</script>
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

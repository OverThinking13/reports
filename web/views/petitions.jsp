<%@ page import="java.util.List" %>
<%@ page import="app.entities.Petition" %>
<%@ page import="app.model.Settings" %>
<%@ page import="app.entities.Catalog" %>
<%@ page import="app.entities.PetitionRepository" %>
<%@ page import="app.model.Model" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title><%out.print((String) request.getAttribute("title"));%></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/w3.css">
</head>

<body class="w3-light-grey">
<div class="w3-bar w3-top w3-black w3-large">
    <button class="w3-bar-item w3-hover-blue w3-left w3-black" onclick="window.location.reload()">Обновить</button>
    <button class="w3-bar-item w3-hover-red w3-right w3-black"
            onclick="location.href='${pageContext.request.contextPath}/deadline'">Сроки назначения
    </button>
</div>
<style>
    .tooltip {
        position: relative;
    }

    .tooltip .tooltip-text {
        visibility: hidden;
        background-color: #000;
        color: #fff;
        border-radius: 10px;
        padding: 1em;
        position: absolute;
        z-index: 1;
        top: 150%;
        left: 10px;
    }

    .tooltip .tooltip-text::after {
        content: "";
        position: absolute;
        bottom: 100%;
        left: 50%;
        margin-left: -5px;
        border-width: 10px;
        border-style: solid;
        border-color: transparent transparent #000 transparent;
    }

    .tooltip:hover .tooltip-text {
        visibility: visible;
    }
</style>


<div class="w3-main" style="margin-top:43px;">

    <div class="w3-row-padding w3-container w3-padding-16 w3-dark-gray">
        <h5 class="tooltip">
            <%out.print((String) request.getAttribute("title"));%>
            <div class="tooltip-text">На дату:
                <%
                    List<Petition> petitions;
                    final boolean isFormed = (request.getParameter("type")).equals("formed");
                    if (isFormed) {
                        petitions = Model.getPetitionsFormed();
                        out.print(PetitionRepository.russianDate(Settings.getInstance().getFormed().getFileTime()));
                    } else {
                        petitions = Model.getPetitionsApproved();
                        out.print(PetitionRepository.russianDate(Settings.getInstance().getApproved().getFileTime()));
                    }
                %>
            </div>
        </h5>
    </div>

    <%

        if (!petitions.isEmpty()) {
            out.println("<table id=\"table\"  class=\"w3-table w3-striped w3-white\">\n" +
                    "<tbody><tr><th>Дни </th><th>Колличество обращений: " + petitions.size() + "</th></tr>");
            for (Petition petition : petitions) {
                String deadline = null;
                Catalog catalog;

                if (!Model.getCatalogs().isEmpty()) {
                    catalog = Model.getCatalogs().get(Objects.hash(petition.getMspId(), petition.getCategoryId()));
                    if (catalog.isCalendar())
                        deadline = String.format("Расчёт: колличество календарных дней (%s)", catalog.getDays());
                    else
                        deadline = String.format("Расчёт: колличество рабочих дней (%s)", catalog.getDays());
                }

                String colorDay = Settings.setDayColor(petition.getDays(), isFormed);

                out.println(String.format("<tr class=\"w3-hover-black\"><td><b>%s<b></td><td class=\"tooltip\">" +
                                "<a href=\"%s/admin/edit.htm?id=%s%%40wmPetition\" target=\"_blank\">%s</a>",
                        colorDay, Settings.getAddress(), petition.getLink(), petition.getMspName()));


                String dateDone = "";

                if (!isFormed)
                    dateDone = String.format("Дата утверждения: %s", PetitionRepository.russianDate(petition.getDateDone()));


                out.println(String.format("<span class=\"tooltip-text\">Льготная категория: %s<br>" +
                                "Дата обращения: %s<br>%s<br>Крайний срок: %s<br>" +
                                "%s</span></td></tr>",
                        petition.getCategoryName(), PetitionRepository.russianDate(petition.getDate()), deadline,
                        PetitionRepository.russianDate(petition.getFinalDate()),
                        dateDone));
            }

            out.println("</tbody></table>");
        } else out.println("<div class=\"w3-panel w3-red w3-display-container w3-card-4 w3-round\">\n"
                +
                "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-red w3-border w3-border-red w3-hover-border-grey\">x</span>\n" +
                "   <h5>Обращения отсутсвуют<br>Проверьте настройки!</h5>\n" +
                "</div>");
    %>

</div>
<div class="w3-padding w3-padding-48"></div>
<div class="w3-black w3-large w3-padding-16 w3-container w3-opacity w3-right-align w3-padding">
    <button class="w3-button w3-hover-blue" onclick="location.href='/reports/'">На главную</button>
</div>
<footer class="w3-container w3-black">
    <p>Powered by uszson-chuna</p>
</footer>
</body>
</html>

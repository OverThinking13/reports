<%@ page import="app.entities.Catalog" %>
<%@ page import="java.util.Map" %>
<%@ page import="app.model.Model" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Сроки назначения</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/w3.css">
</head>

<body class="w3-light-grey">

<div class="w3-bar w3-top w3-black w3-large">
    <%@include file="logout.jsp" %>
    <button class="w3-bar-item w3-hover-red w3-right w3-black" role="button" type="submit" onclick="submitForm()">
        Сохранить
    </button>

</div>
<div class="w3-main" style="margin-top:43px;">

    <div class="w3-container w3-padding-16 w3-dark-gray"><h5>Сроки назначения</h5></div>

    <style>
        .switch {
            position: relative;
            display: inline-block;
            width: 60px;
            height: 34px;
        }

        .switch input {
            opacity: 0;
            width: 0;
            height: 0;
        }

        .slider {
            position: absolute;
            cursor: pointer;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background-color: #ccc;
            -webkit-transition: .4s;
            transition: .4s;
        }

        .slider:before {
            position: absolute;
            content: "";
            height: 26px;
            width: 26px;
            left: 4px;
            bottom: 4px;
            background-color: white;
            -webkit-transition: .4s;
            transition: .4s;
        }

        input:checked + .slider {
            background-color: #2196F3;
        }

        input:focus + .slider {
            box-shadow: 0 0 1px #2196F3;
        }

        input:checked + .slider:before {
            -webkit-transform: translateX(26px);
            -ms-transform: translateX(26px);
            transform: translateX(26px);
        }

        /* Rounded sliders */
        .slider.round {
            border-radius: 34px;
        }

        .slider.round:before {
            border-radius: 50%;
        }
    </style>

    <form method="post" id="myForm">
        <table id="table" class="w3-table w3-striped w3-white">
            <tbody>
            <%
                final Map<Integer, Catalog> catalogs = Model.getCatalogs();
                if (!catalogs.isEmpty()) {%>
            <tr>
                <th onclick="sortTable(0)">МСП &#8645;</th>
                <th onclick="sortTable(1)">ЛК &#8645;</th>
                <th>Дни</th>
                <th>К/Р</th>
            </tr>
            <%
                for (int key : catalogs.keySet()) {
                    String checkbox = String.format("<input id=\"%sisCalendar\" type=\"checkbox\" checked name=\"%s\" value=\"isCalendar\">", key, key);
                    if (catalogs.get(key).isCalendar())
                        checkbox = String.format("<input id=\"%sisCalendar\" type=\"checkbox\"   name=\"%s\">", key, key);

                    out.println(String.format("<tr><td>%s</td><td>%s</td><td>" +
                                    "<input id=\"%sDays\" autocomplete=\"off\" type=\"number\" class=\"w3-input w3-border\" name=\"%s\" value=\"%s\"></td><td>" +
                                    "<label class=\"switch\">%s" +
                                    "<span class=\"slider\"></span>" +
                                    " </label></td></tr>",
                            catalogs.get(key).getMspName(), catalogs.get(key).getCategoryName(), key, key,
                            catalogs.get(key).getDays(), checkbox));
                }

            } else {%>
            <div class="w3-panel w3-red w3-display-container w3-card-4 w3-round">
                <span onclick="this.parentElement.style.display='none' class=" w3-button w3-margin-right
                      w3-display-right w3-round-large w3-hover-red w3-border w3-border-red
                      w3-hover-border-grey">×</span>
                <h5>Сроки назначений отсутсвуют<br>Проверьте настройки!</h5></div>
            <%}%>
            </tbody>
        </table>
    </form>

    <script>
        function submitForm() {
            document.getElementById("myForm").submit();
        }
    </script>
    <script>
        function sortTable(n) {
            let table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
            table = document.getElementById("table");
            switching = true;
            // Set the sorting direction to ascending:
            dir = "asc";
            /* Make a loop that will continue until
            no switching has been done: */
            while (switching) {
                // Start by saying: no switching is done:
                switching = false;
                rows = table.rows;
                /* Loop through all table rows (except the
                first, which contains table headers): */
                for (i = 1; i < (rows.length - 1); i++) {
                    // Start by saying there should be no switching:
                    shouldSwitch = false;
                    /* Get the two elements you want to compare,
                    one from current row and one from the next: */
                    x = rows[i].getElementsByTagName("TD")[n];
                    y = rows[i + 1].getElementsByTagName("TD")[n];
                    /* Check if the two rows should switch place,
                    based on the direction, asc or desc: */
                    if (dir === "asc") {
                        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                            // If so, mark as a switch and break the loop:
                            shouldSwitch = true;
                            break;
                        }
                    } else if (dir === "desc") {
                        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                            // If so, mark as a switch and break the loop:
                            shouldSwitch = true;
                            break;
                        }
                    }
                }
                if (shouldSwitch) {
                    /* If a switch has been marked, make the switch
                    and mark that a switch has been done: */
                    rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                    switching = true;
                    // Each time a switch is done, increase this count by 1:
                    switchcount++;
                } else {
                    /* If no switching has been done AND the direction is "asc",
                    set the direction to "desc" and run the while loop again. */
                    if (switchcount === 0 && dir === "asc") {
                        dir = "desc";
                        switching = true;
                    }
                }
            }
        }
    </script>
</div>

<div class="w3-padding w3-padding-48"></div>
<div class="w3-black w3-large w3-padding-16 w3-container w3-opacity w3-right-align w3-padding">
    <button class="w3-button w3-hover-blue w3-black" onclick="location.href='/reports/'">На главную</button>
</div>
<footer class="w3-container w3-black">
    <p>Powered by uszson-chuna</p>
</footer>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>

    <button onclick="document.getElementById('id01').style.display='block'" class="w3-button w3-hover-green w3-bar-item w3-left">Войти</button>

    <div id="id01" class="w3-modal">
        <div class="w3-modal-content w3-card-4 w3-animate-zoom" style="max-width:600px">

            <div class="w3-center"><br>
                <span onclick="document.getElementById('id01').style.display='none'" class="w3-button w3-xlarge w3-transparent w3-display-topright" title="Close Modal">×</span>
            </div>

            <form class="w3-container" method="post" action="LoginServlet">
                <div class="w3-section">
                    <label><b>Имя пользователя</b>
                        <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="Введите имя" name="user" required>
                    </label>
                    <label><b>Пароль</b>
                        <input class="w3-input w3-border" type="password" placeholder="Введите пароль" name="pwd" required>
                    </label>
                    <button class="w3-button w3-block w3-green w3-section w3-padding" type="submit">Войти</button>
                </div>
            </form>

            <div class="w3-container w3-border-top w3-padding-16 w3-light-grey">
                <button onclick="document.getElementById('id01').style.display='none'" type="button" class="w3-button w3-red">Отмена</button>
            </div>
        </div>
    </div>

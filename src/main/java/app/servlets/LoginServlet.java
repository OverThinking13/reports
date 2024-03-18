package app.servlets;

import app.model.Settings;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.UnknownHostException;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
        String user = req.getParameter("user");
        String pwd = req.getParameter("pwd");

        if(Settings.getLogin().equals(user) && Settings.getPassword().equals(pwd)){
            HttpSession session = req.getSession();
            session.setAttribute("admin",true);
            try {
                resp.sendRedirect("/reports");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            req.setAttribute("event", "Неверный логин или пароль");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/views/error.jsp");
            try {
                requestDispatcher.forward(req, resp);
            } catch (ServletException | IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
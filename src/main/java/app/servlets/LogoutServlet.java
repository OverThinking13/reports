package app.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {

        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        try {
            response.sendRedirect("/reports");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

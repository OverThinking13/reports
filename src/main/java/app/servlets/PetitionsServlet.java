package app.servlets;


import app.model.Model;
import app.model.State;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PetitionsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        if (req.getParameter("type") != null && req.getParameter("type").equals("approved")) {
            req.setAttribute("type", "approved");
            req.setAttribute("title", "Обращения в статусе утверждено");
        } else {
            req.setAttribute("type", "formed");
            req.setAttribute("title", "Обращения в статусе сформировано");
        }

        if (Model.getState() == State.COMPLETE) {
            try {
                getServletContext().getRequestDispatcher(
                        "/views/petitions.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                resp.sendRedirect("/reports/error");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getParameter("type").equals("formed")) {
            Model.getPetitionsFormed();
        } else {
            Model.getPetitionsApproved();
        }

        doGet(req, resp);
    }
}

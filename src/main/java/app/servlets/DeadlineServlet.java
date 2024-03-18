package app.servlets;

import app.model.Model;
import app.model.State;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class DeadlineServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        if (Model.getState() == State.COMPLETE) {
            try {
                getServletContext().getRequestDispatcher("/views/deadline.jsp").forward(req, resp);
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
        Model.getCatalogs();
        if (Model.getState() == State.COMPLETE) {
            for (Map.Entry<String, String[]> entry : req.getParameterMap().entrySet()) {
                int key = Integer.parseInt(entry.getKey());
                if (Model.getCatalogs().get(key) != null) {
                    Model.getCatalogs().get(key).setDays(Integer.parseInt(entry.getValue()[0]));
                    Model.getCatalogs().get(key).setCalendar(entry.getValue().length == 1);
                }
            }
            Model.writeCatalogs();
            doGet(req, resp);
        } else {
            try {
                resp.sendRedirect("/reports/error");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
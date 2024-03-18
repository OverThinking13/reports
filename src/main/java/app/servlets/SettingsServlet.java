package app.servlets;

import app.model.Model;
import app.model.Settings;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Paths;


public class SettingsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    final Settings settings = Settings.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        settings.read();
        try {
            getServletContext().getRequestDispatcher("/views/settings.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        Settings.setLogin(req.getParameter("login"));
        Settings.setPassword(req.getParameter("password"));
        Settings.setAddress(req.getParameter("address"));
        settings.getFormed().setPathPetitions(Paths.get(req.getParameter("pathPetitionsFormed")));
        settings.getApproved().setPathPetitions(Paths.get(req.getParameter("pathPetitionsApproved")));
        Settings.setPathHolidays(Paths.get(req.getParameter("pathHolidays")));
        Settings.setPathCatalogs(Paths.get(req.getParameter("pathCatalogs")));
        Settings.setUpdateFrequency((Long.parseLong(req.getParameter("updateFrequency"))));
        Settings.setRedDay(Integer.parseInt(req.getParameter("redDay")));
        Settings.setRedDayColor(req.getParameter("redDayColor"));
        Settings.setYellowDay(Integer.parseInt(req.getParameter("yellowDay")));
        Settings.setYellowDayColor(req.getParameter("yellowDayColor"));
        Settings.setGreenDayColor(req.getParameter("greenDayColor"));


        if (!settings.setPaths()) {
            try {
                resp.sendRedirect("/reports/error");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            settings.write();
            Model.init();
            doGet(req, resp);
        }
    }
}

package app.servlets;

import app.entities.HolidayRepository;
import app.model.Model;
import app.model.State;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HolidayServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        if (Model.getState() == State.COMPLETE) {
            try {
                getServletContext().getRequestDispatcher("/views/holiday.jsp").forward(req, resp);
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
        if (Model.getState() == State.COMPLETE) {
            if(req.getParameter("day")!=null){//I don't believe if there is no holidays
                Model.getHolidays().clear();
            for (String param : req.getParameterValues("day")) {
                String[] day = param.split(";");
                HolidayRepository.addHoliday(Integer.parseInt(day[0]),
                        Integer.parseInt(day[1]),
                        Integer.parseInt(day[2]),Model.getHolidays());
            }
            Model.writeHolidays();
            }
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
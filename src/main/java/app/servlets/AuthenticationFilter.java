package app.servlets;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter {


    @Override
    public void init(FilterConfig fConfig)  {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain){

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        HttpSession session = req.getSession();

        if (session.getAttribute("admin") == null && !uri.endsWith("LoginServlet")) {
            req.setAttribute("event", "Необходимо войти");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/views/error.jsp");
            try {
                requestDispatcher.forward(req, resp);
            } catch (ServletException | IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            try {
                chain.doFilter(request, response);
            } catch (IOException | ServletException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void destroy() {
    }

}
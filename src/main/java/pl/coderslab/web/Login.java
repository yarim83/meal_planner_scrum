package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (AdminDao.checkLoginData(email, password)) {
            AdminDao adminDao = new AdminDao();
            Admin admin = adminDao.readByEmail(email);
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("username", admin);
            httpSession.setAttribute("adminId", admin.getId());
            response.sendRedirect("/");
        } else {
            response.sendRedirect("/login?msg=Wprowadzono+bledne+dane");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/jsp/login.jsp")
                .forward(request, response);
    }
}

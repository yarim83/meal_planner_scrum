package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/register")
public class Register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String first_name = request.getParameter("name");

        String last_name = request.getParameter("surname");

        String email = request.getParameter("email");

        String[] password = request.getParameterValues("password");

        if (!checkIfEmailNotExist(email) || !checkIfEmailCorrect(email) || !password[0].equals(password[1])) {
            response.sendRedirect("/register?msg=Wprowadzono+bledne+dane");
        }

        if(checkIfEmailNotExist(email) && checkIfEmailCorrect(email) && password[0].equals(password[1])){
            Admin admin = new Admin(first_name, last_name, email, password[0], 0,1);
            AdminDao.create(admin);
            response.sendRedirect("/login");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/html/registration.html")
                .forward(request, response);
    }

    private boolean checkIfEmailNotExist(String email) {
        List<Admin> adminsI = AdminDao.findAll();
        boolean emailNotExist = true;
        for (Admin a : adminsI) {
            if (a.getEmail().equals(email)) {
                emailNotExist = false;
            }
        }
        return emailNotExist;
    }

    private boolean checkIfEmailCorrect(String email) {
        Pattern pattern = Pattern.compile("[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.([a-zA-Z]{2,}){1}");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}


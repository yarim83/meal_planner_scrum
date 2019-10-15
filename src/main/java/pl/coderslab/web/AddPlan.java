package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/app/plan/add")
public class AddPlan extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String description = req.getParameter("description");

        HttpSession httpSession = req.getSession();
        Admin admin = (Admin) httpSession.getAttribute("admin");

        Plan plan = new Plan(name, description, new Timestamp(System.currentTimeMillis()), admin.getId());
        PlanDao planDao = new PlanDao();
        planDao.create(plan);

        resp.sendRedirect("app/plan/list");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext()
                .getRequestDispatcher("/jsp/app-schedule-add.jsp")
                .forward(req, resp);
    }
}

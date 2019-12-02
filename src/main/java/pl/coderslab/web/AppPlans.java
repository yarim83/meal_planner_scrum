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
import java.util.List;

@WebServlet("/app/plan/list")
public class AppPlans extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        Admin admin = (Admin) httpSession.getAttribute("admin");
        List<Plan> planList = PlanDao.findAllByAdmin(admin.getId());
        req.setAttribute("planList", planList);

        getServletContext()
                .getRequestDispatcher("/jsp/app-schedules.jsp")
                .forward(req, resp);
    }
}

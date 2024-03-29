package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.Plan;
import pl.coderslab.model.RecipePlan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/deletePlan")
public class DeletePlan extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String confirm = request.getParameter("confirm");
        String decline = request.getParameter("decline");

        int planId = Integer.parseInt(request.getParameter("planId"));

        if (confirm != null) {
            List<RecipePlan> recipePlans = RecipePlanDao.readByPlanId(planId);
            if (recipePlans != null) {
                for (RecipePlan rp : recipePlans) {
                    int id = rp.getId();
                    RecipePlanDao.delete(id);
                }
            }
            PlanDao.delete(planId);
            response.sendRedirect("/app/plan/list");
        } else {
            response.sendRedirect("/app/plan/list");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int planId = Integer.parseInt(request.getParameter("id"));
        Plan plan = PlanDao.read(planId);

        request.setAttribute("plan", plan);

        getServletContext().getRequestDispatcher("/jsp/deletePlan.jsp")
                .forward(request, response);
    }
}

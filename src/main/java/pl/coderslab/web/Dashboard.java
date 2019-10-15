package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;
import pl.coderslab.model.RecipePlan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession httpSession = req.getSession();
        Admin admin = (Admin) httpSession.getAttribute( "admin");
       // String email = (String) httpSession.getAttribute("email");

        PlanDao planDao = new PlanDao();
        int numberOfAddedPlans = planDao.numberOfPlans(admin.getId());
        Plan plan = planDao.lastAdded(admin.getId());
        int planId = plan.getId();
        RecipePlanDao recipePlanDao = new RecipePlanDao();
        List<RecipePlan> recipePlanList = recipePlanDao.readByPlanId(planId);
        RecipeDao recipeDao = new RecipeDao();
        int numberOfAddedRecipes = recipeDao.numberOfRecipesByAdminId(admin.getId());

        httpSession.setAttribute("plan", plan);
        httpSession.setAttribute("recipePlanList", recipePlanList);
        httpSession.setAttribute("numberOfAddedPlans", numberOfAddedPlans);
        httpSession.setAttribute("numberOfAddedRecipes", numberOfAddedRecipes);

        getServletContext().getRequestDispatcher("/jsp/dashboard.jsp").forward(req, resp);
    }
}

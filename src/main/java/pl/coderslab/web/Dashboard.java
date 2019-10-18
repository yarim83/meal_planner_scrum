package pl.coderslab.web;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession httpSession = req.getSession();
        Admin admin = (Admin) httpSession.getAttribute( "admin");

        PlanDao planDao = new PlanDao();
        int numberOfAddedPlans = planDao.numberOfPlans(admin.getId());
        Plan plan = planDao.lastAdded(admin.getId());
        int planId = plan.getId();

        RecipePlanDao recipePlanDao = new RecipePlanDao();
        List<RecipePlan> recipePlanList = recipePlanDao.readByPlanId(planId);

        RecipeDao recipeDao = new RecipeDao();
        int numberOfAddedRecipes = recipeDao.numberOfRecipesByAdminId(admin.getId());

        DayNameDao dayNameDao = new DayNameDao();
        List<DayName> dayNames = dayNameDao.findAll();
        List<Recipe> recipes = recipeDao.findAll();

        List<PlanCollect> planCollection = new ArrayList<>();

        for (RecipePlan x: recipePlanList) {
            PlanCollect planCollect = new PlanCollect();

            planCollect.setDayName(dayNames.get(x.getDay_name_id()).getName());
            planCollect.setMealName(x.getMeal_name());
            planCollect.setDescription(recipes.get(x.getRecipe_id()-1).getDescription());
            planCollect.setRecipeId(x.getRecipe_id());
            planCollect.setRecipePlanId(x.getPlan_id());

            planCollection.add(planCollect);
        }
        
        httpSession.setAttribute("plan", plan);
        httpSession.setAttribute("collection", planCollection);
        httpSession.setAttribute("recipePlanList", recipePlanList);
        httpSession.setAttribute("numberOfAddedPlans", numberOfAddedPlans);
        httpSession.setAttribute("numberOfAddedRecipes", numberOfAddedRecipes);

        getServletContext().getRequestDispatcher("/jsp/dashboard.jsp").forward(req, resp);
    }
}

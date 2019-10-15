package pl.coderslab.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.filters.AuthFilter;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;
import pl.coderslab.model.RecipePlan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/app/recipe/plan/add")

public class AddRecipeToPlan extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(AuthFilter.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int planId = Integer.parseInt(request.getParameter("plan"));

        String nameOfMeal = request.getParameter("nameOfMeal");

        int numberOfMeal = Integer.parseInt(request.getParameter("numberOfMeal"));

        int recipeId = Integer.parseInt(request.getParameter("recipe"));

        int dayId = Integer.parseInt(request.getParameter("day"));

        RecipePlan recipePlan = new RecipePlan(recipeId, nameOfMeal, numberOfMeal, dayId, planId);

        if (checkIfpositionOnPlanDayExist(planId, numberOfMeal, dayId)) {
            response.sendRedirect("/app/recipe/plan/add?msg=W danym dniu istnieje juz posilek na tej pozycji");
        } else {
            RecipePlanDao.create(recipePlan);
            response.sendRedirect("/app/recipe/plan/add?msg=Przepis+zostal+dodany+do+planu!");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        Admin admin = (Admin) httpSession.getAttribute("admin");

        PlanDao planDao = new PlanDao();
        List<Plan> adminPlans = planDao.findAllForUser(admin.getId());
        request.setAttribute("plans", adminPlans);

        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> adminRecipes = recipeDao.findAllForUser(admin.getId());
        request.setAttribute("recipes", adminRecipes);

        DayNameDao dayNameDao = new DayNameDao();
        request.setAttribute("days", dayNameDao.findAll());

        getServletContext().getRequestDispatcher("/jsp/app-schedules-meal-recipe.jsp")
                .forward(request, response);
    }

    private boolean checkIfpositionOnPlanDayExist(int planId, int numberOfMeal, int dayId) {
        List<RecipePlan> list = RecipePlanDao.readByPlanId(planId);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDisplay_order() == numberOfMeal && list.get(i).getDay_name_id() == dayId) {
               return true;
            }
        }
        return false;
    }
}
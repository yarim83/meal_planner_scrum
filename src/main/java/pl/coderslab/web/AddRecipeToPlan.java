package pl.coderslab.web;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
}

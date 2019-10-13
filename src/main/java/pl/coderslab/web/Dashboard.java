package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession httpSession = req.getSession();
        String email = (String) httpSession.getAttribute("email");

        PlanDao planDao = new PlanDao();
        int numberOfAddedPlans = planDao.numberOfPlans(email);
        Plan plan = planDao.lastAdded(email);
        RecipeDao recipeDao = new RecipeDao();
        int numberOfAddedRecipes = 5; // a czasem zastpić poprawnym kodem z recipeDao jak tylko Tomek napisze metodę.

        httpSession.setAttribute("plan", plan);
        httpSession.setAttribute("numberOfAddedPlans", numberOfAddedPlans);
        httpSession.setAttribute("numberOfAddedRecipes", numberOfAddedRecipes);

        getServletContext().getRequestDispatcher("/jsp/dashboard.jsp").forward(req, resp);
    }
}

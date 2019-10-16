package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/recipe/details")
public class AppRecipeDetails extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        Recipe recipe = RecipeDao.read(id);
        String ingredients = recipe.getIngredients();
        String[] splitedIngredients = ingredients.split(",");
        req.setAttribute("recipe", recipe);
        req.setAttribute("ingredients", splitedIngredients);

        getServletContext()
                .getRequestDispatcher("/jsp/app-recipe-detail.jsp")
                .forward(req,resp);
    }
}

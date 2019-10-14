package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Recipe;

import javax.print.DocFlavor;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/app/recipe/list")
public class AppRecipes extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        Admin admin = (Admin) httpSession.getAttribute("admin");
        List<Recipe> recipeList = RecipeDao.findAllForUser(admin.getId());
        req.setAttribute("recipeList", recipeList);

        getServletContext()
                .getRequestDispatcher("/jsp/app-recipes.jsp")
                .forward(req, resp);
    }
}

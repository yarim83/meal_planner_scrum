package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.Admin;
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

@WebServlet("/deleteRecipe")
public class DeleteRecipe extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String confirm = request.getParameter("confirm");
        String decline = request.getParameter("decline");
        int recipeId = Integer.parseInt(request.getParameter("recipeId"));
        if (confirm != null) {
            HttpSession httpSession = request.getSession();
            Admin admin = getCurrentUser(httpSession);
            int adminId = admin.getId();
            if (checkIfRecipeInAnyPlan(adminId, recipeId)) {
                response.sendRedirect("/app/recipe/list?msg=Nie+mozesz+usunac+przepisu+ktory+znajduje+sie+w+planie!");
            } else {
                RecipeDao.delete(recipeId);
                response.sendRedirect("/app/recipe/list");
            }
        } else {
            response.sendRedirect("/app/recipe/list");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String recipeId = request.getParameter("id");
        Recipe recipe = RecipeDao.read(Integer.parseInt(recipeId));

        request.setAttribute("recipe", recipe);

        getServletContext().getRequestDispatcher("/jsp/deleteRecipe.jsp")
                .forward(request, response);
    }

    private Admin getCurrentUser(HttpSession httpSession) {
        return (Admin) httpSession.getAttribute("admin");
    }

    private boolean checkIfRecipeInAnyPlan(int adminId, int recipeId) {
        List<RecipePlan> list = RecipePlanDao.findAll();
        for (RecipePlan plan : list) {
            if (plan.getRecipe_id() == recipeId) {
                return true;
            }
        }
        return false;
    }
}

package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDao {
    //przygotowanie zapytań SQL
    private static final String CREATE_RECIPE_QUERY = "INSERT INTO recipe(name, ingredients, description, created, updated, preparation_time, preparation, admin_id) VALUES (?,?,?,?,?,?,?,?);";
    private static final String DELETE_RECIPE_QUERY = "DELETE FROM recipe where id = ?;";
    private static final String FIND_ALL_RECIPE_QUERY = "SELECT * FROM recipe;";
    private static final String READ_RECIPE_QUERY = "SELECT * from recipe where id = ?;";
    private static final String UPDATE_RECIPE_QUERY = "UPDATE recipe SET name=? , ingredients=?, description=?, created=?, preparation_time=?, preparation=?, admin_id=?  WHERE id=?;";
    private static final String FIND_EXACT_BY_ADMIN_ID = "    SELECT * FROM recipe WHERE admin_id = ? ORDER BY created DESC LIMIT ?;";
    private static final String FIND_BY_ADMIN_ID = "    SELECT * FROM recipe WHERE admin_id = ? ORDER BY created DESC;";

    /**
     * Create recipe
     *
     * @param recipe
     * @return
     */

    public Recipe create(Recipe recipe) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_RECIPE_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, recipe.getName());
            statement.setString(2, recipe.getIngredients());
            statement.setString(3, recipe.getDescription());
            statement.setTimestamp(4, recipe.getCreated());
            statement.setTimestamp(5, recipe.getUpdated());
            statement.setInt(6, recipe.getPreparationTime());
            statement.setString(7, recipe.getPreparation());
            statement.setInt(8, recipe.getAdminId());
            int result = statement.executeUpdate();
            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    recipe.setId(generatedKeys.getInt(1));
                    return recipe;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Remove recipe by id
     *
     * @param id
     */
    public void delete(int id) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_RECIPE_QUERY)) {
            statement.setInt(1, id);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Recipe not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Read exact numer of recipes from adminId
     *
     * @param exactNumber
     * @param adminId
     * @return
     */
    public static List<Recipe> findExact(int exactNumber, int adminId) {
        List<Recipe> recipeList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_EXACT_BY_ADMIN_ID)) {
            statement.setInt(1, adminId);
            statement.setInt(2, exactNumber);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Recipe recipe = new Recipe();
                recipe.setId(resultSet.getInt("id"));
                recipe.setName(resultSet.getString("name"));
                recipe.setIngredients(resultSet.getString("ingredients"));
                recipe.setDescription(resultSet.getString("description"));
                recipe.setCreated(resultSet.getTimestamp("created"));
                recipe.setUpdated(resultSet.getTimestamp("updated"));
                recipe.setPreparationTime(resultSet.getInt("preparation_time"));
                recipe.setPreparation(resultSet.getString("preparation"));
                recipeList.add(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

    /**
     * find all recipes for specific admin
     *
     * @param adminId
     * @return
     */
    public static List<Recipe> findAllForUser(int adminId) {
        List<Recipe> recipeList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ADMIN_ID)) {
            statement.setInt(1, adminId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Recipe recipe = new Recipe();
                recipe.setId(resultSet.getInt("id"));
                recipe.setName(resultSet.getString("name"));
                recipe.setIngredients(resultSet.getString("ingredients"));
                recipe.setDescription(resultSet.getString("description"));
                recipe.setCreated(resultSet.getTimestamp("created"));
                recipe.setUpdated(resultSet.getTimestamp("updated"));
                recipe.setPreparationTime(resultSet.getInt("preparation_time"));
                recipe.setPreparation(resultSet.getString("preparation"));
                recipeList.add(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

    /**
     * Read all plans
     *
     * @return
     */
    public static List<Recipe> findAll() {
        List<Recipe> recipeList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_RECIPE_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Recipe recipe = new Recipe();
                recipe.setId(resultSet.getInt("id"));
                recipe.setName(resultSet.getString("name"));
                recipe.setIngredients(resultSet.getString("ingredients"));
                recipe.setDescription(resultSet.getString("description"));
                recipe.setCreated(resultSet.getTimestamp("created"));
                recipe.setUpdated(resultSet.getTimestamp("updated"));
                recipe.setPreparationTime(resultSet.getInt("preparation_time"));
                recipe.setPreparation(resultSet.getString("preparation"));
                recipe.setAdminId(resultSet.getInt("admin_id"));
                recipeList.add(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

    /**
     * Get recipe by id
     *
     * @param id
     * @return
     */
    public static Recipe read(int id) {
        Recipe recipe = new Recipe();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_RECIPE_QUERY)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    recipe.setId(id);
                    recipe.setName(resultSet.getString("name"));
                    recipe.setIngredients(resultSet.getString("ingredients"));
                    recipe.setDescription(resultSet.getString("description"));
                    recipe.setCreated(resultSet.getTimestamp("created"));
                    recipe.setUpdated(resultSet.getTimestamp("updated"));
                    recipe.setPreparationTime(resultSet.getInt("preparation_time"));
                    recipe.setPreparation(resultSet.getString("preparation"));
                    recipe.setAdminId(resultSet.getInt("admin_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipe;
    }

    /**
     * Update recipe
     *
     * @param recipe
     */
    public void update(Recipe recipe) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_RECIPE_QUERY)) {
            statement.setString(1, recipe.getName());
            statement.setString(2, recipe.getIngredients());
            statement.setString(3, recipe.getDescription());
            statement.setTimestamp(4, recipe.getCreated());
            statement.setTimestamp(5, recipe.getUpdated());
            statement.setInt(6, recipe.getPreparationTime());
            statement.setString(7, recipe.getPreparation());
            statement.setInt(8, recipe.getAdminId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

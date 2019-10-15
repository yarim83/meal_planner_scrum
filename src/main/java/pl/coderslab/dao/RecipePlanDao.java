package pl.coderslab.dao;

import pl.coderslab.model.RecipePlan;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipePlanDao {
    private static final String CREATE_RECIPE_PLAN_QUERY = "INSERT INTO recipe_plan(recipe_id, meal_name, display_order, day_name_id, plan_id) VALUES (?,?,?,?,?)";
    private static final String READ_RECIPE_PLAN_QUERY = "SELECT * FROM recipe_plan WHERE id = ?";
    private static final String UPDATE_RECIPE_PLAN_QUERY = "UPDATE recipe_plan SET recipe_id = ?, meal_name =?, display_order = ?, day_name_id = ?, plan_id = ? WHERE id = ?";
    private static final String DELETE_RECIPE_PLAN_QUERY = "DELETE FROM recipe_plan WHERE id = ?";
    private static final String FIND_ALL_RECIPE_PLAN_QUERY = "SELECT * FROM recipe_plan";
    private static final String READ_RECIPE_PLAN_BY_PLAN_ID_QUERY = "SELECT * FROM recipe_plan WHERE plan_id = ?";


    public static RecipePlan create(RecipePlan recipePlan) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(CREATE_RECIPE_PLAN_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preStmt.setInt(1, recipePlan.getRecipe_id());
            preStmt.setString(2, recipePlan.getMeal_name());
            preStmt.setInt(3, recipePlan.getDisplay_order());
            preStmt.setInt(4, recipePlan.getDay_name_id());
            preStmt.setInt(5, recipePlan.getPlan_id());
            preStmt.executeUpdate();
            ResultSet rs = preStmt.getGeneratedKeys();
            if (rs.next()) {
                recipePlan.setId(rs.getInt(1));
            }
            return recipePlan;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nie mozna stworzyc wpisu do bazy danych");
            return null;
        }
    }

    public RecipePlan read(int id) {
        try (Connection conn = DbUtil.getConnection()) {
            RecipePlan recipePlan = new RecipePlan();
            PreparedStatement preStmt = conn.prepareStatement(READ_RECIPE_PLAN_QUERY);
            preStmt.setInt(1, id);
            boolean exist = false;
            for (int i = 0; i < findAll().size(); i++) {
                if (findAll().get(i).getId() == id) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                return null;
            }
            ResultSet rs = preStmt.executeQuery();
            if (rs.next()) {
                recipePlan.setId(rs.getInt("id"));
                recipePlan.setRecipe_id(rs.getInt("recipe_id"));
                recipePlan.setMeal_name(rs.getString("meal_name"));
                recipePlan.setDisplay_order(rs.getInt("display_order"));
                recipePlan.setDay_name_id(rs.getInt("day_name_id"));
                recipePlan.setPlan_id(rs.getInt("plan_id"));
            }
            return recipePlan;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nie mozna wczytac wiersza o id: " + id);
            return null;
        }
    }

    public void update(RecipePlan recipePlan) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(UPDATE_RECIPE_PLAN_QUERY);
            preStmt.setInt(1, recipePlan.getRecipe_id());
            preStmt.setString(2, recipePlan.getMeal_name());
            preStmt.setInt(3, recipePlan.getDisplay_order());
            preStmt.setInt(4, recipePlan.getDay_name_id());
            preStmt.setInt(5, recipePlan.getPlan_id());
            preStmt.setInt(6, recipePlan.getId());
            preStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nie mozna zaktualzowac wiersza o id: " + recipePlan.getId());
        }
    }

    public void delete(int id) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(DELETE_RECIPE_PLAN_QUERY);
            preStmt.setInt(1, id);
            preStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nie mozna usunac wiersza z tablicy");
        }
    }


    public static List<RecipePlan> findAll() {
        try (Connection conn = DbUtil.getConnection()) {
            List<RecipePlan> recipePlans = new ArrayList<>();
            PreparedStatement preStmt = conn.prepareStatement(FIND_ALL_RECIPE_PLAN_QUERY);
            ResultSet rs = preStmt.executeQuery();
            while (rs.next()) {
                RecipePlan recipePlan = new RecipePlan();
                recipePlan.setId(rs.getInt("id"));
                recipePlan.setRecipe_id(rs.getInt("recipe_id"));
                recipePlan.setMeal_name(rs.getString("meal_name"));
                recipePlan.setDisplay_order(rs.getInt("display_order"));
                recipePlan.setDay_name_id(rs.getInt("day_name_id"));
                recipePlan.setPlan_id(rs.getInt("plan_id"));
                recipePlans.add(recipePlan);
            }
            return recipePlans;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nie mozna wczytac wszystkich wierszy z tabeli recipe_plan");
            return null;
        }
    }

    public static List<RecipePlan> readByPlanId(int planId) {
        try (Connection conn = DbUtil.getConnection()) {
            List<RecipePlan> recipePlans = new ArrayList<>();
            PreparedStatement preStmt = conn.prepareStatement(READ_RECIPE_PLAN_BY_PLAN_ID_QUERY);
            preStmt.setInt(1, planId);
            ResultSet rs = preStmt.executeQuery();
            while (rs.next()) {
                RecipePlan recipePlan = new RecipePlan();
                recipePlan.setId(rs.getInt("id"));
                recipePlan.setRecipe_id(rs.getInt("recipe_id"));
                recipePlan.setMeal_name(rs.getString("meal_name"));
                recipePlan.setDisplay_order(rs.getInt("display_order"));
                recipePlan.setDay_name_id(rs.getInt("day_name_id"));
                recipePlan.setPlan_id(rs.getInt("plan_id"));
                recipePlans.add(recipePlan);
            }
            return recipePlans;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nie mozna wczytac podanych wierszy z tabeli recipe_plan");
            return null;
        }
    }
}
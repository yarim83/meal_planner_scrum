package pl.coderslab.dao;

import pl.coderslab.model.Admin;
import pl.coderslab.utils.DbUtil;
import pl.coderslab.utils.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {
    private static final String CREATE_ADMIN_QUERY = "INSERT INTO admins(first_name, last_name, email, password, superadmin, enable) VALUES (?,?,?,?,?,?);";
    private static final String READ_ADMIN_QUERY = "SELECT * FROM admins WHERE id = ?;";
    private static final String UPDATE_ADMIN_QUERY = "UPDATE admins SET first_name = ?, last_name =?, email = ?, password = ?, superadmin=?, enable =? WHERE id = ?;";
    private static final String DELETE_ADMIN_QUERY = "DELETE FROM admins WHERE id = ?;";
    private static final String FIND_ALL_ADMIN_QUERY = "SELECT * FROM admins;";
    private static final String READ_ADMIN_BY_EMAIL_QUERY = "SELECT * FROM admins WHERE email = ?;";


    public static Admin create(Admin admin) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(CREATE_ADMIN_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preStmt.setString(1, admin.getFirst_name());
            preStmt.setString(2, admin.getLast_name());
            preStmt.setString(3, admin.getEmail());
            preStmt.setString(4, admin.getPassword());
            preStmt.setInt(5, admin.getSuperadmin());
            preStmt.setInt(6, admin.getEnable());
            preStmt.executeUpdate();
            ResultSet rs = preStmt.getGeneratedKeys();
            if (rs.next()) {
                admin.setId(rs.getInt(1));
            }
            return admin;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nie mozna stworzyc wpisu do bazy danych");
            return null;
        }
    }

    public Admin read(int id) {
        try (Connection conn = DbUtil.getConnection()) {
            Admin admin = new Admin();
            PreparedStatement preStmt = conn.prepareStatement(READ_ADMIN_QUERY);
            preStmt.setInt(1, id);
            boolean exist = false;
            for (int i = 0; i < findAll().size(); i++) {
                if (findAll().get(i).getId() == id) {
                    exist = true;
                }
            }
            if (!exist) {
                return null;
            }
            ResultSet rs = preStmt.executeQuery();
            if (rs.next()) {
                admin.setId(rs.getInt("id"));
                admin.setFirst_name(rs.getString("first_name"));
                admin.setLast_name(rs.getString("last_name"));
                admin.setEmail(rs.getString("email"));
                admin.setPassword(rs.getString("password"));
                admin.setSuperadmin(rs.getInt("superadmin"));
                admin.setEnable(rs.getInt("enable"));
            }
            return admin;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nie mozna wczytac wiersza o id: " + id);
            return null;
        }
    }

    public void update(Admin admin) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(UPDATE_ADMIN_QUERY);
            preStmt.setString(1, admin.getFirst_name());
            preStmt.setString(2, admin.getLast_name());
            preStmt.setString(3, admin.getEmail());
            preStmt.setString(4, admin.getPassword());
            preStmt.setInt(5, admin.getSuperadmin());
            preStmt.setInt(6, admin.getEnable());
            preStmt.setInt(7, admin.getId());
            preStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nie mozna zaktualzowac wiersza o id: " + admin.getId());
        }
    }


    public void delete(int id) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(DELETE_ADMIN_QUERY);
            preStmt.setInt(1, id);
            preStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nie mozna usunac wiersza z tablicy");
        }
    }

    public static List<Admin> findAll() {
        try (Connection conn = DbUtil.getConnection()) {
            List<Admin> admins = new ArrayList<>();
            PreparedStatement preStmt = conn.prepareStatement(FIND_ALL_ADMIN_QUERY);
            ResultSet rs = preStmt.executeQuery();
            while (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setFirst_name(rs.getString("first_name"));
                admin.setLast_name(rs.getString("last_name"));
                admin.setEmail(rs.getString("email"));
                admin.setPassword(rs.getString("password"));
                admin.setSuperadmin(rs.getInt("superadmin"));
                admin.setEnable(rs.getInt("enable"));
                admins.add(admin);
            }
            return admins;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nie mozna wczytac wszystkich wierszy z tabeli admins");
            return null;
        }
    }

    public static boolean checkLoginData(String email, String password) {
        try (Connection conn = DbUtil.getConnection()) {
            Admin admin = new Admin();
            PreparedStatement preStmt = conn.prepareStatement(READ_ADMIN_BY_EMAIL_QUERY);
            preStmt.setString(1, email);
            boolean exist = false;
            for (int i = 0; i < findAll().size(); i++) {
                if (email.equals(findAll().get(i).getEmail())) {
                    exist = true;
                }
            }
            if(!exist){
                return false;
            }

            ResultSet rs = preStmt.executeQuery();
            if (rs.next()) {
                admin.setId(rs.getInt("id"));
                admin.setFirst_name(rs.getString("first_name"));
                admin.setLast_name(rs.getString("last_name"));
                admin.setEmail(rs.getString("email"));
                admin.setPassword(rs.getString("password"));
                admin.setSuperadmin(rs.getInt("superadmin"));
                admin.setEnable(rs.getInt("enable"));
            }

            boolean passwordMatch = PasswordUtil.checkPasswordMatch(password, admin.getPassword());

            if (exist && passwordMatch) {
                return true;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nie mozna wczytac wiersza");
            return false;
        }
    }

}

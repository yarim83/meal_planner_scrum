package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.DayName;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DayNameDao {
    //SQL Query
    private static final String CREATE_DAY_NAME_QUERY = "INSERT INTO day_name(name,display_order) VALUES (?,?,?);";
    private static final String DELETE_DAY_NAME_QUERY = "DELETE FROM day_name WHERE id = ?;";
    private static final String FIND_ALL_DAY_NAME_QUERY = "SELECT * FROM day_name;";
    private static final String READ_DAY_NAME_QUERY = "SELECT * FROM day_name WHERE id = ?;";
    private static final String UPDATE_DAY_NAME_QUERY = "UPDATE day_name SET name = ?, display_order =? WHERE id = ?;";

    /**
     * Get dayName id
     *
     * @param dayNameId
     * @return
     */
    public DayName read(Integer dayNameId) {
        DayName dayName = new DayName();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_DAY_NAME_QUERY)) {
            statement.setInt(1, dayNameId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    dayName.setId(resultSet.getInt("id"));
                    dayName.setName(resultSet.getString("name"));
                    dayName.setDisplayOrder(resultSet.getInt("display_order"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dayName;
    }

    /**
     * Return all dayName
     *
     * @return
     */

    public List<DayName> findAll() {
        List<DayName> dayNames = new ArrayList<>();

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_DAY_NAME_QUERY);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                DayName dayNamesToAdd = new DayName();
                dayNamesToAdd.setId(resultSet.getInt("id"));
                dayNamesToAdd.setName(resultSet.getString("name"));
                dayNamesToAdd.setDisplayOrder(resultSet.getInt("display_order"));
                dayNames.add(dayNamesToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dayNames;
    }

    /**
     * Create dayName
     *
     * @param dayName
     * @return
     */

    public DayName create(DayName dayName) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_DAY_NAME_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, dayName.getName());
            statement.setInt(2, dayName.getDisplayOrder());
            int result = statement.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Executed update returned: " + result);
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    dayName.setId(generatedKeys.getInt(1));
                    return dayName;
                } else {
                    throw new RuntimeException("Genereted keys was not find");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Remove dayName by id
     *
     * @param dayNameId
     */
    private void delete(Integer dayNameId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_DAY_NAME_QUERY)) {
            statement.setInt(1, dayNameId);
            statement.executeUpdate();

            boolean deleted = statement.execute();

            if (!deleted) {
                throw new NotFoundException("Product not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update dayName
     *
     * @param dayName
     */
    public void update(DayName dayName) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_DAY_NAME_QUERY)
        ) {
            statement.setString(1, dayName.getName());
            statement.setInt(2, dayName.getDisplayOrder());
            statement.setInt(3, dayName.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}

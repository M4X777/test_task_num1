package task.repository;

import lombok.extern.log4j.Log4j;
import task.model.Well;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j
public class WellRepository {

    public Well findWellByName(Connection connection, String wellName) {
        log.debug("Call method findWellByName() with wellName = " + wellName);
        Well well = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT id, name FROM Well")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (wellName.equals(resultSet.getString("name"))) {
                    well = new Well(resultSet.getInt("id"), resultSet.getString("name"));
                    break;
                }
            }
        } catch (SQLException e) {
            log.error("ERROR_FIND_WELL_BY_NAME", e);
            throw new RuntimeException(e);
        }
        return well;
    }

    public void addWell(Connection connection, String wellName) {
        log.debug("Call method addWell() with wellName = " + wellName);
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO Well(`name`) " + "VALUES(?)")) {
            statement.setObject(1, wellName.toUpperCase());
            statement.execute();
        } catch (SQLException e) {
            log.error("ERROR_ADD_WELL", e);
            throw new RuntimeException(e);
        }
    }
}

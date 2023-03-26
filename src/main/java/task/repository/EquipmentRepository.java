package task.repository;

import lombok.extern.log4j.Log4j;
import task.model.Equipment;
import task.model.Well;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

@Log4j
public class EquipmentRepository {

    public void addEquipment(Connection connection, Well well, String amountEquipment) {
        log.debug("Call method addEquipment() with well = " + well + ", amountEquipment = " + amountEquipment);
        try {
            int amountInt = Integer.valueOf(amountEquipment);
            for (int i = 1; i <= amountInt; i++) {
                int randomNum = ThreadLocalRandom.current().nextInt(1, 9999);
                String numberEquipment = String.format("%04d", randomNum);
                PreparedStatement statement = connection.prepareStatement("INSERT INTO Equipment(`name`, `well_id`) " + "VALUES(?, ?)");
                statement.setObject(1, "EQ" + numberEquipment);
                statement.setObject(2, well.getId());
                statement.execute();
            }
        } catch (SQLException e) {
            log.error("ERROR_ADD_EQUIPMENT", e);
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> findAllEquipmentByWell(Connection connection, String[] wellNames) {
        log.debug("Call method findAllEquipmentByWell() with wellNames = " + wellNames);
        ArrayList<String> resultList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT Well.name, COUNT(E.id) AS amount FROM Well JOIN Equipment E on Well.id = E.well_id WHERE Well.name = ?")) {
            for (int i = 0; i < wellNames.length; i++) {
                statement.setString(1, wellNames[i]);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    if (resultSet.getString("name") != null) // Костыль :)
                        resultList.add(resultSet.getString("name") + ", " + resultSet.getInt(2));
                }
            }
        } catch (SQLException e) {
            log.error("ERROR_FIND_ALL_EQUIPMENT_BY_WELL", e);
            throw new RuntimeException(e);
        }
        return resultList;
    }

    public HashMap<Well, ArrayList<Equipment>> findAllWellAndEquipment(Connection connection) {
        log.debug("Call method findAllWellAndEquipment()");
        HashMap<Well, ArrayList<Equipment>> resultMap = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT Well.*, E.name AS E_name, E.id AS E_id FROM Well JOIN Equipment E ON Well.id = E.well_id")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Well well = new Well(resultSet.getInt("id"), resultSet.getString("name"));
                Equipment equipment = new Equipment(resultSet.getInt("E_id"), resultSet.getString("E_name"));
                if (!resultMap.containsKey(well)) {
                    resultMap.put(well, new ArrayList<Equipment>());
                    resultMap.get(well).add(equipment);
                } else
                    resultMap.get(well).add(equipment);
            }
        } catch (SQLException e) {
            log.error("ERROR_FIND_ALL_WELL_AND_EQUIPMENT", e);
            throw new RuntimeException(e);
        }
        return resultMap;
    }
}

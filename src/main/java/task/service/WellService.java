package task.service;

import lombok.extern.log4j.Log4j;
import task.repository.EquipmentRepository;

import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

@Log4j
public class WellService {

    private MainService mainService = new MainService();
    private EquipmentRepository equipmentRepository = new EquipmentRepository();

    public boolean checkWellFields(String wellNames) {
        log.debug("Call method checkWellFields() with wellNames = " + wellNames);
        if (wellNames.trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "It is necessary to write the name of the wells!");
            return false;
        }
        return true;
    }

    public int parseWellNames(Connection connection, String wellNames) {
        log.debug("Call method parseWellNames() with wellNames = " + wellNames);
        String[] arrayWellNames = Arrays.stream(wellNames.split(","))
                .map(String::trim)
                .map(i -> i.split(" "))
                .flatMap(Arrays::stream)
                .toArray(String[]::new);
        ArrayList<String> resultList = equipmentRepository.findAllEquipmentByWell(connection, arrayWellNames);
        if (resultList.size() == 0) {
            JOptionPane.showMessageDialog(null, "No wells found for your request!");
        } else {
            mainService.openAllEquipmentsByWellView(resultList);
        }
        return resultList.size();
    }
}

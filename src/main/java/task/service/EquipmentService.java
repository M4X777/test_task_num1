package task.service;

import lombok.extern.log4j.Log4j;
import task.model.Well;
import task.repository.EquipmentRepository;
import task.repository.WellRepository;

import javax.swing.*;
import java.sql.Connection;

@Log4j
public class EquipmentService {

    private EquipmentRepository equipmentRepository = new EquipmentRepository();
    private WellRepository wellRepository = new WellRepository();

    public Well createWellEquipment(Connection connection, String amountEquipment, String wellName) {
        log.debug("Call method createWellEquipment() with amountEquipment = " + amountEquipment + ", wellName = " + wellName);
        Well well = wellRepository.findWellByName(connection, wellName.toUpperCase());
        if (well == null) {
            wellRepository.addWell(connection, wellName.toUpperCase());
            well = wellRepository.findWellByName(connection, wellName.toUpperCase());
            equipmentRepository.addEquipment(connection, well, amountEquipment);
            JOptionPane.showMessageDialog(null, "The well with name \"" + wellName.toUpperCase() + "\" has been successfully created!");
            return well;
        } else {
            JOptionPane.showMessageDialog(null, "A well with name \"" + wellName.toUpperCase() + "\" already exists!");
        }
        return null;
    }

    public boolean checkEquipmentFields(String amountEquipment, String wellName) {
        log.debug("Call method checkEquipmentFields() with amountEquipment = " + amountEquipment + ", wellName = " + wellName);
        if (amountEquipment.trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "It is necessary to write the amount of equipment on the well!");
            return false;
        }
        if (wellName.trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "It is necessary to write the name of the well!");
            return false;
        }
        return true;
    }
}

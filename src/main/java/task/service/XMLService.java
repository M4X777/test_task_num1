package task.service;

import lombok.extern.log4j.Log4j;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import task.model.Equipment;
import task.model.Well;
import task.repository.EquipmentRepository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Log4j
public class XMLService {

    private EquipmentRepository equipmentRepository = new EquipmentRepository();

    public void saveToXML(Connection connection, File selectedFile) {
        log.debug("Call method saveToXML() with selectedFile = " + selectedFile);
        HashMap<Well, ArrayList<Equipment>> resultMap = equipmentRepository.findAllWellAndEquipment(connection);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement(selectedFile.getName());
            document.appendChild(rootElement);
            for (Map.Entry<Well, ArrayList<Equipment>> entry : resultMap.entrySet()) {
                Element well = document.createElement("well");
                rootElement.appendChild(well);

                Attr attributeWellId = document.createAttribute("id");
                attributeWellId.setValue(String.valueOf(entry.getKey().getId()));

                Attr attributeWellName = document.createAttribute("name");
                attributeWellName.setValue(entry.getKey().getName());

                well.setAttributeNode(attributeWellName);
                well.setAttributeNode(attributeWellId);

                for (Equipment equipment : entry.getValue()) {
                    Element equipmentElement = document.createElement("equipment");

                    Attr attributeEqId = document.createAttribute("id");

                    Attr attributeEqName = document.createAttribute("name");
                    attributeEqName.setValue(equipment.getName());

                    attributeEqId.setValue(String.valueOf(equipment.getId()));

                    equipmentElement.setAttributeNode(attributeEqName);
                    equipmentElement.setAttributeNode(attributeEqId);

                    well.appendChild(equipmentElement);
                }
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(selectedFile);
                transformer.transform(source, result);
            }
        } catch (ParserConfigurationException | TransformerException e) {
            log.error("ERROR_SAVE_TO_XML", e);
            throw new RuntimeException(e);
        }
    }
}

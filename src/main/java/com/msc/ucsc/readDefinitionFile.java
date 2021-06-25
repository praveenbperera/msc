package com.msc.ucsc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class readDefinitionFile {
    private static final Logger logger = LogManager.getLogger(readDefinitionFile.class);

    public String readFile(String nodeName, String value) throws IOException {
        logger.debug("Entered to the method readFile() ");

        String returnValue = "NULL";
        try {
            logger.debug("Entered try catch ");
            File file = new File("files/apivalidator.xml");

            logger.debug("Start reading apivalidator.xml file");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            logger.debug("Root element is " + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName(nodeName);

            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                logger.debug("Entered for-loop ");
                Node node = nodeList.item(itr);
                logger.debug("Searching sub element name " + node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    returnValue = eElement.getElementsByTagName(value).item(0).getTextContent();
                    logger.debug("value found > returnValue: " + returnValue);

                }
            }
            logger.debug("Exited from for-loop ");

        } catch (NullPointerException | ParserConfigurationException | SAXException e) {
            logger.debug("value Not found > " + returnValue + e);
            e.printStackTrace();
        }
        logger.debug("Finished reading apivalidator.xml file");
        return returnValue;

    }


}



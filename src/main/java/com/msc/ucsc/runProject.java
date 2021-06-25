package com.msc.ucsc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


public class runProject {
    public static String outputFilePath,inputFilePath;
    private static final Logger logger = LogManager.getLogger(runProject.class);
    public static void main(String[] args) throws IOException {
        guiFrame gf = new guiFrame();
        filePath fp = new filePath();

        logger.debug("************ Start executing main method **********");
        fp.setOutputfilepath(System.getProperty("user.home") + "/Desktop");
        gf.gui();
        logger.debug("************ Completed executing main method **********");
    }


}

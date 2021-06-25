package com.msc.ucsc;

import com.google.gson.Gson;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.msc.ucsc.runProject.inputFilePath;
import static com.msc.ucsc.runProject.outputFilePath;

public class filePath {
    private static final Logger logger = LogManager.getLogger(runProject.class);

    public void setOutputfilepath(String filePath) {

        logger.debug("Setting up outputfilepath");
        outputFilePath = filePath + "/output.csv";
        logger.debug("Completed Setting up outputfilepath : " + outputFilePath);

    }

    public void setInputfilepath(String filePath) throws IOException {

        logger.debug("Setting up inputfilepath");
        if (checkInputFile(filePath)) {
            inputFilePath = filePath;
            logger.debug("Completed Setting up inputfilepath : " + inputFilePath);
        }
    }

    public boolean checkInputFile(String inpath) throws IOException {
        // String inpath = "target/output_file/input.json";
        boolean check1 = false;
        boolean check2 = false;
        boolean finalCheck = false;
        final Gson gson = new Gson();
        logger.debug("Entering to the method isJSONValid()");
        String json = new String(Files.readAllBytes(Paths.get(inpath)), "utf-8");
        try {
            gson.fromJson(json, Object.class);
            logger.debug("Given file has a valid json body");
            check1 = true;

        } catch (com.google.gson.JsonSyntaxException ex) {
            logger.debug("Given file has NOT a valid json body");
            check1 = false;
        }

        try {
            if (FilenameUtils.getExtension(inpath).equals("json")) {
                check2 = true;
                logger.debug("Given file extention is valid - json ");
            }
        } catch (Exception ex) {
            logger.debug("Given file extention is NOT valid : " + FilenameUtils.getExtension(inpath));
            check2 = false;
        }

        if (check1 && check2) {
            finalCheck = true;
            logger.debug("Given file is a valid json file");
        }

        return finalCheck;
    }
}

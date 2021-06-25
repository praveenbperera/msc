package com.msc.ucsc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import com.google.gson.Gson;
import static com.msc.ucsc.runProject.inputFilePath;
public class readFromJsonFile {

    private static final Logger logger = LogManager.getLogger(readFromJsonFile.class);

    //String inPutFilepath = "target/output_file/input.json";

    public boolean isJSONValid(String jsonValue) throws IOException {
        final Gson gson = new Gson();
        logger.debug("Entering to the method isJSONValid()");
        try {
            gson.fromJson(jsonValue, Object.class);
            logger.debug("Given file is a valid json file");
            return true;

        } catch (com.google.gson.JsonSyntaxException ex) {
            logger.debug("Given file is NOT a valid json file");
            return false;
        }

    }

    public String valueFromJson() throws IOException {
        logger.debug("Entering to the method valueFromJson()");
        try {
            String json = new String(Files.readAllBytes(Paths.get(inputFilePath)), "utf-8");

            if (isJSONValid(json)) {
                logger.debug("Getting file to a json object");
                JSONObject jobject = new JSONObject(json);
                getJson(jobject);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        logger.debug("Exiting from the method valueFromJson()");
        return null;
    }

    public void getJson(JSONObject jobject) {
        logger.debug("Entering to the method getJson()");

        Iterator<?> keys;
        String nextKeys;
        keys = jobject.keys();
        try {
            while (keys.hasNext()) {
                nextKeys = (String) keys.next();
                if (nextKeys.equals("swagger")) {
                    logger.debug("Calling to the validateSwaggerVersion() method");
                    validateSwaggerVersion((String) jobject.get(nextKeys));
                } else if (nextKeys.equals("info")) {
                    logger.debug("Calling to the validatingInfo() method");
                    validatingInfo(jobject.getJSONObject(nextKeys));
                } else if (nextKeys.equals("paths")) {
                    logger.debug("Calling to the validatingPath() method");
                    validatingPath(jobject.getJSONObject(nextKeys), nextKeys);
                } else if (!nextKeys.isEmpty()) {

                }

            }
        } catch (Exception e) {
            logger.debug("Error in the method getJson() : " + e);
        }
        logger.debug("Exiting from the method getJson()");
    }

    public void validateSwaggerVersion(String swaggerVersion) {
        logger.debug("Entering to the method validateSwaggerVersion()");
        createTheFile ctf = new createTheFile();
        String sampleData = swaggerVersion;

        try {
            logger.debug("Sending data to write test case from validateSwaggerVersion()");
            ctf.writeTestCase("SWAGGER", "swagger_version", " ", sampleData);
            logger.debug("Successfully Sent data to write test case from validateSwaggerVersion()");
        } catch (Exception e) {
            logger.debug("Error in the method validateSwaggerVersion() : " + e);
        }
        logger.debug("Exiting to the method validateSwaggerVersion()");
    }

    public void validatingInfo(JSONObject jobject) {
        logger.debug("Entering to the method validatingInfo()");
        createTheFile ctf = new createTheFile();
        Iterator<?> keys;
        keys = jobject.keys();
        while (keys.hasNext()) {
            String nextKeys = (String) keys.next();
            try {
                if (jobject.get(nextKeys) instanceof JSONObject) {
                    validatingInfo(jobject.getJSONObject(nextKeys));

                } else {
                    String value = (String) jobject.get(nextKeys);
                    logger.debug("Sending data to write the test case");
                    ctf.writeTestCase("INFO", nextKeys, " ", value);
                    logger.debug("Sent data to write the test case");
                }

            } catch (Exception e) {
                logger.debug("Error in the validatingInfo() method : " + e);
            }
        }
        logger.debug("Exiting from the method validatingInfo()");
    }

    public void validatingPath(JSONObject jobject, String nextKeys) {
        logger.debug("Entering to the method validatingPath()");
        Iterator<?> keys;
        keys = jobject.keys();

        while (keys.hasNext()) {
            String pattern;
            try {
                nextKeys = (String) keys.next();
                pattern = nextKeys;

                if (jobject.get(nextKeys) instanceof JSONObject) {
                    JSONObject jo = (JSONObject) jobject.get(nextKeys);

                    Iterator<?> keysub;
                    keysub = jo.keys();

                    while (keysub.hasNext()) {
                        String nextKeysub = (String) keysub.next();
                        logger.debug("Sending data to write the test case");
                        writeMethods(jo.getJSONObject(nextKeysub), nextKeysub, pattern);
                        logger.debug("Sent data to write the test case");
                    }
                }

            } catch (Exception e) {

            }
        }
        logger.debug("Exited to the method validatingPath()");
    }

    public void writeMethods(JSONObject jobject, String nextKeys, String pattern) {
        logger.debug("Entering to the method writeMethods()");
        String methodType = nextKeys;
        String testData;
        createTheFile ctf = new createTheFile();

        Iterator<?> keys;
        keys = jobject.keys();
        String nextkey;
        while (keys.hasNext()) {

            try {
                nextKeys = (String) keys.next();
                nextkey = nextKeys;

                if (jobject.get(nextKeys) instanceof JSONObject) {
                    testData = jobject.getJSONObject(nextKeys).toString();
                    logger.debug("Sending data to write the test case");
                    ctf.writeTestCase("PATH", methodType.toUpperCase(), " \"" + pattern + "\" with response containing " + nextkey, nextkey + " : " + testData);
                    logger.debug("Sent data to write the test case");
                } else {
                    testData = jobject.getString(nextKeys);
                    logger.debug("Sending data to write the test case");
                    ctf.writeTestCase("PATH", methodType.toUpperCase(), " \"" + pattern + "\" with response containing " + nextkey, nextkey + " : " + testData);
                    logger.debug("Sent data to write the test case");
                }

            } catch (Exception e) {

            }
        }
        logger.debug("Exiting from the method writeMethods()");

    }
}

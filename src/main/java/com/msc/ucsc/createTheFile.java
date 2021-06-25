package com.msc.ucsc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

import static com.msc.ucsc.runProject.outputFilePath;

public class createTheFile {
    private static final Logger logger = LogManager.getLogger(createTheFile.class);
  //  String outPutFilepath = "target/output_file/output.csv";
    readDefinitionFile rdf = new readDefinitionFile();
    testcaseNumber tcn = new testcaseNumber();


    public void deleteFile()  {

        logger.debug("Entered to the method deleteFile()");

        File file = new File(outputFilePath);
        if (file.exists()) {
            file.delete();
            logger.debug("File existed in the location : "+outputFilePath);
        }
        logger.debug("Completed the method deleteFile()");
    }

    public void createAndsetHeader() throws IOException {

        logger.debug("Entered to the method createAndsetHeader() ");
        logger.debug("Calling method deleteFile() ");
        deleteFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, true));
        logger.debug("Creating file and setting up the headers in the CSV file");
        String testCase = "Test Case Number " + ",Test Case" + ",Test Data";
        writer.newLine();
        writer.append(testCase);
        writer.close();
        logger.debug("Completed creating file and setting up the headers in the CSV file");
    }

    public void writeTestCase(String validatormainnode, String validatorsubnode, String pattern, String testdata) throws IOException {

        logger.debug("Entered to the method writeTestCase()");
        String finalDefFileValue = null;

        try {

            logger.debug("Start reading from definition file");
            String defFileValue = rdf.readFile(validatormainnode, validatorsubnode);
            if (!defFileValue.equals("NULL")) {
                finalDefFileValue = "["+validatormainnode+"] "+defFileValue+pattern;
                logger.debug("Retrieved value from definition file");

            } else if (defFileValue.equals("NULL")) {
                finalDefFileValue = "["+validatormainnode+"] "+"[WARNING] No definition found, Definition : " + validatorsubnode ;
                logger.debug("Parsed definition value is Null  or new definition found, main node : " + validatormainnode + " , sub node : " + validatorsubnode);
            }
            logger.debug("Successfully read the definition file");

        } catch (Exception e) {
            logger.debug("Error reading definition file " + e);
        }

        String sampleData = null;

        if (!testdata.equals(null)) {
            logger.debug("Sample data is not null");
            sampleData = ", Sample Data - " + testdata;
        }

        logger.debug("Getting the next test case number and setup");
        String nextTcNumber = "TC_" + tcn.nextTestcaseNumber(outputFilePath) + " ,";
        logger.debug("Successfully got the next test case number");

        logger.debug("Starting to write the test case");
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, true));

        if (!finalDefFileValue.equals(null)) {
            logger.debug("Finalizing the test case");
            String testCase = nextTcNumber + finalDefFileValue + sampleData;
            try {
                logger.debug("Writing the test case");
                writer.newLine();
                writer.append(testCase);
                writer.close();
                logger.debug("Successfully wrote the test case ");
            } catch (Exception e) {
                logger.debug("Error writing test case " + e);
            }
        }

    }


}

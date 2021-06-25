package com.msc.ucsc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class testcaseNumber {
    private static final Logger logger = LogManager.getLogger(testcaseNumber.class);

    public String nextTestcaseNumber(String outPutFilepath) throws IOException {

        logger.debug("Entered to the method nextTestcaseNumber()");
        String nextValue = "001";

        logger.debug("Reading the output file");
        FileInputStream in = new FileInputStream(outPutFilepath);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String strLine = null, tmp;

        while ((tmp = br.readLine()) != null) {
            strLine = tmp;
        }

        String lastLine = strLine;

        in.close();

        if (lastLine.length() > 3 & !lastLine.isEmpty() & lastLine.contains("TC_")) {
            logger.debug("Getting the last test case number");
            int tmpv = Integer.parseInt(lastLine.substring(3, 6));
            logger.debug("Setting the next test case number by incrementing by 1");
            tmpv = tmpv + 1;
            logger.debug("Complete setting the next test case number");
            nextValue = String.format("%03d", tmpv);

        }
        logger.debug("Exiting from the method nextTestcaseNumber()");
        return nextValue;

    }
}

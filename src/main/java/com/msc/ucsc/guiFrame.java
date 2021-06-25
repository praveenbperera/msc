package com.msc.ucsc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JFrame;

import static com.msc.ucsc.runProject.outputFilePath;

public class guiFrame extends JFrame implements ActionListener {
    private static final Logger logger = LogManager.getLogger(guiFrame.class);
    JButton button1, button2, button3, button4, button5;
    JLabel welcomeMessage = new JLabel();
    JLabel label01, label02, label03, label04, label05, label06;
    filePath fp = new filePath();

    public void gui() {

        JFrame frame = new JFrame("Test Case Writer");

        welcomeMessage.setText("<html><b> Welcome to the Test case Generator - v1.0</b></html>");
        welcomeMessage.setBounds(45, 5, 1000, 100);
        welcomeMessage.setFont(new Font(null, Font.BOLD, 25));


        button1 = new JButton("Generate");
        button2 = new JButton("Close");
        button3 = new JButton("Browse");
        button4 = new JButton("Browse");
        button5 = new JButton("Edit");

        //Generate button
        button1.setBounds(200, 320, 120, 25);
        button1.addActionListener(this);
        button1.setFocusable(false);
        button1.setEnabled(false);

        //Cancel button
        button2.setBounds(340, 320, 120, 25);
        button2.addActionListener(this);
        button2.setFocusable(false);

        //Browser button to select input file
        button3.setBounds(520, 110, 100, 25);
        button3.addActionListener(this);
        button3.setFocusable(false);

        //Browser button to select output file path
        button4.setBounds(520, 160, 100, 25);
        button4.addActionListener(this);
        button4.setFocusable(false);

        //Edit button to select output file path
        button5.setBounds(520, 250, 100, 25);
        button5.addActionListener(this);
        button5.setFocusable(false);

        label01 = new JLabel();
        label01.setBounds(25, 128, 900, 25);
        label01.setVisible(false);
        label01.setFont(new Font("Serif", Font.PLAIN, 14));

        label02 = new JLabel();
        label02.setBounds(25, 110, 900, 25);
        label02.setText("<html>Please select a valid REST API Json file... Eg : Test.json &nbsp;&nbsp;    : <font color='red'>*</font></html>");
        label02.setFont(new Font("Serif", Font.BOLD, 15));

        label03 = new JLabel();
        label03.setBounds(25, 160, 800, 25);
        label03.setText("<html>Please select a directory to save the test case document : </html>");
        label03.setFont(new Font("Serif", Font.BOLD, 15));

        label04 = new JLabel();
        label04.setBounds(25, 180, 580, 25);
        label04.setText("Default file path : " + outputFilePath);
        label04.setForeground(Color.GRAY);
        label04.setFont(new Font("Serif", Font.BOLD, 14));

        label05 = new JLabel();
        label05.setBounds(25, 250, 800, 25);
        label05.setText("You can also edit the Json file using Swagger Editor >> ");
        label05.setForeground(Color.DARK_GRAY);
        label05.setFont(new Font("Serif", Font.BOLD, 14));

        label06 = new JLabel();
        label06.setBounds(120, 340, 500, 100);
        label06.setText("<html><body><pre>               &copy;praveenbperera@gmail.com <br> " +
                "[Index No :18440644 || MCS 3204-Individual Project-UCSC]</pre></body></html>");
        label06.setForeground(new Color(7, 24, 173));
        label06.setFont(new Font("Franktur", Font.BOLD, 12));


        frame.add(button1);
        frame.add(button2);
        frame.add(button3);
        frame.add(button4);
        frame.add(button5);
        frame.add(welcomeMessage);
        frame.add(label01);
        frame.add(label02);
        frame.add(label03);
        frame.add(label04);
        frame.add(label05);
        frame.add(label06);

        frame.setResizable(false);
        setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 450);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(new Color(189, 208, 252));

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // String action = ae.getActionCommand();
        if (ae.getSource() == button1) {
            logger.debug("Generate button pressed ");
            createTheFile ctf = new createTheFile();
            readFromJsonFile rfjf = new readFromJsonFile();
            try {
                ctf.createAndsetHeader();
                rfjf.valueFromJson();
                Thread.sleep(2000);
                JOptionPane.showMessageDialog(null, "Successfully Generated", "Test Case Writer", JOptionPane.INFORMATION_MESSAGE);
                logger.debug("--------- Test cases and document generated successfully  --------");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Sorry, Unable to Generate...", "Test Case Writer", JOptionPane.INFORMATION_MESSAGE);
                logger.debug("???????? Error executing Methods ????????" + e);
            }

        }
        if (ae.getSource() == button2) {
            System.exit(0);
            logger.debug("Close window button pressed.. Closing the window ");
        }
        if (ae.getSource() == button3) {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new java.io.File("."));
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

                String s = String.valueOf(file);
                logger.debug("Start validating selected input file " + s);
                try {
                    if (fp.checkInputFile(String.valueOf(s))) {
                        fp.setInputfilepath(s);
                        logger.debug("Correct file type selected" + s);
                        label01.setText(s);
                        label01.setForeground(new Color(34, 156, 25));
                        label01.setVisible(true);
                        button1.setEnabled(true);
                    } else {
                        label01.setText("Invalid file or file type, Please select valid json file...");
                        label01.setForeground(Color.RED);
                        label01.setVisible(true);
                        button1.setEnabled(false);
                        logger.debug("Invalid file type selected");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.debug("Error occured validating selected input file " + s);
                }
                logger.debug("Completed validating selected input file " + s);
            }
        }
        if (ae.getSource() == button4) {
            logger.debug("Adding the csv file save directory path");

            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("choosertitle");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                logger.debug("User selected the path and sending to add the csv file save directory path");
                String s = String.valueOf(chooser.getSelectedFile());
                logger.debug("Sending data to save output file path");
                fp.setOutputfilepath(s);
                logger.debug("Completed Sending data to save output file path");
                logger.debug("Displaying message as output file path");
                label04.setText(s);
                label04.setForeground(new Color(34, 156, 25));
                label04.setVisible(true);
            } else {
                logger.debug("Output file path not selected");
            }
        }
        if (ae.getSource() == button5) {
            try {
                logger.debug("Clicking and opening browser to navigate Swagger io URl ");
                Desktop browser = Desktop.getDesktop();
                browser.browse(new URI("https://editor.swagger.io/"));
                logger.debug("Opened browser to navigate Swagger io URl ");
            } catch (Exception e) {
                logger.debug("Error navigating Swagger io URl : " + e);
            }
        }
    }


}

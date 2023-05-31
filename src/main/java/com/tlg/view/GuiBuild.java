package com.tlg.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GuiBuild {

    private JFrame frame;
    private JPanel titleNamePanel, startButtonPanel, mainTextPanel, choiceButtonPanel;
    private JTextArea mainTextArea;
    private Container con;
    private JLabel titleNameLabel;
    private JPanel panel1;
    private Font titleFont = new Font("Times New Roman", Font.PLAIN, 60);
    private Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
    private JButton startButton, choice1, choice2, choice3, choice4;

        private GuiBuild() {
            // Create and set up the window.
            frame = new JFrame("Heartsoar Tower");
            frame.setSize(800, 900);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setBackground(Color.BLACK);
            frame.setLayout(null);

            // Get the content pane of the frame
            con = frame.getContentPane();
            frame.setTitle("HEARTSOAR TOWER");

            // Create a panel for the title name
            titleNamePanel = new JPanel();
            titleNamePanel.setBounds(100, 100, 600, 150);
            titleNamePanel.setBackground(Color.BLACK);

            // Set the layout manager of the titleNamePanel to center the components
            titleNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

            // Create the title name label
            titleNameLabel = new JLabel("HEARTSORE TOWER");
            titleNameLabel.setForeground(Color.WHITE); // Text color
            titleNameLabel.setFont(titleFont);

            titleNamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "EnterPressed");
            titleNamePanel.getActionMap().put("EnterPressed", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    createGameScreen();
                }
            });

//            // Create button panel
            startButtonPanel = new JPanel();
            startButtonPanel.setBounds(300, 400, 200, 100);
            startButtonPanel.setBackground(Color.BLACK);

            // Create the start button
            startButton = new JButton("New Game");
            startButton.setBackground(Color.YELLOW);
            startButton.setForeground(Color.RED);
            startButton.setFont(normalFont);
            startButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    createGameScreen();
                }
            });

            titleNamePanel.add(titleNameLabel); // Add the label to the panel
            con.add(titleNamePanel); // Add the title name panel to the content pane
            con.add(startButtonPanel);
            //startButtonPanel.add(startButton);

            frame.setVisible(true);
        }

    public void createGameScreen() {

        //HIDE TITLE SCREEN
        titleNamePanel.setVisible(false);
        startButtonPanel.setVisible(false);

        //TEXT PANEL
        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(100, 100, 600, 250);
        mainTextPanel.setBackground(Color.BLACK);
        con.add(mainTextPanel);

        //TEXT AREA
        mainTextArea = new JTextArea("This is the main game text area");
        mainTextArea.setBounds(100, 100, 600, 250);
        mainTextArea.setBackground(Color.black);
        mainTextArea.setForeground(Color.WHITE);
        mainTextArea.setFont(normalFont);
        mainTextArea.setLineWrap(true);
        mainTextArea.setWrapStyleWord(true);
        mainTextArea.setEditable(false);
        mainTextPanel.add(mainTextArea); //adding text area to text panel

        choiceButtonPanel = new JPanel();
        choiceButtonPanel.setBounds(250, 350, 300, 150);
        choiceButtonPanel.setBackground(Color.BLACK);
        choiceButtonPanel.setLayout(new GridLayout(4, 1));
        con.add(choiceButtonPanel);

        choice1 = new JButton("Choice 1");
        choice1.setBackground(Color.BLACK);
        choice1.setForeground(Color.black);
        choice1.setFont(normalFont);
        choiceButtonPanel.add(choice1);

        choice2 = new JButton("Choice 2");
        choice2.setBackground(Color.BLACK);
        choice2.setForeground(Color.black);
        choice2.setFont(normalFont);
        choiceButtonPanel.add(choice2);

        choice3 = new JButton("Choice 3");
        choice3.setBackground(Color.BLACK);
        choice3.setForeground(Color.black);
        choice3.setFont(normalFont);
        choiceButtonPanel.add(choice3);

        choice4 = new JButton("Choice 4");
        choice4.setBackground(Color.BLACK);
        choice4.setForeground(Color.black);
        choice4.setFont(normalFont);
        choiceButtonPanel.add(choice4);
    }



    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGUI();
//            }
//        });
//    }
        new GuiBuild();
    }
}

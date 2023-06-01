package com.tlg.view;

import com.tlg.controller.AlwaysCommands;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.tlg.controller.AlwaysCommands.*;
import static com.tlg.controller.NewGame.newGame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import static com.tlg.controller.AlwaysCommands.musicSettings;

public class GuiBuild {

    private JFrame frame;
    private JPanel titleNamePanel, musicButtonPanel, mainTextPanel, choiceButtonPanel, playerPanel,
    choiceTextPanel, helpPanel, instructionPanel;
    private JTextArea mainTextArea, choiceTextArea, instructionTextArea;
    private Container con;
    private JLabel titleNameLabel, playerLabel, artLabel;

    private Font titleFont = new Font("Times New Roman", Font.PLAIN, 60);
    private Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
    private Font storyFont = new Font("Times New Roman", Font.PLAIN, 20);
    private JButton musicButton, helpButton;
    private MusicPlayer musicPlayer;

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
                    createInstructionScreen();
                }
            });

//            // Create button panel
            musicButtonPanel = new JPanel();
            musicButtonPanel.setBounds(300, 400, 200, 100);
            musicButtonPanel.setBackground(Color.BLACK);

            // Create the start button
            musicButton = new JButton("music");
            musicButton.setBackground(Color.YELLOW);
            musicButton.setForeground(Color.RED);
            musicButton.setFont(normalFont);
            musicButton.addActionListener(e -> musicSettings(musicPlayer));

            titleNamePanel.add(titleNameLabel); // Add the label to the panel
            con.add(titleNamePanel); // Add the title name panel to the content pane
            con.add(musicButtonPanel);
            musicButtonPanel.add(musicButton);

            frame.setVisible(true);
        }

    public void createGameScreen() {

        // HIDE TITLE SCREEN
        instructionPanel.setVisible(false);
        // musicButtonPanel.setVisible(false);

        // TEXT PANEL
        mainTextPanel = new JPanel();
        mainTextPanel.setBackground(Color.CYAN);

        // TEXT AREA
        mainTextArea = new JTextArea("This is the main game text area");
        mainTextArea.setBackground(Color.black);
        mainTextArea.setForeground(Color.WHITE);
        mainTextArea.setFont(normalFont);
        mainTextArea.setLineWrap(true);
        mainTextArea.setWrapStyleWord(true);
        mainTextArea.setEditable(false);
        mainTextPanel.add(mainTextArea);

        // CHOICE TEXT PANEL
        choiceTextPanel = new JPanel();
        choiceTextPanel.setBackground(Color.YELLOW);

        // USER INPUT
        choiceTextArea = new JTextArea("This is the player input text area");
        choiceTextArea.setBackground(Color.white);
        choiceTextArea.setForeground(Color.BLACK);
        choiceTextArea.setFont(normalFont);
        choiceTextArea.setLineWrap(true);
        choiceTextArea.setWrapStyleWord(true);

        choiceTextPanel.add(choiceTextArea);

        // HELP PANEL
        helpPanel = new JPanel();
        helpPanel.setBackground(Color.GREEN);

        // HELP BUTTON
        helpButton = new JButton("HELP");
        helpButton.setForeground(Color.RED);
        helpButton.setFont(normalFont);

        ImageIcon imageIcon = new ImageIcon("/Users/stanjess24/Documents/Practical-Applications/Capstone-T1-HeartsoarTower/src/main/resources/Images/Help.png");
        helpButton.addActionListener(e ->
                JOptionPane.showMessageDialog(null, null, "Help", JOptionPane.PLAIN_MESSAGE, imageIcon)
        );

        helpPanel.add(helpButton);

        // PLAYER PANEL
        playerPanel = new JPanel();
        playerPanel.setBackground(Color.BLUE);

        playerLabel = new JLabel("Map");
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setFont(normalFont);
        playerPanel.add(playerLabel);

        artLabel = new JLabel("Ascii art graphics");
        artLabel.setForeground(Color.MAGENTA);
        artLabel.setFont(normalFont);
        playerPanel.add(artLabel);

        // Set up the layout using BorderLayout
        con.setLayout(new BorderLayout());

        // Add panels to the container using BorderLayout regions
        con.add(mainTextPanel, BorderLayout.CENTER);
        con.add(choiceTextPanel, BorderLayout.SOUTH);
        con.add(helpPanel, BorderLayout.NORTH);
        con.add(playerPanel, BorderLayout.WEST);

        playerSetup();
    }



    public void createInstructionScreen() {
        //HIDE TITLE SCREEN
        titleNamePanel.setVisible(false);
        musicButtonPanel.setVisible(false);

        //TEXT PANEL
        instructionPanel = new JPanel();
        instructionPanel.setBounds(100, 180, 600, 550);
        instructionPanel.setBackground(Color.BLACK);
        con.add(instructionPanel);

        //TEXT AREA
        instructionTextArea = new JTextArea("Welcome, Harmony, to the enchanted world of Terra Motus."+
                "\n\nStory:"+
                "\nThe King and Queen of Terra Motus are grief-stricken. An evil curse has sealed away their young Prince Timore in a tower at the edge of their kingdom."+
                "\nThe royalty have requested you, Harmony, to enter the tower and save their gentle son."+
                "\nArmed with only your trusted sword and an amulet wrapped in a handkerchief given by the queen, you approach the ominous tower, unsure of what lies within."+
                "\n\nObjective:"+
                "\nYour mission is to navigate through the tower, overcoming challenges along the way."+
                "\nTo win, you must free Prince Timore from the curse by reaching the top of the tower."+

                "\n\nAre you ready to begin your journey, Harmony? The fate of Prince Timore rests in your hands!");
        instructionTextArea.setBounds(100, 180, 600, 550);
        instructionTextArea.setBackground(Color.black);
        instructionTextArea.setForeground(Color.WHITE);
        instructionTextArea.setFont(storyFont);
        instructionTextArea.setLineWrap(true);
        instructionTextArea.setWrapStyleWord(true);
        instructionTextArea.setEditable(false);

        instructionPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "EnterPressed");
        instructionPanel.getActionMap().put("EnterPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                createGameScreen();
            }
        });

        instructionPanel.add(instructionTextArea); //adding text area to text panel
    }

    public void playerSetup() {

    }



//    public class HelpButtonHandler implements ActionListener {
//
//        @Override
//        public void actionPerformed(ActionEvent event) {
//            try {
//                String fileContent = readFile("path/to/your/helpfile.txt"); // Replace with the actual file path
//                JOptionPane.showMessageDialog(mainTextPanel, fileContent);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private String readFile(String filePath) throws IOException {
//        Path path = Paths.get(filePath);
//        byte[] fileBytes = Files.readAllBytes(path);
//        return new String(fileBytes);
//    }




    public static void main(String[] args) throws IOException {
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

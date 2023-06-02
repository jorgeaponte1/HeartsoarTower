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

public class GuiBuild {

    private JFrame frame;
    private JPanel titleNamePanel, musicButtonPanel, gameTextPanel, userInputPanel, navPanel,
    choiceTextPanel, helpPanel, instructionPanel, graphicPanel, navBtnPanel;
    private JTextArea userInputTextField, instructionTextArea;
    private Container con;
    private JLabel titleNameLabel, graphicLabel, gameTextLabel, mapLabel, inventoryLabel;


    private Font titleFont = new Font("Times New Roman", Font.PLAIN, 60);
    private Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
    private Font storyFont = new Font("Times New Roman", Font.PLAIN, 20);
    private JButton musicButton, helpButton, leftButton, rightButton, upButton, downButton;
    private MusicPlayer musicPlayer;

    private NewGame introductionMessage;

    private GuiBuild() {
            // Create and set up the window.
            frame = new JFrame("Heartsoar Tower");
            frame.setSize(1100, 900);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setBackground(Color.WHITE);
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
                   // instructionTextArea.setText(introductionMessage);
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

        // Set up the layout using BorderLayout
        con.setLayout(new BorderLayout());

        // Graphic PANEL
        graphicPanel = new JPanel();
        graphicPanel.setBounds(0,0,750,800);
        graphicPanel.setBackground(Color.BLUE);

        //graphic label
        graphicLabel = new JLabel("game graphics");
        graphicLabel.setForeground(Color.WHITE);
        graphicLabel.setFont(normalFont);
        graphicPanel.add(graphicLabel);

        // GameText panel
        gameTextPanel = new JPanel();
        gameTextPanel.setBackground(Color.CYAN);

        // GameText label
        gameTextLabel = new JLabel("Game text goes here");
        gameTextLabel.setForeground(Color.MAGENTA);
        gameTextLabel.setFont(normalFont);
        gameTextPanel.add(gameTextLabel);

        // UserInput TEXT PANEL
        userInputPanel = new JPanel();
        // added this line to make userInputPanel have a layout to be the parent of navBtnPanel
        userInputPanel.setLayout(new BorderLayout());
        userInputPanel.setBackground(Color.YELLOW);

        // UserInput TEXT AREA (bottom left)
        userInputTextField = new JTextArea("This is the main game text area");
        userInputTextField.setBackground(Color.black);
        userInputTextField.setForeground(Color.WHITE);
        userInputTextField.setFont(normalFont);
//        userInputTextField.setPreferredSize(new Dimension(300, 180));
//        userInputPanel.add(userInputTextField);
        userInputPanel.add(userInputTextField, BorderLayout.CENTER);

        // Nav PANEL (right column)
        navPanel = new JPanel();
        navPanel.setLayout(new BorderLayout());
        navPanel.setBackground(Color.GREEN);

        // Map label
        mapLabel = new JLabel("Map goes here");
        mapLabel.setBackground(Color.lightGray);
        mapLabel.setForeground(Color.BLACK);
        navPanel.add(mapLabel, BorderLayout.NORTH);

        // Inventory label
        inventoryLabel = new JLabel("Inventory goes here");
        inventoryLabel.setBackground(Color.lightGray);
        inventoryLabel.setForeground(Color.BLACK);
        navPanel.add(inventoryLabel, BorderLayout.CENTER);

        // NavBtn Panel
        navBtnPanel = new JPanel();
        navBtnPanel.setLayout(new GridLayout(5, 1));
        navBtnPanel.setBackground(Color.PINK);

        // HELP BUTTON
        helpButton = new JButton("HELP");
        helpButton.setForeground(Color.RED);
        helpButton.setFont(normalFont);

        ImageIcon imageIcon = new ImageIcon("/Users/stanjess24/Documents/Practical-Applications/Capstone-T1-HeartsoarTower/src/main/resources/Images/Help.png");
        helpButton.addActionListener(e ->
                JOptionPane.showMessageDialog(null, null, "Help", JOptionPane.PLAIN_MESSAGE, imageIcon)
        );
        navBtnPanel.add(helpButton);
        // Nav Buttons
        upButton = new JButton("Up");
        upButton.setForeground(Color.RED);
        upButton.setFont(normalFont);
        navBtnPanel.add(upButton);

        downButton = new JButton("Down");
        downButton.setForeground(Color.RED);
        downButton.setFont(normalFont);
        navBtnPanel.add(downButton);

        rightButton = new JButton("Right");
        rightButton.setForeground(Color.RED);
        rightButton.setFont(normalFont);
        navBtnPanel.add(rightButton);

        leftButton = new JButton("Left");
        leftButton.setForeground(Color.RED);
        leftButton.setFont(normalFont);
        navBtnPanel.add(leftButton);

        // Sub Panel for navBtnPanel and gameTextPanel inside UserInput Panel
        userInputPanel.add(navBtnPanel, BorderLayout.EAST);
        userInputPanel.add(gameTextPanel, BorderLayout.NORTH);

        // Add panels to the container using BorderLayout
        con.add(graphicPanel, BorderLayout.NORTH);
        con.add(userInputPanel, BorderLayout.SOUTH);
        con.add(navPanel, BorderLayout.EAST);
        con.add(gameTextPanel, BorderLayout.CENTER);

        playerSetup();
    }

    // I can create a subpanel above the userInputTextField that can include the game Text
    // I can also add some margins to the userInputTextField so that the userInput is seperated from game Text. Yes,
    // maybe center the text inside the panel or just add right 20 px

    public void createInstructionScreen() {
        //HIDE TITLE SCREEN
        titleNamePanel.setVisible(false);
        musicButtonPanel.setVisible(false);

        //TEXT PANEL
        instructionPanel = new JPanel();
        instructionPanel.setBounds(180, 180, 800, 550);
        instructionPanel.setBackground(Color.WHITE);
        con.add(instructionPanel);

        //TEXT AREA
        instructionTextArea = new JTextArea();
        instructionTextArea.setBounds(180, 180, 800, 550);
        instructionTextArea.setBackground(Color.WHITE);
        instructionTextArea.setForeground(Color.black);
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

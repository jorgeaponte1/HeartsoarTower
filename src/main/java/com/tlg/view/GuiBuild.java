package com.tlg.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.tlg.controller.AlwaysCommands.*;
import static com.tlg.controller.NewGame.newGame;

public class GuiBuild {

    private JFrame frame;
    private JPanel titleNamePanel, musicButtonPanel, mainTextPanel, choiceButtonPanel, playerPanel, choiceTextPanel,
    instructionPanel;
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

        //HIDE TITLE SCREEN
        instructionPanel.setVisible(false);
        //musicButtonPanel.setVisible(false);

        //TEXT PANEL
        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(100, 300, 600, 250);
        mainTextPanel.setBackground(Color.CYAN);
        mainTextPanel.setLayout(new GridLayout(2, 1));


        //TEXT AREA
        mainTextArea = new JTextArea("This is the main game text area");
        mainTextArea.setBounds(100, 200, 600, 250);
        mainTextArea.setBackground(Color.black);
        mainTextArea.setForeground(Color.WHITE);
        mainTextArea.setFont(normalFont);
        mainTextArea.setLineWrap(true);
        mainTextArea.setWrapStyleWord(true);
        mainTextArea.setEditable(false);
        mainTextPanel.add(mainTextArea);

        helpButton = new JButton("HELP");
        helpButton.setForeground(Color.RED);
        helpButton.setFont(normalFont);
        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "    ██████████▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█▀▀█████████\n" +
                        "    █████████ Welcome to Heartsoar Tower █████████████\n" +
                        "    ████████                                 ████████████\n" +
                        "    ████████▌ You can use the following commands: ██████████\n" +
                        "    ████████                                 ██████████████\n" +
                        "    ████████**Verbs**go, use, talk, dance,  ██████████████\n" +
                        "    ████████▌  open, grab, listen, look, cry, ██████████████\n" +
                        "    ████████▌  get, drop, yell, comfort, hug, █████████████\n" +
                        "    ████████▌  hide, attack, music, move, feed, ███████████\n" +
                        "    ████████▌  turn, reject, accept, teach, show, ███████████\n" +
                        "    ████████▌  remove, affirm.                 ██████████████\n" +
                        "    ████████                                  ██████████████\n" +
                        "    ████████**Nouns**left, right, up, teddy, ████████████\n" +
                        "    ████████  down, candle, fridge, stove, gun, ███████████\n" +
                        "    ████████  sword, amulet, ghost, door, nazi, ████████████\n" +
                        "    ████████  sandwich, goblin, island, casper, █████████████\n" +
                        "    ████████  toaster, mixer, succubus, mix, ██████████████\n" +
                        "    ████████  wereworf, direbear, bear, blanket, █████████████\n" +
                        "    ████████  vampire, mirror, zombie, off, key, █████████████\n" +
                        "    ████████  alien, window, prince, tim, understand,███████████\n" +
                        "    ████████  around, handkerchief.             █████████████\n" +
                        "    █████████                               └████████████\n" +
                        "    █████████⌐   **A few others**          ▐█████████████\n" +
                        "    █████████▌      look around            █████████████\n" +
                        "    ██████████       music                 ▐████████████\n" +
                        "    ███████████        quit                 ████████████\n" +
                        "    ██████████████                           └████████\n" +
                        "    ██████████▀▀▀▀▀MMMMMMMMMMMMMMMMMMMMMMM█ß▄ ████████\n" +
                        "    ████████                              ▐▌▒░▌▐███████\n" +
                        "    ███████▌                             █▒▒▒█▐███████\n" +
                        "    ███████▌    Press enter to continue    █▒▒▒█▐███████\n" +
                        "    ████████                               ▐▄▄▄▀▐███████\n" +
                        "    ████████▄                               █   ████████\n" +
                        "    █████████▀∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞∞4▀4█████████\n");
            }
        });

        mainTextPanel.add(helpButton);
        con.add(mainTextPanel);


        //Choice TEXT PANEL
        choiceTextPanel = new JPanel();
        choiceTextPanel.setBounds(100, 550, 600, 250);
        choiceTextPanel.setBackground(Color.YELLOW);
        con.add(choiceTextPanel);

        //User input
        choiceTextArea = new JTextArea("This is the player input text area");
        choiceTextArea.setBounds(100, 200, 600, 250);
        choiceTextArea.setBackground(Color.white);
        choiceTextArea.setForeground(Color.BLACK);
        choiceTextArea.setFont(normalFont);
        choiceTextArea.setLineWrap(true);
        choiceTextArea.setWrapStyleWord(true);

        choiceTextPanel.add(choiceTextArea); //adding text area to text panel

//        choiceButtonPanel = new JPanel();
//        choiceButtonPanel.setBounds(250, 650, 300, 150);
//        choiceButtonPanel.setBackground(Color.BLACK);
//        choiceButtonPanel.setLayout(new GridLayout(4, 1));
//        con.add(choiceButtonPanel);
//
//        choice1 = new JButton("Choice 1");
//        choice1.setBackground(Color.BLACK);
//        choice1.setForeground(Color.black);
//        choice1.setFont(normalFont);
//        choiceButtonPanel.add(choice1);
//
//        choice2 = new JButton("Choice 2");
//        choice2.setBackground(Color.BLACK);
//        choice2.setForeground(Color.black);
//        choice2.setFont(normalFont);
//        choiceButtonPanel.add(choice2);
//
//        choice3 = new JButton("Choice 3");
//        choice3.setBackground(Color.BLACK);
//        choice3.setForeground(Color.black);
//        choice3.setFont(normalFont);
//        choiceButtonPanel.add(choice3);
//
//        choice4 = new JButton("Choice 4");
//        choice4.setBackground(Color.BLACK);
//        choice4.setForeground(Color.black);
//        choice4.setFont(normalFont);
//        choiceButtonPanel.add(choice4);

        playerPanel = new JPanel();
        playerPanel.setBounds(100, 25, 600, 300);
        playerPanel.setBackground(Color.BLUE);
        playerPanel.setLayout(new GridLayout(1, 4));

        playerLabel = new JLabel("Map");
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setFont(normalFont);
        playerPanel.add(playerLabel);

        artLabel = new JLabel("Ascii art graphics");
        artLabel.setForeground(Color.MAGENTA);
        artLabel.setFont(normalFont);
        playerPanel.add(artLabel);
        con.add(playerPanel);

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

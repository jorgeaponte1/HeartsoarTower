package com.tlg.view;

import com.tlg.controller.AlwaysCommands;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static com.tlg.controller.AlwaysCommands.musicSettings;

public class GuiBuild {

    private JFrame frame;
    private JPanel titleNamePanel, musicButtonPanel, gameTextPanel, userInputPanel, navPanel,
    choiceTextPanel, helpPanel, instructionPanel, graphicPanel, navBtnPanel;
    private JTextField userInputTextField;
    private JTextArea instructionTextArea, introductionTextArea;
    private Container con;
    private JLabel titleNameLabel, graphicLabel, gameTextLabel, mapLabel, inventoryLabel, introductionLabel;


    private Font titleFont = new Font("Times New Roman", Font.PLAIN, 60);
    private Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
    private Font storyFont = new Font("Times New Roman", Font.PLAIN, 20);
    private JButton musicButton, helpButton, leftButton, rightButton, upButton, downButton;
    private MusicPlayer musicPlayer;

    private GuiBuild() {
            // Create and set up the window.
            frame = new JFrame("Heartsoar Tower");
            frame.setSize(1100, 900);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setBackground(Color.WHITE);
            frame.setLayout(null);
            // Test

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
        userInputPanel.setLayout(new BorderLayout());
        userInputPanel.setBackground(Color.YELLOW);

        // UserInput TEXT AREA (bottom left)
        userInputTextField = new JTextField("This is the main game text area");
        userInputTextField.setDocument(new JTextFieldLimit(25));
        userInputTextField.setBackground(Color.black);
        userInputTextField.setForeground(Color.WHITE);
        userInputTextField.setFont(normalFont);
        userInputTextField.setCaretColor(Color.WHITE);
        userInputTextField.setHorizontalAlignment(JTextField.CENTER);  // Add this line

        // Set margin around the text area
        int marginSize = 40;
        Insets margin = new Insets(marginSize, marginSize, marginSize, marginSize);
        userInputTextField.setMargin(margin);

        // Saved Variable that will have the text from userInputTextField
        String[] text = new String[1];
        // ActionListener for the userInputTextField
        userInputTextField.addActionListener(e -> {
            text[0] = userInputTextField.getText();
            System.out.println("Text saved: " + text[0]);
            userInputTextField.setText("");
        });

        userInputPanel.add(userInputTextField, BorderLayout.CENTER);

        // Nav PANEL (right column)
        navPanel = new JPanel();
        navPanel.setLayout(new BorderLayout());
        navPanel.setBackground(Color.GREEN);

        // Map label
        mapLabel = new JLabel("Map goes here");
        mapLabel.setBackground(Color.lightGray);
        mapLabel.setForeground(Color.BLACK);
        navPanel.add(mapLabel, BorderLayout.CENTER);

        // Inventory label
        inventoryLabel = new JLabel("Inventory goes here");
        inventoryLabel.setBackground(Color.lightGray);
        inventoryLabel.setForeground(Color.BLACK);
        navPanel.add(inventoryLabel, BorderLayout.SOUTH);

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
        //upButton.setBorder();
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

        // Music Panel
        musicButtonPanel = new JPanel();
        musicButtonPanel.setBounds(300, 400, 200, 100);
        musicButtonPanel.setBackground(Color.BLACK);

        // Music Panel Label
        JLabel music = new JLabel();
        // TODO ADD Speaker ICON instead of setText
        music.setText("Volume");
        music.setForeground(Color.WHITE);
        musicButtonPanel.add(music);

        // Volume Slider
        JSlider slider = new JSlider(0,100,50);
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(10);
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(25);
        slider.setPaintLabels(true);
        musicButtonPanel.add(slider);

        // Volume Slider Listener
        final int[] Volume = {slider.getValue()};
        slider.addChangeListener(e -> {
            Volume[0] = ((JSlider)e.getSource()).getValue();
            System.out.println(Volume[0]);
        });

        // Sub Panel for navBtnPanel and gameTextPanel inside UserInput Panel
        userInputPanel.add(gameTextPanel, BorderLayout.NORTH);
        userInputPanel.add(navBtnPanel, BorderLayout.EAST);

        // Sub Panel for musicButtonPanel inside navPanel
        navPanel.add(musicButtonPanel, BorderLayout.NORTH);

        // Add panels to the container using BorderLayout
        con.add(graphicPanel, BorderLayout.CENTER);
        con.add(userInputPanel, BorderLayout.SOUTH);
        con.add(navPanel, BorderLayout.EAST);
        //con.add(gameTextPanel, BorderLayout.CENTER);

        playerSetup();
    }

    class JTextFieldLimit extends PlainDocument {
        private int limit;
        JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null)
                return;
            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }

    public String createInstructionScreen() {
        // HIDE TITLE SCREEN
        titleNamePanel.setVisible(false);
        musicButtonPanel.setVisible(false);

        // TEXT PANEL
        instructionPanel = new JPanel();
        instructionPanel.setBackground(Color.WHITE);
        instructionPanel.setLayout(new BorderLayout());

        // TEXT AREA
        introductionTextArea = new JTextArea();
        introductionTextArea.setBackground(Color.WHITE);
        introductionTextArea.setForeground(Color.BLACK);
        introductionTextArea.setFont(storyFont);
        introductionTextArea.setLineWrap(true);
        introductionTextArea.setWrapStyleWord(true);
        introductionTextArea.setEditable(false);
        instructionPanel.add(introductionTextArea, BorderLayout.CENTER);

        int marginSize = 50;
        Insets margin = new Insets(marginSize, marginSize, marginSize, marginSize);
        introductionTextArea.setMargin(margin);

        // ADD PANEL TO CONTAINER
        con.setLayout(new BorderLayout());
        con.add(instructionPanel, BorderLayout.CENTER);

        String path = "/Intro/Introduction.txt";
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: " + path);
            }
            String text = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            introductionTextArea.setText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }

        instructionPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "EnterPressed");
        instructionPanel.getActionMap().put("EnterPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                createGameScreen();
            }
        });

        return path;
    }

    public void playerSetup() {

    }

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

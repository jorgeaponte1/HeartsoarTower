package com.tlg.view;

import com.tlg.controller.GameInputListener;
import com.tlg.controller.HeartsoarTower;
import com.tlg.model.Factory;
import com.tlg.model.Item;
import com.tlg.model.Player;
import com.tlg.model.Room;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

import static com.tlg.controller.AlwaysCommands.musicSettings;

public class GuiBuild {

    private JFrame frame;
    private JPanel titleNamePanel, musicButtonPanel, gameTextPanel, userInputPanel, navPanel,
    choiceTextPanel, helpPanel, instructionPanel, graphicPanel, navBtnPanel, locationPanel, inventoryHelpPanel;
    private JTextField userInputTextField, inventoryTextField, locationTextField;
    private JTextArea instructionTextArea, introductionTextArea, mapTextArea, gameTextArea;
    private Container con;
    private JLabel titleNameLabel, locationLabel;
    private JLabel graphicLabel;
    private static JLabel gameTextLabel;
    private JLabel inventoryLabel;
    private JLabel introductionLabel;
    HeartsoarTower heartsoarTower = new HeartsoarTower();
    public com.tlg.model.Factory factory = new Factory();
    private List<Room> rooms;
    private List<Item> items;
    Player player;
    DisplayArt displayArt;


    private Font titleFont = new Font("Times New Roman", Font.PLAIN, 60);
    private Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
    private Font storyFont = new Font("Times New Roman", Font.PLAIN, 20);
    private JButton musicButton, helpButton, leftButton, rightButton, upButton, downButton;
    private MusicPlayer musicPlayer = new MusicPlayer("Music/medievalrpg-music.wav");
    private GameInputListener gameInputListener;
    public GuiBuild(GameInputListener gameInputListener, Player player, List<Room> rooms, List<Item> items, DisplayArt displayArt) throws IOException {

        // Create and set up the window.
        frame = new JFrame("Heartsoar Tower");
        frame.setSize(1650, 1080);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(new BorderLayout());
        // Test

        // Get the content pane of the frame
        con = frame.getContentPane();
        frame.setTitle("HEARTSOAR TOWER");

        // Create a panel for the title name
        titleNamePanel = new JPanel();
        // DO NOT need to set Bounds on BorderLayout Manager
        //titleNamePanel.setBounds(100, 100, 600, 150);
        titleNamePanel.setBackground(Color.BLACK);

        // Set the layout manager of the titleNamePanel to center the components
        //titleNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleNamePanel.setLayout(new GridBagLayout());
        //titleNamePanel.setLayout(new BorderLayout());

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

//        // Create button panel
//        musicButtonPanel = new JPanel();
//        musicButtonPanel.setBounds(300, 400, 200, 100);
//        musicButtonPanel.setBackground(Color.BLACK);
//
//        // Create the start button
//        musicButton = new JButton("music");
//        musicButton.setBackground(Color.YELLOW);
//        musicButton.setForeground(Color.RED);
//        musicButton.setFont(normalFont);
//        musicButton.addActionListener(e -> musicSettings(musicPlayer));

        titleNamePanel.add(titleNameLabel);
        //titleNamePanel.add(titleNameLabel, BorderLayout.CENTER); // Add the label to the panel
        con.add(titleNamePanel, BorderLayout.CENTER); // Add the title name panel to the content pane
        //con.add(musicButtonPanel);
        //musicButtonPanel.add(musicButton);

        this.gameInputListener = gameInputListener;
        this.displayArt = displayArt;
        this.rooms = rooms;
        this.items = items;
        this.player = player;
        musicPlayer.play();
        frame.setVisible(true);
    }

    public void createGameScreen() {
        // HIDE TITLE SCREEN
        instructionPanel.setVisible(false);

        // Set up the layout using BorderLayout
        con.setLayout(new BorderLayout());

        // Graphic PANEL
        graphicPanel = new JPanel();
        graphicPanel.setBounds(0,0,750,800);
        graphicPanel.setBackground(new Color(26,83,92));


        // Graphic JTextArea
        JTextArea graphicTextArea = new JTextArea();
        //1A535C
        graphicTextArea.setBackground(new Color(247,255,247));
        graphicTextArea.setForeground(Color.BLACK);
        graphicTextArea.setFont(normalFont);
        graphicTextArea.setEditable(false);  // Ensure that user cannot edit the content

        String display = displayArt.getDisplay();
        String[] artLines;
        artLines = display.split("\n");
        String[] output = new String[artLines.length];
        for (int i = 0; i < artLines.length; i++) {
            output[i] = " " + artLines[i];
        }
        graphicTextArea.setText(String.join("\n", output));
        graphicPanel.add(graphicTextArea);

        // GameText panel
        gameTextPanel = new JPanel();
        // Added this line
        gameTextPanel.setLayout(new BorderLayout());
        gameTextPanel.setBackground(Color.CYAN);
        gameTextPanel.setPreferredSize(new Dimension(gameTextPanel.getPreferredSize().width, 120));

        JTextArea gameTextArea = new JTextArea();
        gameTextArea.setLineWrap(true); // Set line-wrap to true
        gameTextArea.setWrapStyleWord(true); // Set word-wrap to true
        gameTextArea.setEditable(false); // Make the JTextArea uneditable
        gameTextArea.setBackground(new Color(247,255,247));
        gameTextArea.setForeground(Color.BLACK);
        gameTextArea.setFont(normalFont);
        gameTextArea.setMargin(new Insets(0,40,0,0));
        DisplayText displayText = new DisplayText();
        gameTextArea.setText(displayText.getDisplay());
        gameTextPanel.add(gameTextArea, BorderLayout.CENTER);

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
        userInputTextField.setHorizontalAlignment(JTextField.CENTER);

        // Set margin around the text area
        int marginSize = 40;
        Insets margin = new Insets(marginSize, marginSize, marginSize, marginSize);
        userInputTextField.setMargin(margin);
        userInputPanel.add(userInputTextField, BorderLayout.CENTER);

        // Nav PANEL (right column)
        navPanel = new JPanel();
        navPanel.setLayout(new BorderLayout());
        navPanel.setBackground(new Color(78,205,196));

        // Set preferred width for the navPanel
        int navPanelWidth = 300; // Adjust this value as desired
        navPanel.setPreferredSize(new Dimension(navPanelWidth, navPanel.getPreferredSize().height));

        // Map Text area
        mapTextArea = new JTextArea("Map goes here");
        mapTextArea.setBackground(new Color(78,205,196));
        mapTextArea.setForeground(Color.BLACK);
        navPanel.add(mapTextArea, BorderLayout.CENTER);
        String filePath = "/Ascii_art/fullmap.txt";
        readFileIntoJTextArea(filePath, mapTextArea);

        int mapTextAreaHeight = 300;
        mapTextArea.setPreferredSize(new Dimension(mapTextArea.getPreferredSize().width, mapTextAreaHeight));

        // Create a new panel with BorderLayout
        inventoryHelpPanel = new JPanel(new BorderLayout());

        // Inventory Text field
        inventoryLabel = new JLabel();
        inventoryLabel.setBackground(new Color(78,205,196));
        inventoryLabel.setForeground(Color.BLACK);
        DisplayInput displayInput = new DisplayInput(player);
        inventoryLabel.setText(displayInput.getInventory());
        inventoryLabel.setBorder(new EmptyBorder(0,20,0,0));

        //preferred size of the inventoryTextField
        Dimension textFieldSize = new Dimension(100, 50);
        inventoryLabel.setPreferredSize(textFieldSize);
        // Add inventoryTextField to the center of the new panel
        inventoryHelpPanel.add(inventoryLabel, BorderLayout.CENTER);

        // ActionListener for the userInputTextField
        userInputTextField.addActionListener(e -> {
            // String input will have the text from userInputTextField
            String input = userInputTextField.getText();
            input = input.replaceAll("\\W+", " ").toLowerCase().strip();
            String[] words = input.split("\\s+");  // split on one or more whitespace characters
            userInputTextField.setText("");
            gameInputListener.onInputReceived(words);
            gameTextArea.setText(displayText.getDisplay());
            graphicTextArea.setText(displayArt.getDisplay());
            inventoryLabel.setText(displayInput.getInventory());
            locationLabel.setText(player.getLocation().getName());
        });

        // NavBtn Panel
        navBtnPanel = new JPanel();
        navBtnPanel.setLayout(new GridBagLayout());
        navBtnPanel.setBackground(new Color(247,255,247));

        // Location Panel
        locationPanel = new JPanel();
        locationPanel.setBackground(new Color(26,83,92));
        locationPanel.setForeground(new Color(247,255,247));
        locationLabel = new JLabel();
        locationLabel.setText(player.getLocation().getName());
        locationLabel.setForeground(new Color(247,255,247));
        locationPanel.add(locationLabel);

// Create GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

// HELP BUTTON
        helpButton = new JButton("HELP");
        helpButton.setForeground(Color.RED);
        helpButton.setFont(normalFont);
        URL helpUrl = getClass().getClassLoader().getResource("Images/Help.png");
        assert helpUrl != null;
        ImageIcon helpImageIcon = new ImageIcon(helpUrl);
        helpButton.addActionListener(e ->
                JOptionPane.showMessageDialog(null, null, "Help", JOptionPane.PLAIN_MESSAGE, helpImageIcon)
        );
        // Add helpButton to the south of the new panel
        inventoryHelpPanel.add(helpButton, BorderLayout.SOUTH);
        navPanel.add(inventoryHelpPanel, BorderLayout.SOUTH);

// Nav Buttons
        // Up Button
        upButton = new JButton();
        upButton.setForeground(Color.RED);
        upButton.setFont(normalFont);

        URL upDirectionURL = getClass().getClassLoader().getResource("Images/up icon.png");
        assert upDirectionURL != null;
        ImageIcon upDirectionIcon = new ImageIcon(upDirectionURL);
        Image upDirectionImage = upDirectionIcon.getImage(); // transform it
        Image upDirectionImg = upDirectionImage.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH); // scale it smoothly
        ImageIcon newUpDirectionIcon = new ImageIcon(upDirectionImg);  // assign to a new ImageIcon instance

        upButton.setBorderPainted(false);
        upButton.setContentAreaFilled(false);
        upButton.setFocusPainted(false);
        upButton.setOpaque(false);

        upButton.setIcon(newUpDirectionIcon);
        upButton.addActionListener(e -> {
            String[] upCommand = {"go", "up"};
            gameInputListener.onInputReceived(upCommand);
            gameTextArea.setText(displayText.getDisplay());
            graphicTextArea.setText(displayArt.getDisplay());
            inventoryLabel.setText(displayInput.getInventory());
            locationLabel.setText(player.getLocation().getName());
        });
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        navBtnPanel.add(upButton, gbc);

        // Down Button
        downButton = new JButton();
        downButton.setForeground(Color.RED);
        downButton.setFont(normalFont);

        URL downDirectionURL = getClass().getClassLoader().getResource("Images/down icon.png");
        assert downDirectionURL != null;
        ImageIcon downDirectionIcon = new ImageIcon(downDirectionURL);
        Image downDirectionImage = downDirectionIcon.getImage(); // transform it
        Image downDirectionImg = downDirectionImage.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH); // scale it smoothly
        ImageIcon newDownDirectionIcon = new ImageIcon(downDirectionImg);  // assign to a new ImageIcon instance

        downButton.setBorderPainted(false);
        downButton.setContentAreaFilled(false);
        downButton.setFocusPainted(false);
        downButton.setOpaque(false);

        downButton.setIcon(newDownDirectionIcon);
        downButton.addActionListener(e -> {
            String[] downCommand = {"go", "down"};
            gameInputListener.onInputReceived(downCommand);
            gameTextArea.setText(displayText.getDisplay());
            graphicTextArea.setText(displayArt.getDisplay());
            inventoryLabel.setText(displayInput.getInventory());
            locationLabel.setText(player.getLocation().getName());
        });
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        navBtnPanel.add(downButton, gbc);

        // Right Button
        rightButton = new JButton();
        rightButton.setForeground(Color.RED);
        rightButton.setFont(normalFont);

        URL rightDirectionURL = getClass().getClassLoader().getResource("Images/right icon.png");
        assert rightDirectionURL != null;
        ImageIcon rightDirectionIcon = new ImageIcon(rightDirectionURL);
        Image rightDirectionImage = rightDirectionIcon.getImage(); // transform it
        Image rightDirectionImg = rightDirectionImage.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH); // scale it smoothly
        ImageIcon newRightDirectionIcon = new ImageIcon(rightDirectionImg);  // assign to a new ImageIcon instance

        rightButton.setBorderPainted(false);
        rightButton.setContentAreaFilled(false);
        rightButton.setFocusPainted(false);
        rightButton.setOpaque(false);

        rightButton.setIcon(newRightDirectionIcon);
        rightButton.addActionListener(e -> {
            String[] rightCommand = {"go", "right"};
            gameInputListener.onInputReceived(rightCommand);
            gameTextArea.setText(displayText.getDisplay());
            graphicTextArea.setText(displayArt.getDisplay());
            inventoryLabel.setText(displayInput.getInventory());
            locationLabel.setText(player.getLocation().getName());
        });
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        navBtnPanel.add(rightButton, gbc);

        // Left Button
        leftButton = new JButton();
        leftButton.setForeground(Color.RED);
        leftButton.setFont(normalFont);

        URL leftDirectionURL = getClass().getClassLoader().getResource("Images/left icon.png");
        assert leftDirectionURL != null;
        ImageIcon leftDirectionIcon = new ImageIcon(leftDirectionURL);
        Image leftDirectionImage = leftDirectionIcon.getImage(); // transform it
        Image leftDirectionImg = leftDirectionImage.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH); // scale it smoothly
        ImageIcon newLeftDirectionIcon = new ImageIcon(leftDirectionImg);  // assign to a new ImageIcon instance

        leftButton.setBorderPainted(false);
        leftButton.setContentAreaFilled(false);
        leftButton.setFocusPainted(false);
        leftButton.setOpaque(false);

        leftButton.setIcon(newLeftDirectionIcon);
        leftButton.addActionListener(e -> {
            String[] leftCommand = {"go", "left"};
            gameInputListener.onInputReceived(leftCommand);
            gameTextArea.setText(displayText.getDisplay());
            graphicTextArea.setText(displayArt.getDisplay());
            inventoryLabel.setText(displayInput.getInventory());
            //inventoryTextField.setText(displayInput.getInventory());
            locationLabel.setText(player.getLocation().getName());
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        navBtnPanel.add(leftButton, gbc);

        // Music Panel
        musicButtonPanel = new JPanel();
        musicButtonPanel.setBounds(300, 400, 200, 100);
        musicButtonPanel.setBackground(new Color(247,255,247));

        // Music Panel Label
        JLabel music = new JLabel();
        // Speaker Image
        URL speakerUrl = getClass().getClassLoader().getResource("Images/Speaker.png");
        assert speakerUrl != null;
        ImageIcon speakerIcon = new ImageIcon(speakerUrl);
        Image speakerImage = speakerIcon.getImage(); // transform it
        Image newSpeakerImg = speakerImage.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH); // scale it smoothly
        ImageIcon newSpeakerIcon = new ImageIcon(newSpeakerImg);  // assign to a new ImageIcon instance
        // Mute Image
        URL muteUrl = getClass().getClassLoader().getResource("Images/Speaker-Mute.png");
        assert muteUrl != null;
        ImageIcon muteIcon = new ImageIcon(muteUrl);
        Image muteImage = muteIcon.getImage(); // transform it
        Image newMuteImg = muteImage.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH); // scale it smoothly
        ImageIcon newMuteIcon = new ImageIcon(newMuteImg);  // assign to a new ImageIcon instance

        JButton speakerButton = new JButton(newSpeakerIcon);
        // Make SpeakerIcon be the button and remove the border of the button.
        speakerButton.setBorderPainted(false);
        speakerButton.setContentAreaFilled(false);
        speakerButton.setFocusPainted(false);
        speakerButton.setOpaque(false);
        speakerButton.addActionListener(e -> {
            if (musicPlayer.isPlaying()) {
                speakerButton.setIcon(newMuteIcon);
                musicPlayer.stop();
            } else {
                speakerButton.setIcon(newSpeakerIcon);
                musicPlayer.play();
            }
        });
        music.setForeground(Color.WHITE);
        musicButtonPanel.add(speakerButton);
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
        final float[] Volume = {slider.getValue()};
        slider.addChangeListener(e -> {
            Volume[0] = (float) (((JSlider)e.getSource()).getValue() / 100.00);
            musicPlayer.setVolume(Volume[0]);
        });

        // Sub Panel for navBtnPanel and gameTextPanel inside UserInput Panel
        userInputPanel.add(gameTextPanel, BorderLayout.NORTH);
        userInputPanel.add(navBtnPanel, BorderLayout.EAST);

        // Sub Panel for musicButtonPanel inside navPanel
        navPanel.add(musicButtonPanel, BorderLayout.NORTH);

        // Add panels to the container using BorderLayout
        con.add(locationPanel, BorderLayout.NORTH);
        con.add(graphicPanel, BorderLayout.CENTER);
        con.add(userInputPanel, BorderLayout.SOUTH);
        con.add(navPanel, BorderLayout.EAST);
    }

    // TODO This method I need to Change to Create the Pop-Up of the Yes/No
    public void onYesNoInputReceived() {
        // Here, you should wait for user input from the GUI,
        // When the user inputs something, return it.
        String input = "Yes";
        gameInputListener.onYesNoInputReceived(input);
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
        //musicButtonPanel.setVisible(false);

        // TEXT PANEL
        instructionPanel = new JPanel();
        instructionPanel.setBackground(Color.WHITE);
        instructionPanel.setLayout(new GridBagLayout());
        instructionPanel.setSize(100,100);
        //instructionPanel.setLayout(new BorderLayout());

        // TEXT AREA
        introductionTextArea = new JTextArea(13,90);
        introductionTextArea.setBackground(Color.WHITE);
        introductionTextArea.setForeground(Color.BLACK);
        introductionTextArea.setFont(storyFont);
        introductionTextArea.setLineWrap(true);
        introductionTextArea.setWrapStyleWord(true);
        introductionTextArea.setEditable(false);
        instructionPanel.add(introductionTextArea);
        //instructionPanel.add(introductionTextArea, BorderLayout.CENTER);

        int marginSize = 2;
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

    public void readFileIntoJTextArea(String filePath, JTextArea textArea) {
        //noinspection ConstantConditions
        try (InputStream is = getClass().getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            textArea.setText(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //public void updateGameText(String text) {
    //        if (gameTextLabel != null) {
    //            gameTextLabel.setText(text);
    //            System.out.println(text);
    //        }
    //}

//    public void displayGuiMap() {
//        DisplayEngine displayEngine = new DisplayEngine();
//        //Display MapUI FULL MAP in Gui
//        String map = MapUI.getFullMap();
//        System.out.println(map);
//        mapTextArea.setText(map);
//    }

//    public String displayGuiMap(List<Room> rooms) {
//        DisplayEngine displayEngine = new DisplayEngine();
//        DisplayArt displayArt = new DisplayArt();
          //String map = displayEngine.printMapAndArt(displayArt, heartsoarTower.getRooms());
//        System.out.println(map);
//        if (mapTextArea != null) {
//            mapTextArea.setText(map);
//        }
//        return map;
//    }
}

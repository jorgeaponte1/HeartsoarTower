package com.tlg.view;

import com.tlg.controller.GameInputListener;
import com.tlg.controller.HeartsoarTower;
import com.tlg.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class GuiBuild {

    private JFrame frame;
    private JPanel titleNamePanel, musicButtonPanel, gameTextPanel, userInputPanel, navPanel,
            instructionPanel, graphicPanel, navBtnPanel, locationPanel, inventoryHelpPanel;
    private JTextField userInputTextField;
    private JTextArea introductionTextArea;
    private Container con;
    private JLabel locationLabel, mapLabel;
    private JLabel graphicLabel;
    private JLabel inventoryLabel;
    private HeartsoarTower heartsoarTower;
    private List<Room> rooms;
    private List<Item> items;
    private Player player;
    private DisplayArt displayArt;
    private final Font titleFont = new Font("Helvetica", Font.BOLD, 80);
    private final Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
    private final Font invFont = new Font("Ariel", Font.PLAIN, 20);
    private final Font storyFont = new Font("Ariel", Font.PLAIN, 30);
    private JButton helpButton, leftButton, rightButton, upButton, downButton;
    private MusicPlayer musicPlayer = new MusicPlayer("Music/medievalrpg-music.wav");
    private GameInputListener gameInputListener;
    private List<Scene> scenes;

    public GuiBuild(GameInputListener gameInputListener, Player player, List<Room> rooms, List<Item> items, DisplayArt displayArt, HeartsoarTower heartsoarTower, List<Scene> scenes) throws IOException {

        this.heartsoarTower = heartsoarTower;
        this.gameInputListener = gameInputListener;
        this.displayArt = displayArt;
        this.rooms = rooms;
        this.items = items;
        this.player = player;
        this.scenes = scenes;

        // Create and set up the window.
        frame = new JFrame("Heartsoar Tower");

        //set frame width and height
        int frameWidth = 1500;
        int frameHeight = 1400;


        // Set the Frame to the Max Size of a User's Screen. So Image is reflected in accordance.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(frameWidth, frameHeight);
        int centerX = (screenSize.width - frame.getWidth()) / 2;
        int centerY = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(centerX, centerY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(new BorderLayout());

        // Get the content pane of the frame
        con = frame.getContentPane();
        frame.setTitle("HEARTSOAR TOWER");

        //Cover Image
        // Image of Castle
        String imgResourcePath = "/Images/Castle.png";
        //noinspection ConstantConditions
        Image backgroundImage = ImageIO.read(getClass().getResource(imgResourcePath));
        Image scaledImage = backgroundImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_DEFAULT);

        JLabel titleNameLabel = new JLabel("<html><center>HEARTSORE TOWER</center></html>", new ImageIcon(scaledImage), JLabel.CENTER);
        titleNameLabel.setVerticalTextPosition(JLabel.CENTER);
        titleNameLabel.setHorizontalTextPosition(JLabel.CENTER);
        titleNameLabel.setForeground(Color.WHITE); // Text color
        titleNameLabel.setFont(titleFont);
        titleNamePanel = new JPanel(new BorderLayout());

        addChildren(titleNamePanel, List.of(titleNameLabel), List.of(BorderLayout.CENTER)); // Add the label to the panel
        addChildren(con, List.of(titleNamePanel), List.of(BorderLayout.CENTER)); // Add the title name panel to the content pane

        // Transition call to introduction screen
        titleTransition(titleNameLabel);

        // Add component resize listener to resize Image everytime Frame is resized.
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Image scaledImage = backgroundImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_DEFAULT);
                titleNameLabel.setIcon(new ImageIcon(scaledImage));
            }
        });

        musicPlayer.play();
        frame.setVisible(true);
    }

    private void titleTransition(JLabel titleNameLabel) {
        // Add a mouse listener to the title name label
        // When Enter is pressed, the title screen will disappear and the introduction screen will appear
        titleNamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "EnterPressed");
        titleNamePanel.getActionMap().put("EnterPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                createInstructionScreen();
            }
        });
    }

    public void createGameScreen() {
        // HIDE TITLE SCREEN
        instructionPanel.setVisible(false);

        // Set up the layout using BorderLayout
        con.setLayout(new BorderLayout());

        // Graphic PANEL
        graphicPanel = new JPanel();
        graphicPanel.setBounds(0, 0, 750, 800);
        graphicPanel.setBackground(new Color(26, 83, 92));

        int graphicPanelwidth = 900; // Adjust this value as desired
        graphicPanel.setPreferredSize(new Dimension(graphicPanelwidth, graphicPanel.getPreferredSize().height));

        // Graphic Label
        graphicLabel = new JLabel();
        URL imageUrl = getClass().getClassLoader().getResource(rooms.get(rooms.indexOf(player.getLocation())).getGraphicMonster());
        //noinspection ConstantConditions
        ImageIcon graphicIcon = new ImageIcon(imageUrl);
        graphicLabel.setIcon(graphicIcon);
        graphicPanel.add(graphicLabel);

        // GameText panel
        gameTextPanel = new JPanel();
        gameTextPanel.setLayout(new BorderLayout());
        gameTextPanel.setBackground(Color.CYAN);


        // GameText Area
        JTextArea gameTextArea = new JTextArea();
        gameTextArea.setLineWrap(true);
        gameTextArea.setWrapStyleWord(true);
        gameTextArea.setEditable(false);
        gameTextArea.setBackground(new Color(247, 255, 247));
        gameTextArea.setForeground(Color.BLACK);
        gameTextArea.setFont(normalFont);
        gameTextArea.setMargin(new Insets(0, 40, 0, 0));
        // Set the text of the gameTextArea to the display text
        DisplayText displayText = new DisplayText();
        gameTextArea.setText(displayText.getDisplay());
        // Add the gameTextArea to the gameTextPanel
        addChildren(gameTextPanel,List.of(gameTextArea),List.of(BorderLayout.CENTER));

        // Set preferred size of the gameTextPanel to allow for 10 lines of text
        //int panelHeight = gameTextArea.getFontMetrics(gameTextArea.getFont()).getHeight() * 5;
        //gameTextPanel.setPreferredSize(new Dimension(gameTextPanel.getPreferredSize().width, panelHeight));

        // UserInput TEXT PANEL
        userInputPanel = new JPanel();
        userInputPanel.setLayout(new BorderLayout());
        userInputPanel.setBackground(Color.YELLOW);

        // UserInput TEXT AREA (bottom left)
        userInputTextField = new JTextField();
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
        addChildren(userInputPanel,List.of(userInputTextField),List.of(BorderLayout.CENTER));

        // Nav PANEL (right column)
        navPanel = new JPanel();
        navPanel.setLayout(new BorderLayout());
        navPanel.setBackground(new Color(26, 83, 92));

        // Set preferred width for the navPanel
//        int navPanelWidth = 500; // Adjust this value as desired
        navPanel.setPreferredSize(new Dimension(frame.getWidth() - graphicPanelwidth,
                navPanel.getPreferredSize().height));

        // Map Image default
        String imgResourcePath = "/Images/map.png";
        //noinspection ConstantConditions
        ImageIcon mapImageIcon = new ImageIcon(getClass().getResource(imgResourcePath));

        // Scale down the image
        Image mapImage = mapImageIcon.getImage();
        int newWidth = mapImageIcon.getIconWidth() / 2;
        int newHeight = mapImageIcon.getIconHeight() / 2;
        //smooth scaling
        Image scaledImage = mapImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        // Create a new ImageIcon with the scaled image
        ImageIcon scaledMapImageIcon = new ImageIcon(scaledImage);

        // Map Label
        mapLabel = new JLabel(imgResourcePath, scaledMapImageIcon, JLabel.CENTER);
        mapLabel.setForeground(Color.BLACK);
        mapLabel.setPreferredSize(new Dimension(mapLabel.getPreferredSize().width, 480));
        navPanel.add(mapLabel, BorderLayout.NORTH);

        // Create a new panel with BorderLayout
        inventoryHelpPanel = new JPanel(new BorderLayout());

        // Inventory Text field
        inventoryLabel = new JLabel();
        inventoryLabel.setBackground(new Color(78,205,196));
        inventoryLabel.setForeground(Color.WHITE);
        inventoryLabel.setFont(invFont);
        DisplayInput displayInput = new DisplayInput(player);
        inventoryLabel.setText("<HTML>" + displayInput.getInventory() + "<br>" + displayInput.getAmuletCharges() + "<br>Items located here: " + "</HTML");
        inventoryLabel.setBorder(new EmptyBorder(0, 20, 0, 0));

        //preferred size of the inventoryTextField
        Dimension textFieldSize = new Dimension(100, 150);
        inventoryLabel.setPreferredSize(textFieldSize);
        // Add inventoryTextField to the center of the new panel
        inventoryHelpPanel.add(inventoryLabel, BorderLayout.CENTER);
        inventoryHelpPanel.setBackground(new Color(26,83,92));



        // NavBtn Panel
        navBtnPanel = new JPanel();
        navBtnPanel.setLayout(new GridBagLayout());
        navBtnPanel.setBackground(Color.BLACK);
        navBtnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 120));

        // Location Panel
        locationPanel = new JPanel();
        locationPanel.setBackground(new Color(26,83,92));

        // Location Label
        locationLabel = new JLabel();
        locationLabel.setBackground(new Color(26,83,92));
        locationLabel.setText(player.getLocation().getName());
        locationLabel.setForeground(new Color(247, 255, 247));
        locationPanel.add(locationLabel);

// Create GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(2, 2, 2, 2);

// HELP BUTTON
        helpButton = new JButton("HELP");
        helpButton.setForeground(Color.RED);
        helpButton.setFont(normalFont);
        URL helpUrl = getClass().getClassLoader().getResource("Images/Help.png");
        //noinspection ConstantConditions
        ImageIcon helpIcon = new ImageIcon(helpUrl);
        Image helpImage = helpIcon.getImage(); // transform it
        Image helpImg = helpImage.getScaledInstance(800, 800, java.awt.Image.SCALE_SMOOTH); // scale it smoothly
        ImageIcon newHelpIcon = new ImageIcon(helpImg);  // assign to a new ImageIcon instance
        helpButton.addActionListener(e ->
                JOptionPane.showMessageDialog(null, null, "Help Scroll", JOptionPane.PLAIN_MESSAGE, newHelpIcon)
        );
        // Add inventoryTextField to the center and add helpButton to the south of the new panel
        addChildren(inventoryHelpPanel,List.of(inventoryLabel,helpButton), List.of(BorderLayout.CENTER,BorderLayout.SOUTH));


// Nav Buttons
        // Up Button
        upButton = new JButton();
        upButton.setForeground(Color.RED);
        upButton.setFont(normalFont);

        URL upDirectionURL = getClass().getClassLoader().getResource("Images/up icon.png");
        //noinspection ConstantConditions
        ImageIcon upDirectionIcon = new ImageIcon(upDirectionURL);
        Image upDirectionImage = upDirectionIcon.getImage(); // transform it
        Image upDirectionImg = upDirectionImage.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it smoothly
        ImageIcon newUpDirectionIcon = new ImageIcon(upDirectionImg);  // assign to a new ImageIcon instance

        hideButton(upButton);

        upButton.setIcon(newUpDirectionIcon);
        upButton.addActionListener(e -> {
            String[] upCommand = {"go", "up"};
            updateGUI(gameTextArea, displayText, displayInput, upCommand);
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
        //noinspection ConstantConditions
        ImageIcon downDirectionIcon = new ImageIcon(downDirectionURL);
        Image downDirectionImage = downDirectionIcon.getImage(); // transform it
        Image downDirectionImg = downDirectionImage.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it smoothly
        ImageIcon newDownDirectionIcon = new ImageIcon(downDirectionImg);  // assign to a new ImageIcon instance

        hideButton(downButton);

        downButton.setIcon(newDownDirectionIcon);
        downButton.addActionListener(e -> {
            String[] downCommand = {"go", "down"};
            updateGUI(gameTextArea, displayText, displayInput, downCommand);
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
        //noinspection ConstantConditions
        ImageIcon rightDirectionIcon = new ImageIcon(rightDirectionURL);
        Image rightDirectionImage = rightDirectionIcon.getImage(); // transform it
        Image rightDirectionImg = rightDirectionImage.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it smoothly
        ImageIcon newRightDirectionIcon = new ImageIcon(rightDirectionImg);  // assign to a new ImageIcon instance

        hideButton(rightButton);

        rightButton.setIcon(newRightDirectionIcon);
        rightButton.addActionListener(e -> {
            String[] rightCommand = {"go", "right"};
            updateGUI(gameTextArea, displayText, displayInput, rightCommand);
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
        //noinspection ConstantConditions
        ImageIcon leftDirectionIcon = new ImageIcon(leftDirectionURL);
        Image leftDirectionImage = leftDirectionIcon.getImage(); // transform it
        Image leftDirectionImg = leftDirectionImage.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it smoothly
        ImageIcon newLeftDirectionIcon = new ImageIcon(leftDirectionImg);  // assign to a new ImageIcon instance

        hideButton(leftButton);

        leftButton.setIcon(newLeftDirectionIcon);
        leftButton.addActionListener(e -> {
            String[] leftCommand = {"go", "left"};
            updateGUI(gameTextArea, displayText, displayInput, leftCommand);
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        navBtnPanel.add(leftButton, gbc);

        // Music Panel
        musicButtonPanel = new JPanel();
        musicButtonPanel.setBounds(300, 400, 200, 100);
        musicButtonPanel.setBackground(new Color(26,83,92));
        musicButtonPanel.setForeground(Color.WHITE);
        musicButtonPanel.setLayout(new GridBagLayout());

        // Modify the height of the Bounds object
        Rectangle bounds = musicButtonPanel.getBounds();
        bounds.height = 150; // Set the desired height
        musicButtonPanel.setBounds(bounds);

        // Music Panel Label
        JLabel music = new JLabel();
        // Speaker Image
        URL speakerUrl = getClass().getClassLoader().getResource("Images/speakeron.png");
        //noinspection ConstantConditions
        ImageIcon speakerIcon = new ImageIcon(speakerUrl);
        Image speakerImage = speakerIcon.getImage(); // transform it
        Image newSpeakerImg = speakerImage.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH); // scale it smoothly
        ImageIcon newSpeakerIcon = new ImageIcon(newSpeakerImg);  // assign to a new ImageIcon instance
        // Mute Image
        URL muteUrl = getClass().getClassLoader().getResource("Images/speakeroff.png");
        //noinspection ConstantConditions
        ImageIcon muteIcon = new ImageIcon(muteUrl);
        Image muteImage = muteIcon.getImage(); // transform it
        Image newMuteImg = muteImage.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH); // scale it smoothly
        ImageIcon newMuteIcon = new ImageIcon(newMuteImg);  // assign to a new ImageIcon instance

        JButton speakerButton = new JButton(newSpeakerIcon);
        // Make SpeakerIcon be the button and remove the border of the button.
        hideButton(speakerButton);
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
        musicButtonPanel.add(buildVolumeSlider());

        // Sub Panel for navBtnPanel and gameTextPanel inside UserInput Panel
        addChildren(userInputPanel, List.of(gameTextPanel,navBtnPanel), List.of(BorderLayout.NORTH, BorderLayout.EAST));
        addChildren(inventoryHelpPanel, List.of(helpButton), List.of(BorderLayout.SOUTH));

        // Sub Panel for musicButtonPanel inside navPanel
        addChildren(navPanel,List.of(musicButtonPanel, mapLabel, inventoryHelpPanel),List.of(BorderLayout.NORTH, BorderLayout.CENTER, BorderLayout.SOUTH));

        // Add panels to the container using BorderLayout
        addChildren(con,List.of(locationPanel,graphicPanel,userInputPanel,navPanel),
                List.of(BorderLayout.NORTH, BorderLayout.CENTER, BorderLayout.SOUTH, BorderLayout.EAST));

        //Call the ActionListener for the userInputTextField
        actionListenerInput(gameTextArea, displayText, displayInput);
    }

    private void actionListenerInput(JTextArea gameTextArea, DisplayText displayText, DisplayInput displayInput) {
        // ActionListener for the userInputTextField
        userInputTextField.addActionListener(e -> {
            // String input will have the text from userInputTextField
            String input = userInputTextField.getText();
            input = input.replaceAll("\\W+", " ").toLowerCase().strip();
            String[] words = input.split("\\s+");  // split on one or more whitespace characters
            userInputTextField.setText("");
            updateGUI(gameTextArea, displayText, displayInput, words);
        });
    }

    private Container addChildren(Container parent, List<Component> children, List<Object> constraints) {
        Iterator<Component> childIter = children.iterator();
        Iterator<Object> constraintIter = constraints.iterator();
        while (childIter.hasNext() && constraintIter.hasNext()) {
            Component child = childIter.next();
            Object constraint = constraintIter.next();
            parent.add(child, constraint);
        }
        return parent;
    }

    public void dispose() {
        if (frame != null) {
            frame.dispose();
        }
    }

    private JSlider buildVolumeSlider() {
        JSlider slider = new JSlider(0, 100, 50);
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(10);
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(25);
        slider.setPaintLabels(true);
        slider.setForeground(Color.WHITE);
        slider.setOpaque(false);

        // Volume Slider Listener
        final float[] Volume = {slider.getValue()};
        slider.addChangeListener(e -> {
            Volume[0] = (float) (((JSlider) e.getSource()).getValue() / 100.00);
            musicPlayer.setVolume(Volume[0]);
        });
        return slider;
    }

    private void hideButton(JButton button) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
    }

    private void updateGUI(JTextArea gameTextArea, DisplayText displayText, DisplayInput displayInput, String[] command) {
        gameInputListener.onInputReceived(command);
        gameTextArea.setText(displayText.getDisplay());
        setImageIcon();
        setInventoryLabelText(displayInput);
        locationLabel.setText(player.getLocation().getName());
        mapLabel.setIcon(player.getLocation().getMapImage());
        endGame(player);
    }

    private void setInventoryLabelText(DisplayInput displayInput) {
        List<Scene> currentScene = scenes.stream().filter(scene -> scene.getRoom().equals(player.getLocation()))
                .collect(Collectors.toList());
        if (!currentScene.isEmpty() && !currentScene.get(0).getSceneItems().isEmpty()) {
            List<String> itemsLocatedHere = currentScene.get(0).getSceneItems().stream().map(Item::getName).collect(Collectors.toList());
            String itemsString = String.join(", ", itemsLocatedHere);
            inventoryLabel.setText("<HTML>" + displayInput.getInventory() + "<br>" + displayInput.getAmuletCharges() + "<br>Items located here: " + itemsString + "</HTML");
        }
        else {
            inventoryLabel.setText("<HTML>" + displayInput.getInventory() + "<br>" + displayInput.getAmuletCharges() + "<br>Items located here: " + "</HTML");
        }
    }

    private void setImageIcon() {
        if (player.getLocation().isMonsterDefeated()){
            List<Scene> currentScene = scenes.stream().filter(scene -> scene.getRoom().equals(player.getLocation()))
                    .collect(Collectors.toList());
            if (!currentScene.isEmpty() && !currentScene.get(0).getSceneItems().isEmpty()) {
                if (currentScene.get(0).getSceneItems().get(0).getGraphic() != null) {
                    URL itemImageUrls = getClass().getClassLoader().getResource(currentScene.get(0).getSceneItems().get(0).getGraphic());
                    setGraphicLabelImageIcon(itemImageUrls);
                }
            }
            else {
                URL itemImageUrls = getClass().getClassLoader().getResource(rooms.get(rooms.indexOf(player.getLocation())).getGraphicRoom());
                setGraphicLabelImageIcon(itemImageUrls);
            }
        }
        else {
            URL imageUrls = getClass().getClassLoader().getResource(rooms.get(rooms.indexOf(player.getLocation())).getGraphicMonster());
            setGraphicLabelImageIcon(imageUrls);
        }
    }

    private void setGraphicLabelImageIcon(URL itemImageUrls) {
        ImageIcon graphicIconsForItem = new ImageIcon(itemImageUrls);
        if (!player.getLocation().getName().equalsIgnoreCase("Entrance")) {
            //scale graphic Icons
            Image graphicImagesForItem = graphicIconsForItem.getImage(); // transform it
            graphicImagesForItem = graphicImagesForItem.getScaledInstance(graphicPanel.getWidth(), graphicPanel.getHeight(),
                    Image.SCALE_SMOOTH); // scale
            graphicLabel.setIcon(new ImageIcon(graphicImagesForItem));
        } else {
            graphicLabel.setIcon(graphicIconsForItem);
        }
    }

    private void endGame(Player player) {
        if (player.isGameOver()) {
            try {
                gameOver();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (player.isWonGame()) {
            try {
                congratulations();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class JTextFieldLimit extends PlainDocument {
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

    private void gameOver() throws IOException {
        // Custom button text
        Object[] options = {"Start New Game", "Quit Game"};

        JOptionPane pane = new JOptionPane(
                "Game Over! Would you like to start a new game or quit?",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION,
                null,
                options,
                options[0]);

        // Create JDialog and set attributes
        JDialog dialog = pane.createDialog(frame, "Game Over");
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);

        // Check which button was pressed
        String selectedOption = (String) pane.getValue();

        while (selectedOption == null) {
            // Reopen the dialog when the X button or escape is pressed
            dialog.setVisible(true);
            selectedOption = (String) pane.getValue();
        }

        if (selectedOption.equals(options[0])) {
            musicPlayer.stop();
            heartsoarTower.resetGame();
            heartsoarTower.gameLoop();
        } else if (selectedOption.equals(options[1])) {
            System.exit(0); // exit program
        }
    }

    private void congratulations() throws IOException {
        // Custom button text
        Object[] options = {"Start New Game", "Quit Game"};

        JOptionPane pane = new JOptionPane(
                "Congratulations! Would you like to start a new game or quit?",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION,
                null,
                options,
                options[0]);

        // Create JDialog and set attributes
        JDialog dialog = pane.createDialog(frame, "Congratulations");
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);

        // Check which button was pressed
        String selectedOption = (String) pane.getValue();

        while (selectedOption == null) {
            // Reopen the dialog when the X button or escape is pressed
            dialog.setVisible(true);
            selectedOption = (String) pane.getValue();
        }

        if (selectedOption.equals(options[0])) {
            musicPlayer.stop();
            heartsoarTower.resetGame();
            heartsoarTower.gameLoop();
        } else if (selectedOption.equals(options[1])) {
            System.exit(0); // exit program
        }
    }

    private String createInstructionScreen() {
        // HIDE TITLE SCREEN
        titleNamePanel.setVisible(false);
        //musicButtonPanel.setVisible(false);

        // TEXT PANEL
        instructionPanel = new JPanel();
        instructionPanel.setBackground(new Color(26, 83, 92));
        instructionPanel.setLayout(new GridBagLayout());
        instructionPanel.setSize(100, 100);

        // TEXT AREA
        introductionTextArea = new JTextArea(20, 90);
        introductionTextArea.setBackground(new Color(26, 83, 92));
        introductionTextArea.setForeground(Color.WHITE);
        introductionTextArea.setFont(storyFont);
        introductionTextArea.setLineWrap(true);
        introductionTextArea.setWrapStyleWord(true);
        introductionTextArea.setEditable(false);
        instructionPanel.add(introductionTextArea);

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
}
package rogue;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.TerminalPosition;


import javax.swing.JFrame;
import java.awt.Container;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileSystemView;

import java.io.File;

public class WindowUI extends JFrame {


    private SwingTerminal terminal;
    private TerminalScreen screen;
    public static final int WIDTH = 700;
    public static final int HEIGHT = 800;
    // Screen buffer dimensions are different than terminal dimensions
    public static final int COLS = 80;
    public static final int ROWS = 24;
    private int storeHeight = (2 * 2 * 2) + 2;
    private int storeWidth = storeHeight;
   private final char startCol = 0;
   private final char roomRow = 1;
   private Container contentPane;
   private JTextArea inventory = new JTextArea(storeHeight, storeWidth);
   private JTextArea clothing = new JTextArea(storeHeight, storeWidth);
   private JLabel playerName = new JLabel();
   private JLabel message = new JLabel();
   private Rogue game;

/**
Constructor.
**/

    public WindowUI() {
        super("Rogue");
    }

    /**
     * This method sets the Rogue game we're playing.
     * @param newGame the Rogue game
     */
    public void setGame(Rogue newGame) {
        game = newGame;
    }

    /**
     * This method return the Rogue game we are playing.
     * @return the Rogue game
     */
    public Rogue getGame() {
        return game;
    }

    /**
     * This method initializes the window.
     */
    public void startWindow() {
        contentPane = getContentPane();
        setWindowDefaults();
        setUpPanels();
        pack();
        start();
    }

    /**
     * This method updates the inventory and the clothes the player holds.
     */
    public void updateWindow() {
        contentPane = getContentPane();
        updateInventory();
        updateClothing();
    }

    private void setWindowDefaults() {
        setTitle("Rogue!");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contentPane.setLayout(new BorderLayout());

    }

    private void setTerminal() {
        JPanel terminalPanel = new JPanel();
        terminal = new SwingTerminal();
        terminalPanel.add(terminal);
        contentPane.add(terminalPanel, BorderLayout.WEST);
    }

    private void setUpPanels() {
        JPanel inventoryPanel = new JPanel();
        makeMenuBar();
        setUpLabelPanel();
        setUpCommentPanel();
        setUpInventoryPanel(inventoryPanel);
        setTerminal();
    }

    private void makeMenuBar() {
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        JMenu optionsMenu = new JMenu("Options");
        menubar.add(optionsMenu);

        JMenuItem loadJSON = new JMenuItem("Load Map");
        loadJSON.addActionListener(ev -> loadJson());
        JMenuItem save = new JMenuItem("Save Game");
        save.addActionListener(ev -> saveGame());
        JMenuItem load = new JMenuItem("Load Game");
        load.addActionListener(ev -> loadGame());
        JMenuItem changeName = new JMenuItem("Change Name");
        changeName.addActionListener(ev -> nameChange());
        optionsMenu.add(loadJSON);
        optionsMenu.add(load);
        optionsMenu.add(save);
        optionsMenu.add(changeName);
    }

    private String fileChooser() {
        File f = null;
        try {
            f = new File(new File(".").getCanonicalPath());
        } catch (IOException e) {

        }

        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getParentDirectory(f));
        int rValue = jfc.showOpenDialog(null);
        if (rValue == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            return file.getPath();
        }
        return null;
    }

    private void saveGame() {
        Serialize save = new Serialize();
        save.serializeGame(game);
        JOptionPane.showMessageDialog(this, "Your game is saved!");
    }

    private void loadGame() {
        Deserialize load = new Deserialize();
        String file = fileChooser();
        game = load.deserializeGame(file);
        while (game == null) {
            JOptionPane.showMessageDialog(this, "Select the right File!", "Inane error", JOptionPane.ERROR_MESSAGE);
            loadGame();
        }
        this.setGame(game);
        game.verifyRooms();
        this.updateWindow();
        JOptionPane.showMessageDialog(this, "Your game is loaded!");
        message.setText("The game is loaded!");
        draw(game.getNextDisplay());
    }

    private void loadJson() {
        String file = fileChooser();
        RogueParser parser = new RogueParser(file);
        Player gamePlayer = game.getPlayer();
        game = new Rogue(parser);
        game.setPlayer(gamePlayer);
        this.setGame(game);
        this.updateWindow();
        message.setText("The new map is loaded!");
        draw(game.getNextDisplay());
    }

    private void nameChange() {
        String input = JOptionPane.showInputDialog(this, "Enter a new Name: ");
        game.getPlayer().setName(input);
        playerName.setText("Player: " + game.getPlayer().getName());
    }

    private void setUpLabelPanel() {
        JPanel thePanel = new JPanel();
        Border prettyLine = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        thePanel.setBorder(prettyLine);
        playerName.setText("Player: " + game.getPlayer().getName());
        thePanel.add(playerName);
        contentPane.add(thePanel, BorderLayout.NORTH);
    }

    private void setUpCommentPanel() {
        JPanel thePanel = new JPanel();
        Border prettyLine = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        thePanel.setBorder(prettyLine);
        message.setText("Welcome to the Game!");
        thePanel.add(message);
        contentPane.add(thePanel, BorderLayout.SOUTH);
    }

    /**
     * This method sets the message to be displayed at the bottom of the screen.
     * @param msg the message to display
     */
    public void setMessageToDisplay(String msg) {
        if (msg != null) {
            message.setText(msg);
        }
    }

    private void setUpInventoryPanel(JPanel thePanel) {
        JPanel inventoryList = new JPanel(new GridLayout(2, 1));
        setUpInventoryList(inventoryList);
        setUpWearList(inventoryList);
        thePanel.add(inventoryList);
        contentPane.add(thePanel, BorderLayout.EAST);
    }

    private void setUpInventoryList(JPanel thePanel) {
        thePanel.setLayout(new GridLayout(2, 1));
        JLabel heading = new JLabel("Current Inventory: ");
        thePanel.add(heading);
        inventory.setEditable(false);
        JScrollPane scroll = new JScrollPane(inventory);
        thePanel.add(scroll);
    }

    private void setUpWearList(JPanel thePanel) {
        thePanel.setLayout(new GridLayout(2, 1));
        JLabel heading = new JLabel("Clothing: ");
        thePanel.add(heading);
        clothing.setEditable(false);
        JScrollPane scroll = new JScrollPane(clothing);
        thePanel.add(scroll);
    }

    private void updateInventory() {
        String items = "";
        for (Item item: game.getPlayer().getInventory()) {
            items += item.getName();
            items += "\n";
        }
        inventory.setText(items);
    }

    private void updateClothing() {
        String items = "";
        for (Item item: game.getPlayer().getClothes()) {
            items += item.getName();
            items += "\n";
        }
        clothing.setText(items);
    }

    private void start() {
        try {
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(TerminalPosition.TOP_LEFT_CORNER);
            screen.startScreen();
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        /**
    Prints a string to the screen starting at the indicated column and row.
    @param toDisplay the string to be printed
    @param column the column in which to start the display
    @param row the row in which to start the display
    **/
    public void putString(String toDisplay, int column, int row) {

            Terminal t = screen.getTerminal();
            try {
                t.setCursorPosition(column, row);
            for (char ch: toDisplay.toCharArray()) {
                t.putCharacter(ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void clearScreen() {
        for (int i = 0; i < (storeHeight * 2 * 2 * 2); i++) {
            putString("                                                          ", 0, i);
        }
    }

    /**
     * Redraws the whole screen including the room and the message.
     * @param room the room map to be drawn
     */
        public void draw(String room) {

            try {
                clearScreen();
                putString(room, startCol, roomRow);
                screen.refresh();
            } catch (IOException e) {

            }

        }

        /**
         * Obtains input from the user and returns it as a char.  Converts arrow
         * keys to the equivalent movement keys in rogue.
         * @return the ascii value of the key pressed by the user
        */
        public char getInput() {
            KeyStroke keyStroke = null;
            char returnChar;
            while (keyStroke == null) {
                try {
                    keyStroke = screen.pollInput();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            returnChar = getKeyStroke(keyStroke);
            return returnChar;
        }

        private char getKeyStroke(KeyStroke keyStroke) {
            char returnChar;
            if (keyStroke.getKeyType() == KeyType.ArrowDown) {
                returnChar = Rogue.DOWN;  //constant defined in rogue
            } else if (keyStroke.getKeyType() == KeyType.ArrowUp) {
                returnChar = Rogue.UP;
            } else if (keyStroke.getKeyType() == KeyType.ArrowLeft) {
                returnChar = Rogue.LEFT;
            } else if (keyStroke.getKeyType() == KeyType.ArrowRight) {
                returnChar = Rogue.RIGHT;
            } else {
                returnChar = keyStroke.getCharacter();
            }
            return returnChar;
        }

    /**
     * This method checks if the player wants to toss, eat or wear an item.
     * @param input either 't','w', or 'e'
     * @return it returns the message behind the action taken
     */
    public String checkAction(char input) {
        String msg = null;
        if (input == 't') {
            Item toToss = tossItem();
            if (toToss != null) {
                msg = ((Tossable) toToss).toss();
            }
        }
        if (input == 'e') {
            Item toEat = eatItem();
            if (toEat != null) {
                msg = ((Edible) toEat).eat();
            }
        }
        if (input == 'w') {
            Item toWear = wearItem();
            if (toWear != null) {
                msg = ((Wearable) toWear).wear();
            }
        }
        this.updateWindow();
        return msg;
    }

    private Item eatItem() {
        ArrayList<String> list = new ArrayList<String>();
        for (Item i: game.getPlayer().getInventory()) {
            if (i != null) {
                String type = i.getType();
                if (type.equals("Food") || type.equals("Potion") || type.equals("Small Food")) {
                    list.add(i.getName());
                }
            }
        }
        Object[] list1 = list.toArray();
        JComboBox jcb = new JComboBox(list1);
        jcb.setEditable(false);
        JOptionPane.showMessageDialog(this, jcb, "Select the item to Eat", JOptionPane.QUESTION_MESSAGE);
        String item = jcb.getSelectedItem().toString();
        if (item != null) {
            return (getItemByName(jcb.getSelectedItem().toString()));
        }
        return null;
    }

    private Item wearItem() {
        ArrayList<String> list = new ArrayList<String>();
        for (Item i: game.getPlayer().getInventory()) {
            String type = i.getType();
            if (i != null) {
                if (type.equals("Clothing") || type.equals("Ring")) {
                    list.add(i.getName());
                }
            }
        }
        Object[] list1 = list.toArray();
        JComboBox jcb = new JComboBox(list1);
        jcb.setEditable(false);
        JOptionPane.showMessageDialog(this, jcb, "Select the item to Wear", JOptionPane.QUESTION_MESSAGE);
        String item = jcb.getSelectedItem().toString();
        if (item != null) {
            return (getItemByName(jcb.getSelectedItem().toString()));
        }
        return null;
    }

    private Item tossItem() {
        ArrayList<String> list = new ArrayList<String>();
        for (Item i: game.getPlayer().getInventory()) {
            String type = i.getType();
            if (i != null) {
                if (type.equals("Potion") || type.equals("SmallFood")) {
                    list.add(i.getName());
                }
            }
        }
        Object[] list1 = list.toArray();
        JComboBox jcb = new JComboBox(list1);
        jcb.setEditable(false);
        JOptionPane.showMessageDialog(this, jcb, "Select the item to Toss", JOptionPane.QUESTION_MESSAGE);
        String item = jcb.getSelectedItem().toString();
        if (item != null) {
            return (getItemByName(jcb.getSelectedItem().toString()));
        }
        return null;
    }

    private Item getItemByName(String name) {
        for (Item i: game.getPlayer().getInventory()) {
            if (name.equals(i.getName())) {
                return i;
            }
        }
        return null;
    }

    /**
     * This method makes the game ready for us.
     * @param fileName the JSON file containing Rooms and symbols
     * @param player the player starting the game
     * @return the Rogue game
     */
    public Rogue selfSetup(String fileName, Player player) {
        RogueParser parser = new RogueParser(fileName);
        Rogue theGame = new Rogue(parser);
        theGame.setPlayer(player);
        setGame(theGame);
        startWindow();
        setMessageToDisplay("Welcome to my Rogue Game!");
        draw(theGame.getNextDisplay());
        setVisible(true);
        return theGame;
    }

    /**
    The controller method for making the game logic work.
    @param args command line parameters
    **/
    public static void main(String[] args) {
    char userInput = 'h';
    String configurationFileLocation = "fileLocations.json";
    WindowUI theGameUI = new WindowUI();
    Player thePlayer = new Player("Judi");
    Rogue theGame = theGameUI.selfSetup(configurationFileLocation, thePlayer);
    while (userInput != 'q') {
    userInput = theGameUI.getInput();
    try {
        theGame = theGameUI.getGame();
        theGameUI.setMessageToDisplay(theGameUI.checkAction(userInput));
        theGameUI.setMessageToDisplay(theGame.makeMove(userInput));
        theGameUI.updateWindow();
        theGameUI.draw(theGame.getNextDisplay());
    } catch (InvalidMoveException badMove) {
        theGameUI.setMessageToDisplay(badMove.getMessage());
    }
    }
    }

}

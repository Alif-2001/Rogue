package rogue;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.gui2.LayoutManager;

import javax.swing.JFrame;
import java.awt.Container;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileSystemView;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

public class WindowUI extends JFrame {


    private SwingTerminal terminal;
    private TerminalScreen screen;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 800;
    // Screen buffer dimensions are different than terminal dimensions
    public static final int COLS = 80;
    public static final int ROWS = 24;
   private final char startCol = 0;
   private final char msgRow = 1;
   private final char roomRow = 3;
   private Container contentPane;
   private JTextArea inventory = new JTextArea(10, 10);
   private JTextArea clothing = new JTextArea(10, 5);
   private JLabel playerName = new JLabel();
   private Rogue game;

/**
Constructor.
**/

    public WindowUI() {
        super("Rogue");
    }

    public void setGame(Rogue newGame){
        game = newGame;
    }

    public Rogue getGame(){
        return game;
    }

    public void startWindow(){
        contentPane = getContentPane();
        setWindowDefaults(getContentPane());
        setUpPanels();
        pack();
        start();
    }

    public void updateWindow(){
        contentPane = getContentPane();
        updateInventory();
        //updateTerminal();
        updateClothing();
    }

    private void setWindowDefaults(Container contentPane) {
        setTitle("Rogue!");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contentPane.setLayout(new BorderLayout());

    }

    private void setTerminal() {
        JPanel terminalPanel = new JPanel();
        terminal = new SwingTerminal();
        terminalPanel.add(terminal);
        contentPane.add(terminalPanel,BorderLayout.WEST);
    }

    private void setUpPanels(){
        JPanel inventoryPanel = new JPanel();
        makeMenuBar();
        setUpLabelPanel();
        setUpInventoryPanel(inventoryPanel);
        setTerminal();
    }

    private void makeMenuBar(){
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
        changeName.addActionListener(ev -> NameChange());

        optionsMenu.add(loadJSON);
        optionsMenu.add(load);
        optionsMenu.add(save);
        optionsMenu.add(changeName);
    }

    private String FileChooser(){
        File f = null;
        try{
            f = new File(new File(".").getCanonicalPath());
        }catch (IOException e){

        }

        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getParentDirectory(f));
        int rValue = jfc.showOpenDialog(null);
        if(rValue == JFileChooser.APPROVE_OPTION){
            File file = jfc.getSelectedFile();
            return file.getPath();
        }
        return null;
    }

    private void saveGame(){
        Serialize save = new Serialize();
        save.SerializeGame(game);
    }

    private void loadGame(){
        Deserialize load = new Deserialize();
        String file = FileChooser();
        game = load.DeserializeGame(file);
        while(game == null){
            loadGame();
        }
        this.setGame(game);
        game.verifyRooms();
        this.updateWindow();
        draw("The game is loaded", game.getNextDisplay());
    }

    private void loadJson(){
        
    }

    private void NameChange(){
        String input = JOptionPane.showInputDialog(this, "Enter a new Name: ");
        game.getPlayer().setName(input);
        playerName.setText("Player: " + game.getPlayer().getName());
    }

    private void setUpLabelPanel(){
        JPanel thePanel = new JPanel();
        Border prettyLine = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        thePanel.setBorder(prettyLine);
        playerName.setText("Player: "+game.getPlayer().getName());
        thePanel.add(playerName);
        contentPane.add(thePanel, BorderLayout.SOUTH);
    }

    private void setUpInventoryPanel(JPanel thePanel){
        JPanel inventoryList = new JPanel(new GridLayout(2,1));
        setUpInventoryList(inventoryList);
        setUpWearList(inventoryList);
        thePanel.add(inventoryList);
        contentPane.add(thePanel, BorderLayout.EAST);
    }

    private void setUpInventoryList(JPanel thePanel){
        thePanel.setLayout(new GridLayout(2,1));
        JLabel heading = new JLabel("Current Inventory: ");
        thePanel.add(heading);
        inventory.setEditable(false);
        JScrollPane scroll = new JScrollPane(inventory);
        thePanel.add(scroll);
    }

    private void setUpWearList(JPanel thePanel){
        thePanel.setLayout(new GridLayout(2,1));
        JLabel heading = new JLabel("Clothing: ");
        thePanel.add(heading);
        clothing.setEditable(false);
        JScrollPane scroll = new JScrollPane(clothing);
        thePanel.add(scroll);
    }

    private void updateInventory(){
        String items = "";
        for(Item item: game.getPlayer().getInventory()){
            items += item.getName();
            items += "\n";
        }
        inventory.setText(items);
    }

    private void updateClothing(){
        String items = "";
        for (Item item: game.getPlayer().getClothes()){
            items += item.getName();
            items += "\n";
        }
        clothing.setText(items);
    }

    private void updateTerminal(){
        terminal = new SwingTerminal();
        start();
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

/**
Changes the message at the top of the screen for the user.
@param msg the message to be displayed
**/
            public void setMessage(String msg) {
                clearScreen();
                putString(msg, startCol, msgRow);
            }

            private void clearScreen(){
                for(int i = 0; i < 50; i++) {
                    putString("                                                          ", 0, i);
                }
            } 

/**
Redraws the whole screen including the room and the message.
@param message the message to be displayed at the top of the room
@param room the room map to be drawn
**/
            public void draw(String message, String room) {

                try {
                    setMessage(message);
                    putString(room, startCol, roomRow);
                    screen.refresh();
                } catch (IOException e) {

                }

        }

/**
Obtains input from the user and returns it as a char.  Converts arrow
keys to the equivalent movement keys in rogue.
@return the ascii value of the key pressed by the user
**/
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

    public void checkAction(char input){
        if(input == 't'){
            Item toToss = tossItem();
            String msg = ((Tossable)toToss).toss();
        }
        if(input == 'e'){
            Item toEat = eatItem();
            String msg = ((Edible)toEat).eat();
        }
        if(input == 'w'){
            Item toWear = wearItem();
            String msg = ((Wearable)toWear).wear();            
        }
        this.updateWindow();
    }

    private Item eatItem(){
        ArrayList<String> list = new ArrayList<String>();
        for(Item i: game.getPlayer().getInventory()){
            if(i != null){
                String type = i.getType();
                if(type.equals("Food") || type.equals("Potion") || type.equals("Small Food")){
                    list.add(i.getName());
                }
            }
        }
        Object[] list1 = list.toArray();
        JComboBox jcb = new JComboBox(list1);
        jcb.setEditable(false);
        JOptionPane.showMessageDialog(this, jcb, "Select the item to eat", JOptionPane.QUESTION_MESSAGE);
        String item = jcb.getSelectedItem().toString();
        return (getItemByName(jcb.getSelectedItem().toString()));
    }

    private Item wearItem(){
        ArrayList<String> list = new ArrayList<String>();
        for(Item i: game.getPlayer().getInventory()){
            String type = i.getType();
            if(i != null){
                if(type.equals("Clothing") || type.equals("Ring")){
                    list.add(i.getName());
                }
            }
        }
        Object[] list1 = list.toArray();
        JComboBox jcb = new JComboBox(list1);
        jcb.setEditable(false);
        JOptionPane.showMessageDialog(this, jcb, "Select the item to wear", JOptionPane.QUESTION_MESSAGE);
        return (getItemByName(jcb.getSelectedItem().toString()));
    }

    private Item tossItem(){
        ArrayList<String> list = new ArrayList<String>();
        for(Item i: game.getPlayer().getInventory()){
            String type = i.getType();
            if(i != null){
                if(type.equals("Potion") || type.equals("SmallFood")){
                    list.add(i.getName());
                }
            }
        }
        Object[] list1 = list.toArray();
        JComboBox jcb = new JComboBox(list1);
        jcb.setEditable(false);
        JOptionPane.showMessageDialog(this, jcb, "Select the item to toss", JOptionPane.QUESTION_MESSAGE);
        return (getItemByName(jcb.getSelectedItem().toString()));
    }

    private Item getItemByName(String name){
        for(Item i: game.getPlayer().getInventory()){
            if(name.equals(i.getName())){
                return i;
            }
        }
        return null;
    }
/**
The controller method for making the game logic work.
@param args command line parameters
**/
    public static void main(String[] args) {

    char userInput = 'h';
    String message;
    String configurationFileLocation = "fileLocations.json";
    //Parse the json files
    RogueParser parser = new RogueParser(configurationFileLocation);
    //allocate memory for the GUI
    WindowUI theGameUI = new WindowUI();
    // allocate memory for the game and set it up
    Rogue theGame = new Rogue(parser);
   //set up the initial game display
    Player thePlayer = new Player("Judi");
    theGame.setPlayer(thePlayer);
    message = "Welcome to my Rogue game";

    theGameUI.setGame(theGame);
    theGameUI.startWindow();
    theGameUI.draw(message, theGame.getNextDisplay());
    theGameUI.setVisible(true);

    while (userInput != 'q') {
    //get input from the user
    userInput = theGameUI.getInput();

    //ask the game if the user can move there
    try {
        theGame = theGameUI.getGame();
        theGameUI.checkAction(userInput);
        message = theGame.makeMove(userInput);
        theGameUI.updateWindow();
        theGameUI.draw(message, theGame.getNextDisplay());
    } catch (InvalidMoveException badMove) {
        message = "I didn't understand what you meant, please enter a command";
        theGameUI.setMessage(message);
    }
    }
    }

}

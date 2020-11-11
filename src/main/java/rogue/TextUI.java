package rogue;

//We will need these imports but you don't need them to get started

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
// import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
// import com.googlecode.lanterna.screen.VirtualScreen;
import com.googlecode.lanterna.terminal.ansi.UnixTerminal;

import rogue.rogueExceptions.InvalidMoveException;

// import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.TerminalPosition;
// import com.googlecode.lanterna.TerminalSize;

import java.io.IOException;
//import java.util.ArrayList;

public class TextUI {
    private TerminalScreen screen;
    private final char startCol = 1;
    private final char msgRow = 1;
    private final char roomRow = 3;

    /**
     * TextUI constructor.
     */
    public TextUI() {
        super();
        try {
            screen = new TerminalScreen(new UnixTerminal());
            screen.setCursorPosition(TerminalPosition.TOP_LEFT_CORNER);
            screen.startScreen();


            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is used to put the string to display on the terminal.
     * @param toDisplay
     * @param column
     * @param row
     */
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
     * This method is used to set the message to be sent to the terminal.
     * @param msg message to be displayed
     */
    public void setMessage(String msg) {
        putString("                                                ", 1, 1);
        putString(msg, startCol, msgRow);
    }

    /**
     * This method is used to put the string on the terminal and refresh.
     * @param message message to display
     * @param room room to display
     */
    public void draw(String message, String room) {
        try {
            setMessage(message);
            putString(room, startCol, roomRow);
            screen.refresh();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to get movement input from user.
     * @return the key user pressed
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
     * A2 solution, play game by moving around in the rooms.
     * @param args comand line input.
     */
    public static void main(String[] args) {
        char userInput = 'w';
        String message;
        String configurationFileLocation = "fileLocations.json";
    //create an instance of the UI
        TextUI theGameUI = new TextUI();
    // create an instance of the Game
        RogueParser parser = new RogueParser(configurationFileLocation);
        Rogue theGame = new Rogue(parser);

    //send the output of the rooms to the GUI and show
        message = "Welcome to my Rogue game";
        theGameUI.draw(message, theGame.getNextDisplay());


        while (userInput != 'q') {
            userInput = theGameUI.getInput();

            try {
                message = theGame.makeMove(userInput);
                theGameUI.draw(message, theGame.getNextDisplay());
            } catch (InvalidMoveException badMove) {
                theGameUI.setMessage(badMove.getMessage());
            }
        }



   /* here there will be logic to run the game and provide
   the output to the UI.
   This main method is the controller for communication betwee
   your rogue game and the UI*/

   /*for now just let this be a no-op main while you
   refactor your code to pass the linter checks
    Once you have it passing the linter, feel free to add some of your A1 solution
    in here so that you can test the refactoring in step 3.

   */

    }
}

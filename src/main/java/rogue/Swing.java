package rogue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.googlecode.lanterna.gui2.LayoutManager;

import javafx.geometry.Dimension2D;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;


public class Swing extends JFrame{
    public static final int WIDTH = 500;
    public static final int HEIGHT = 300;

    private Container contentPane;

    public Swing(){
        super("Rogue");
        setDefaults();
        setUpPanels();

    }

    private void setDefaults(){
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = getContentPane();
    }

    private void setUpPanels(){
        Border blackline = BorderFactory.createLineBorder(Color.black);
        FlowLayout layout = new FlowLayout();
        JPanel labelPanel = new JPanel();
        JPanel textPanel = new JPanel();
        JPanel textPanel2 = new JPanel();
        JPanel textPanel3 = new JPanel();
        
        labelPanel.add(new JLabel("Tomorrow"));
        labelPanel.setBorder(blackline);
        
        textPanel.setLayout(new GridLayout(3,1));
        textPanel2.setBorder(blackline);
        textPanel2.add(new JLabel("Hello"));
        textPanel3.setBorder(blackline);
        textPanel3.add(new JLabel("Hello"));

        textPanel.add(textPanel2);
        textPanel.add(textPanel3);
        
        contentPane.setLayout(layout);
        
        contentPane.add(textPanel);
        contentPane.add(labelPanel);
    }

    public static void main(String[] args){
        Swing game = new Swing();
        game.setVisible(true);
    }
}

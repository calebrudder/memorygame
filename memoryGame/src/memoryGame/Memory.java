package memoryGame;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

public class Memory extends JFrame {

    private GameBoard  gameBoard = new GameBoard();

    public static void main(String[] args) {
        Memory memory = new Memory();

    }

    public Memory() {
        setSize(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(gameBoard);
        setVisible(true);
                
                MenuBar menuBar = new MenuBar();
                Menu gameMenu = new Menu("Game");
                MenuItem startOver = new MenuItem("Start Over", new MenuShortcut(KeyEvent.VK_E));
                MenuItem showSolution = new MenuItem("Show Solution", new MenuShortcut(KeyEvent.VK_S));
                MenuItem viewHighScores = new MenuItem("View High Scores", new MenuShortcut(KeyEvent.VK_H));
                
                gameMenu.add(startOver);
                gameMenu.add(showSolution);
                gameMenu.add(viewHighScores);
                menuBar.add(gameMenu);
                setMenuBar(menuBar);
                
                startOver.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       gameBoard.startOver();
                   }
                });
                
                showSolution.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       gameBoard.showSolution();
                   }
                });
                
                viewHighScores.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       try {
                           gameBoard.viewHighScores();
                       } catch (FileNotFoundException exception) {
                           System.out.println(exception);
                       } catch (IOException exception) {
                           System.out.println(exception);
                       }
                   }
                });
    }
}

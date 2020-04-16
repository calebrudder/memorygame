package memoryGame;

import java.awt.Color;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;

import javax.swing.JPanel;

public class GameBoard extends JPanel {

    private final int ROWS = 4;
    private final int COLUMNS = 4;

    private LocalTime startTime;
    private LocalTime endTime;
    private boolean gameOver;
    private boolean cheated;

    private Card[][] cells = new Card[ROWS][COLUMNS];
    private Color[] colorOptions = {Color.red, Color.orange,
        Color.yellow, Color.green,
        Color.lightGray, Color.blue,
        Color.magenta, Color.pink};

    private ArrayList<Color> colors = new ArrayList<Color>();

    private Card cardSelected;

    public GameBoard() {
        setLayout(new GridLayout(ROWS, COLUMNS));
        InitializeGameBoard();

    }

    private void InitializeGameBoard() {
        cheated = false;

        for (int i = 0; i < (ROWS * COLUMNS) / 2; i++) {
            Random rand = new Random();
            int colorIndex = rand.nextInt(colorOptions.length);

            colors.add(colorOptions[colorIndex]);
            colors.add(colorOptions[colorIndex]);
        }

        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                Card newCard = new Card("");

                Random rand = new Random();
                int colorIndex = rand.nextInt(colors.size());

                newCard.setCorrectColor(colors.get(colorIndex));
                colors.remove(colorIndex);

                newCard.setGameBoard(this);

                cells[row][column] = newCard;
                add(newCard);
            }
        }
        startTime = LocalTime.now();
    }
    
    public void startOver() {
        for (Card[] cards : cells) {
            for (Card card : cards) {
                remove(card);
            }
        }
        revalidate();
        repaint();
        InitializeGameBoard();
    }
    
    public void showSolution() {
        for (Card[] cards : cells) {
            for (Card card : cards) {
                card.showCard();
            }
        }
        cheated = true;
        revalidate();
        repaint(); 
    }
    
    public void cardClicked(Card card) throws IOException {
        if (!card.isMatched()) {
            if (cardSelected == null) {
                cardSelected = card;
            } else {
                compareCards(card);
            }
        }
    }

    public void compareCards(Card card) throws IOException {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!card.equals(cardSelected)) {
                    card.flipOver();
                    cardSelected.flipOver();
                    cardSelected = null;
                } else {
                    card.setMatched(true);
                    cardSelected.setMatched(true);
                    cardSelected = null;
                    try {
                        checkBoard();
                    } catch (IOException exception) {
                        System.out.println(exception);
                    }
                }
            }
        }, 1000);
    }

    private void checkBoard() throws IOException {
        gameOver = true;
        for (Card[] cards : cells) {
            for (Card card : cards) {
                if (!card.isMatched()) {
                    gameOver = false;
                }
            }
        }
        if (gameOver && !cheated) {
            endTime = LocalTime.now();

            PrintWriter outFile;

            if (new File("highscore.txt").exists()) {
                outFile = new PrintWriter(new FileWriter("highscore.txt", true));
            } else {
                outFile = new PrintWriter(new FileWriter("highscore.txt"));
            }

            Duration timeScore = Duration.between(startTime, endTime);
            float scoreInMinutes = (float) timeScore.getSeconds() / 60;

            outFile.println(ROWS + "x" + COLUMNS + " in " + scoreInMinutes + " minutes");
            outFile.close();
        }
    }
    
    public void viewHighScores() throws FileNotFoundException, IOException {
        if (new File("highscore.txt").exists()) {
            BufferedReader in = new BufferedReader(new FileReader("highscore.txt"));
            String highscore = "";
            String allHighscores = "";
            while ((highscore = in.readLine()) != null) {
                allHighscores += highscore + "\n";
            }
            
            JOptionPane.showMessageDialog(null, allHighscores);
        }
    }
}
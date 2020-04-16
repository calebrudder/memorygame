package memoryGame;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;

public class Card extends JButton {

    private Color correctColor;
    private boolean matched;
    private GameBoard gameBoard;

    public Card(String label) {
        super(label);
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    cardClicked(e);
                } catch (IOException exception) {
                    System.out.println(exception);
                }
            }
        });
    }

    private void cardClicked(MouseEvent e) throws IOException {
        this.setOpaque(true);
        this.setBackground(correctColor);
        this.setBorderPainted(false);
        gameBoard.cardClicked(this);
    }

    public void flipOver() {
        this.setOpaque(false);
        this.setBackground(null);
        this.setBorderPainted(true);
    }

    public void showCard() {
        this.setOpaque(true);
        this.setBackground(correctColor);
        this.setBorderPainted(false);
    }

    public Color getCorrectColor() {
        return correctColor;
    }

    public void setCorrectColor(Color correctColor) {
        this.correctColor = correctColor;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Card)) {
            return false;
        }
        Card card = (Card) o;
        return card.correctColor.equals(this.correctColor);
    }

}

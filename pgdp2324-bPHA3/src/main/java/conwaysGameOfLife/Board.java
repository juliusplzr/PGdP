package conwaysGameOfLife;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.GridLayout;
import javax.swing.*;

public class Board {
    private final JFrame frame;
    private Tile[][] board;
    private int genCounter;

    public Board(int height, int width, int delay) {
        board = new Tile[height][width];
        genCounter = 0;

        Random randomGenerator = new Random();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = randomGenerator.nextBoolean() ? Tile.ALIVE : Tile.DEAD;
            }
        }

        frame = new JFrame("Board");
        frame.setBounds(100, 100, 760, 523);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(height, width, 0, 0));

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                JComponent comp = board[i][j].getIcon();
                comp.setToolTipText(board[i][j].toString() + " (" + i + ", " + j + ")");
                frame.getContentPane().add(comp);
            }
        }

        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        frame.setSize(800, 800);

        System.out.println("Generation = 0");

        int population = 0;

        for (int i = 0; i < height; i++)  {
            for (int j = 0; j < width; j++) {
                if (board[i][j] == Tile.ALIVE) {
                    population++;
                }
            }
        }

        System.out.println("Population = " + population);
        System.out.println("--------------------------");

        initializeTimer(delay);
    }

    public void nextGen() {
        int height = board.length;
        int width = board[0].length;

        Tile[][] nextGen = new Tile[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int neighboursAlive = 0;

                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (withinBounds(i, dx, j, dy) && board[i + dx][j + dy] == Tile.ALIVE) {
                            neighboursAlive++;
                        }
                    }
                }

                nextGen[i][j] = board[i][j];

                if (board[i][j] == Tile.ALIVE) {
                    neighboursAlive--;

                    if (neighboursAlive < 2 || neighboursAlive > 3) {
                        nextGen[i][j] = Tile.DEAD;
                    }
                } else {
                    if (neighboursAlive == 3) {
                        nextGen[i][j] = Tile.ALIVE;
                    }
                }
            }
        }

        board = nextGen;
    }

    public void refreshBoard() {
        frame.getContentPane().removeAll();

        int height = board.length;
        int width = board[0].length;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                JComponent comp = board[i][j].getIcon();
                comp.setToolTipText(board[i][j].toString() + " (" + i + ", " + j + ")");
                frame.getContentPane().add(comp);
            }
        }

        frame.validate();
        frame.repaint();

        genCounter++;
        System.out.println("Generation = " + genCounter);

        int population = 0;

        for (int i = 0; i < height; i++)  {
            for (int j = 0; j < width; j++) {
                if (board[i][j] == Tile.ALIVE) {
                    population++;
                }
            }
        }

        System.out.println("Population = " + population);
        System.out.println("--------------------------");
    }

    public void initializeTimer(int delay) {
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextGen();
                refreshBoard();
            }
        });

        timer.start();
    }

    private boolean withinBounds(int rowIndex, int dx, int colIndex, int dy) {
        return ((rowIndex + dx) >= 0 && (rowIndex + dx) < board.length) &&
                (colIndex + dy >= 0 && colIndex + dy < board[0].length);
    }

    public static void main(String[] args) {
        Board testBoard = new Board(100,100, 1000);
    }
}

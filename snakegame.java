import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SnakeGame extends JFrame {
    private SnakePanel snakePanel;
    private Timer timer;

    public SnakeGame() {
        setTitle("Snake Game");
        snakePanel = new SnakePanel();
        add(snakePanel);
        pack();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                snakePanel.processKey(e.getKeyCode());
            }
        });

        timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                snakePanel.update();
                snakePanel.repaint();
            }
        });

        setVisible(true);
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SnakeGame();
            }
        });
    }
}

class SnakePanel extends JPanel {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final int UNIT_SIZE = 20;
    private static final int GAME_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private static final int DELAY = 100;
    private int[] x = new int[GAME_UNITS];
    private int[] y = new int[GAME_UNITS];
    private int bodyParts = 6;
    private int applesEaten = 0;
    private int appleX;
    private int appleY;
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;

    public SnakePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        setLayout(null);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                processKey(e.getKeyCode());
            }
        });

        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(new Color(45, 180, 0));
                }
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    public void update() {
        if (running) {
            for (int i = bodyParts; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }

            switch (direction) {
                case 'U':
                    y[0] = y[0] - UNIT_SIZE;
                    break;
                case 'D':
                    y[0] = y[

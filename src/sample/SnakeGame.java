package sample;

import java.util.Random;

public class SnakeGame {
    /**
     * Zakres x pola gry.
     */
    private int xBound;
    /**
     * Zakres y pola gry.
     */
    private int yBound;
    private Snake snake;
    private Point apple;
    private DisplayBoardStrategy displayBoardStrategy;

    /**
     * Konstruktor tworzy grę w węża dla zadanych rozmiaru planszy oraz węża.
     * @param xBound
     * @param yBound
     * @param snake
     */
    public SnakeGame(int xBound, int yBound, Snake snake) {
        this.snake = snake;
        initBoard(xBound, yBound);
    }

    /**
     * Konstruktor tworzy grę w węża dla zadanego rozmiaru planszy
     * @param xBound
     * @param yBound
     */
    public SnakeGame(int xBound, int yBound) {
        Point head = new Point(0, 0);
        this.snake = new Snake(head);
        initBoard(xBound, yBound);
    }

    public Point getApple() {
        return apple;
    }

    public void setSnakeDirection(Direction direction) {
        snake.setDirection(direction);
    }

    public Snake getSnake() {
        return snake;
    }

    public void setDisplayBoardStrategy(DisplayBoardStrategy displayBoardStrategy) {
        this.displayBoardStrategy = displayBoardStrategy;
    }

    /**
     * Metoda inicjalizuje planszę - ustawia jej rozmiar oraz losuje jabłko.
     * @param xBound
     * @param yBound
     */
    private void initBoard(int xBound, int yBound) {
        this.xBound = xBound;
        this.yBound = yBound;
        randomizeApple();
    }

    /**
     * Metoda startuje rozgrywkę - w pętli, póki wąż się nie rozbije wykonywane są kolejne ruchy węża.
     */
    public void start() {
        if (displayBoardStrategy != null) {
            // to jej użyjemy.
            displayBoardStrategy.display();
        }
        // Póki wąż się nie rozbił
        while (!isSnakeOutOfBounds()) {
            // Wąż rośnie w kierunku jego ruchu
            snake.expand();
            // Jeśli głowa węża nie leży na jabłku
            if (!apple.equals(snake.getHead())) {
                // Ucinamy ogon węża.
                snake.cutTail();
            } else {
                // W innym wypadku (wąż zjada jabłko) losujemy nowe jabłko.
                randomizeApple();
            }
            // Jeśli mamy ustawioną strategię przedstawiania planszy gry,
            if (displayBoardStrategy != null) {
                // to jej użyjemy.
                displayBoardStrategy.display();
            }
            try {
                // Poczekamy sekundę.
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    /**
     * Sprawdza, czy wąż (jego głowa) mieści się w polu gry.
     * @return
     */
    private boolean isSnakeOutOfBounds() {
        int headX = snake.getHead().getX();
        int headY = snake.getHead().getY();
        return headX < 0 || headX >= xBound
                || headY < 0 || headY >= yBound;
    }

    /**
     * Losuje nowe jabłko (tak, aby nie leżało na wężu).
     */
    public void randomizeApple() {
        Random random = new Random ();
        do {
            // Wylosuj nowe jabłko
            int appleX = random.nextInt(xBound);
            int appleY = random.nextInt(yBound);
            apple = new Point(appleX, appleY);
            // Póki jabłko leży na wężu powtarzaj losowanie.
        } while (snake.contains(apple));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < yBound; y++) {
            stringBuilder.append('|');
            for (int x = 0; x < xBound; x++) {
                Point point = new Point(x, y);
                if (point.equals(snake.getHead())) {
                    stringBuilder.append('H');
                } else if (snake.getBody().contains(point)) {
                    stringBuilder.append('B');
                } else if (point.equals(apple)) {
                    stringBuilder.append('A');
                } else {
                    stringBuilder.append('_');
                }
            }
            stringBuilder.append("|\n");
        }
        return stringBuilder.toString();
    }
}
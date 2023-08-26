package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class Controller {
    @FXML
    private Canvas canvas;
    @FXML
    private Button btnUp;
    @FXML
    private Button btnDown;
    @FXML
    private Button btnLeft;
    @FXML
    private Button btnRight;
    // Punkt gry będzie kwadratem o szerokości 20 pikseli.
    private final static int POINT_SIZE = 20;
    private GraphicsContext graphicsContext;
    private SnakeGame snakeGame;

    public void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();

        // Tworzymy grę w węża:
        Point head = new Point(3, 3);
        // Rozmiar planszy ustalimy bazując na szerokości płótna i rozmiarze punktu w pikselach.
        int xBound = (int) (canvas.getWidth() / POINT_SIZE);
        int yBound = (int) (canvas.getHeight() / POINT_SIZE);
        snakeGame = new SnakeGame(xBound, yBound);

        // Ustawimy, w jaki sposób będziemy przedstawiali sytuację z gry:
        snakeGame.setDisplayBoardStrategy(() -> Platform.runLater(() -> {
            // Ustawimy białe tło gry
            graphicsContext.setFill(Color.WHITE);
            // Zamalujemy całe płótno na biało.
            graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            drawPoint(snakeGame.getApple(), Color.RED);

            Snake snake = snakeGame.getSnake();
            drawPoint(snake.getHead(), Color.BLACK);
            snake.getBody()
                    .forEach(point -> drawPoint(point, Color.GREEN));
        }));
        // Ustawimy funkcjonalność przycisków ze strzałkami.
        setDirectionButtonsClickHandlers();

        Thread thread = new Thread(snakeGame::start);
        // Aplikacja nie będzie czekała na koniec pracy wątku, aby zakończyć działanie.
        thread.setDaemon(true);
        thread.start();

    }

    private void setDirectionButtonsClickHandlers() {
        btnDown.setOnAction(event -> snakeGame.setSnakeDirection(Direction.DOWN));
        btnUp.setOnAction(event -> snakeGame.setSnakeDirection(Direction.UP));
        btnLeft.setOnAction(event -> snakeGame.setSnakeDirection(Direction.LEFT));
        btnRight.setOnAction(event -> snakeGame.setSnakeDirection(Direction.RIGHT));
    }


    private void drawPoint(Point point, Color color) {
        graphicsContext.setFill(color);
        graphicsContext.fillRect(point.getX() * POINT_SIZE, point.getY() * POINT_SIZE, POINT_SIZE, POINT_SIZE);
    }

}
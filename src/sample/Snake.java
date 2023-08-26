package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentuje węża.
 */
public class Snake {
    /**
     * Głowa węża.
     */
    private Point head;
    /**
     * Ciało węża - pozostałe punkty (pierwszy element listy to pierwszy punkt ciała po głowie, ostatni to ogon).
     */
    private List<Point> body;
    /**
     * Kierunek ruchu węża.
     */
    private Direction direction;

    /**
     * Tworzy węża dla zadanej głowy, ciała oraz kierunku.
     * @param head
     * @param body
     * @param direction
     */
    public Snake(Point head, List<Point> body, Direction direction) {
        this.head = head;
        this.body = new ArrayList<> (body);
        this.direction = direction;
    }

    /**
     * Tworzy węża o zadanej głowie. Wąż będzie poruszał się w prawo i miał puste ciało.
     * @param head
     */
    public Snake(Point head) {
        this.head = head;
        this.body = new ArrayList<>();
        this.direction = Direction.RIGHT;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Point getHead() {
        return head;
    }

    public List<Point> getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Snake{" +
                "head=" + head +
                ", body=" + body +
                '}';
    }

    /**
     * Porusza węża zgodnie z jego kierunkiem (nieużywana w aktualnej implementacji klasy SnakeGame).
     */
    public void move() {
        expand();
        cutTail();
    }

    /**
     * Metoda rozszerza węża w kierunku jego ruchu.
     */
    public void expand() {
        // głowa trafia do ciała
        body.add(0, head);
        // usuwamy ostatni punkt ciała
        // głowa stanie się wynikiem przesunięcia siebie w kierunku direction
        head = head.moveTo(direction);
    }

    /**
     * Ucina ogon węża.
     */
    public void cutTail() {
        body.remove(body.size() - 1);
    }

    /**
     * Sprawdza, czy wąż zawiera zadany punkt (używana dla sprawdzenia, czy wąż zawiera jabłko).
     * @param point
     * @return
     */
    public boolean contains(Point point) {
        return head.equals(point) || body.contains(point);
    }
}
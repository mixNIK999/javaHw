package me.nikolyukin.GameObject;

import static me.nikolyukin.Utility.checkAllCollision;
import static me.nikolyukin.Utility.moveX;
import static me.nikolyukin.Utility.moveY;

import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Bullet {
    private Point2D speedVector;
    private final double g;
    private double power;

    private Circle sprite;

    public Bullet(Circle sprite, Point2D speedVector, double power) {
        this(sprite, speedVector, 10, power);
    }

    public Bullet(Circle sprite, Point2D speedVector, double g, double power) {
        speedVector = speedVector;
        this.g = g;
        this.power = power;
        this.sprite = sprite;
    }

    public boolean fly(List<Shape> barriers) {
        moveX(sprite, speedVector.getX());
        moveY(sprite, speedVector.getY());
        speedVector = speedVector.add(0, g);
        return checkAllCollision(sprite, barriers);
    }

    public Explosion boom() {
        return new Explosion(new Circle(power, Color.DARKORANGE));
    }

    public Bullet(Bullet other) {
        this.speedVector = other.speedVector;
        this.g = other.g;
        this.power = other.power;
        this.sprite = other.sprite;
    }

    public void setSpeedVector(Point2D speedVector) {
        this.speedVector = speedVector;
    }

    public Circle getSprite() {
        return sprite;
    }
}

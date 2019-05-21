package me.nikolyukin.GameObject;

import static me.nikolyukin.Utility.checkAllCollision;
import static me.nikolyukin.Utility.moveX;
import static me.nikolyukin.Utility.moveY;

import java.util.List;
import javafx.scene.shape.Shape;

public class Bullet {
    private double speedX;
    private double speedY;
    private final double g;
    private double power;

    private Shape sprite;

    public Bullet(Shape sprite, double speedX, double speedY, double g, double power) {
        this.speedX = speedX;
        this.speedY = speedY;
        this.g = g;
        this.power = power;
        this.sprite = sprite;
    }

    public boolean fly(List<Shape> barriers) {
        moveX(sprite, speedX);
        moveY(sprite, speedY);
        speedY += g;
        return checkAllCollision(sprite, barriers);
    }

//    public Explosion boom() {
//
//    }
}

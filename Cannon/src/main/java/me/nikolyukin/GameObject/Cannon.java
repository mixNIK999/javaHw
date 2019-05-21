package me.nikolyukin.GameObject;

import static java.lang.Math.abs;
import static me.nikolyukin.Utility.checkAllCollision;
import static me.nikolyukin.Utility.checkCollision;
import static me.nikolyukin.Utility.moveX;
import static me.nikolyukin.Utility.moveY;
import static me.nikolyukin.Utility.rotateNode;

import java.util.List;
import javafx.scene.Group;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Cannon {
    private Group sprite;

    private Circle wheel;
    private Arc gun;

    public Cannon(Group sprite) {
        this.sprite = sprite;
        wheel = (Circle) sprite.lookup("#wheel");
        gun = (Arc) sprite.lookup("#gun");
    }

    public void goTo(int dist, List<Shape> mounts) {
        boolean goRight = dist > 0;
        int step = ((goRight)? 1 : -1);
        for (int i = 0; i < abs(dist); i++) {
            for (var mount : mounts) {
                while (checkCollision(wheel, mount)) {
                    moveY(sprite,-1);
                }
            }
            moveX(sprite, step);
        }
    }

    public void gravity(List<Shape> mounts) {
        while (!checkAllCollision(wheel, mounts) && sprite.getTranslateY() < 0) {
            moveY(sprite,1);
        }
    }

    public void rotate(double angle) {
        rotateNode(sprite, angle);
    }
}

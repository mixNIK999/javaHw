package me.nikolyukin;

import static java.lang.Math.abs;

import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;
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

    public boolean goTo(int dist, List<Node> mounts) {
        boolean goRight = dist > 0;
        int step = ((goRight)? 1 : -1);
        for (int i = 0; i < abs(dist); i++) {
            for (var mount : mounts) {
                while (checkCollision(wheel, (Shape) mount)) {
//                    moveX(-step);
                    moveY(-1);

//                    return false;
                }
            }
            moveX(step);
        }
        return true;
    }

    public boolean gravity() {
        return false;
    }
    private void moveX(int dist) {
        sprite.setTranslateX(sprite.getTranslateX() + dist);
    }

    private void moveY(int dist) {
        sprite.setTranslateY(sprite.getTranslateY() + dist);
    }

    private static boolean checkCollision(Shape a, Shape b) {
        return Shape.intersect(a, b).getBoundsInLocal().getWidth() != -1;
    }
}

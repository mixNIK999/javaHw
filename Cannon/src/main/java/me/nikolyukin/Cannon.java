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
    private final double bottom;

    public Cannon(Group sprite, double bottom) {
        this.bottom = bottom;
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
                    moveY(-1);
                }
            }
            moveX(step);
        }
    }

    public void gravity(List<Shape> mounts) {
        while (!checkAllCollision(wheel, mounts) && sprite.getTranslateY() < bottom) {
            moveY(1);
        }
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

    private static boolean checkAllCollision(Shape a, List<Shape> shapes) {
        return shapes.stream().anyMatch(e -> checkCollision(a, e));
    }
}

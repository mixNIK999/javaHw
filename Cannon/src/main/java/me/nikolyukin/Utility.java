package me.nikolyukin;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

public class Utility {
    public static void rotateNode(Node sprite, double angle) {
        sprite.getTransforms().add(new Rotate(angle));
    }

    public static void moveX(Node sprite, double dist) {
        sprite.setTranslateX(sprite.getTranslateX() + dist);
    }

    public static void moveY(Node sprite, double dist) {
        sprite.setTranslateY(sprite.getTranslateY() + dist);
    }


    public static boolean checkCollision(Shape a, Shape b) {
        return Shape.intersect(a, b).getBoundsInLocal().getWidth() != -1;
    }

    public static boolean checkAllCollision(Shape a, List<Shape> shapes) {
        return shapes.stream().anyMatch(e -> checkCollision(a, e));
    }
}

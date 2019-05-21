package me.nikolyukin;

import static java.lang.Math.abs;

import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;

public class Cannon {
    private Group sprite;

    private Circle wheel;
    private Arc gun;

    public Cannon(Group sprite) {
        this.sprite = sprite;
        wheel = (Circle) sprite.lookup("#wheel");
        gun = (Arc) sprite.lookup("#gun");
    }

    public boolean go(int dist, List<Node> mounts) {
        boolean goRight = dist > 0;
        for (int i = 0; i < abs(dist); i++) {
            for (var mount : mounts) {
                if (wheel.getBoundsInParent().intersects(mount.getBoundsInParent())) {
                    return false;
                }
            }
            sprite.setTranslateX(sprite.getTranslateX() + ((goRight)? 1 : -1));
        }
        return true;
    }
}

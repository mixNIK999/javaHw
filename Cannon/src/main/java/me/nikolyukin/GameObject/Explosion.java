package me.nikolyukin.GameObject;

import static me.nikolyukin.Utility.checkCollision;

import java.util.List;
import javafx.scene.shape.Shape;

public class Explosion {
    private Shape sprite;

    public Explosion(Shape sprite) {
        this.sprite = sprite;
    }

    void hit(List<Sensitive> targets) {
        for (var target : targets) {
            if (checkCollision(sprite, target.getHitBox())) {
                target.onHit();
            }
        }
    }
}

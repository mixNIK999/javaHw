package me.nikolyukin.GameObject;

import static java.lang.Double.max;
import static me.nikolyukin.Utility.checkCollision;

import java.util.List;
import javafx.scene.shape.Shape;

public class Explosion {
    private Shape sprite;

    private final static double fadingSpeed = 0.05;

    public Explosion(Shape sprite) {
        this.sprite = sprite;
    }

    public void hit(List<Sensitive> targets) {
        for (var target : targets) {
            if (checkCollision(sprite, target.getHitBox())) {
                target.onHit();
            }
        }
    }

    public Shape getSprite() {
        return sprite;
    }

    public boolean fading() {
        double opacity = sprite.getOpacity();
        if (opacity <= 0) {
            return false;
        }
        opacity = max(opacity - fadingSpeed, 0);
        sprite.setOpacity(opacity);
        return true;
    }
}

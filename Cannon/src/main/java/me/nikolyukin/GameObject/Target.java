package me.nikolyukin.GameObject;

import javafx.scene.shape.Shape;

public class Target implements Sensitive {
    private Shape sprite;
    private boolean alive;

    public Target(Shape sprite) {
        this.sprite = sprite;
        alive = true;
    }

    @Override
    public void onHit() {
        alive = false;
    }

    @Override
    public Shape getHitBox() {
        return sprite;
    }

    public boolean isAlive() {
        return alive;
    }
}

package me.nikolyukin.GameObject;


import javafx.scene.shape.Shape;

public interface Sensitive {
    void onHit();

    Shape getHitBox();

    boolean isAlive();
}

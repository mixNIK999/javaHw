package me.nikolyukin;

import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.shape.Shape;

public class Controller {

    private Cannon cannon;
    private List<Shape> mounts;

    private AnimationTimer timer;
    private boolean isPressedUp, isPressedDown, isPressedRight, isPressedLeft;

    private final int speed = 3;

    public Controller(Group cannonSprite, List<Shape> mounts, double height) {
        this.cannon = new Cannon(cannonSprite, height);
        this.mounts = mounts;

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
//                cannon.setTranslateY(cannon.getTranslateY() - 1);
                if (isPressedRight) {
                    cannon.goTo(speed, mounts);
                }
                if (isPressedLeft) {
                    cannon.goTo(-speed, mounts);
                }

                cannon.gravity(mounts);
            }
        };
    }

    public AnimationTimer getTimer() {
        return timer;
    }

    public void setPressedUp(boolean pressedUp) {
        this.isPressedUp = pressedUp;
    }

    public void setPressedDown(boolean pressedDown) {
        this.isPressedDown = pressedDown;
    }

    public void setPressedRight(boolean pressedRight) {
        this.isPressedRight = pressedRight;
    }

    public void setPressedLeft(boolean pressedLeft) {
        this.isPressedLeft = pressedLeft;
    }
}

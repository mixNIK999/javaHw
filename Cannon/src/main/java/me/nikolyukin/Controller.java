package me.nikolyukin;


import static java.lang.Math.round;

import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.shape.Shape;

public class Controller {

    private Cannon cannon;
    private List<Shape> mounts;

    private AnimationTimer timer;
    private boolean isPressedUp, isPressedDown, isPressedRight, isPressedLeft;

    private final int speed;
    private final int speedCoefficient = 300;
    private final double width;
    private final double height;
    private final double rotateSpeed = 5;

    public Controller(Group cannonSprite, List<Shape> mounts, double height, double width) {
        this.cannon = new Cannon(cannonSprite);
        this.mounts = mounts;
        this.width = width;
        this.height = height;

        speed = Integer.max(1, (int) round(width / speedCoefficient));

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isPressedRight) {
                    cannon.goTo(speed, mounts);
                }
                if (isPressedLeft) {
                    cannon.goTo(-speed, mounts);
                }
                if (isPressedUp) {
                    cannon.rotate(-rotateSpeed);
                }
                if (isPressedDown) {
                    cannon.rotate(rotateSpeed);
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

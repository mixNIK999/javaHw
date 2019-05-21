package me.nikolyukin;

import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;

public class Controller {

    private Cannon cannon;
    private List<Node> mounts;

    private AnimationTimer timer;

    private boolean isPressedUp, isPressedDown, isPressedRight, isPressedLeft;

    public Controller(Group cannonSprite, List<Node> mounts) {
        this.cannon = new Cannon(cannonSprite);
        this.mounts = mounts;

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
//                cannon.setTranslateY(cannon.getTranslateY() - 1);
                if (isPressedRight) {
                    cannon.go(5, mounts);
                }
                if (isPressedLeft) {
                    cannon.go(-5, mounts);
                }
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

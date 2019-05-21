package me.nikolyukin;


import static java.lang.Math.round;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import me.nikolyukin.GameObject.Bullet;
import me.nikolyukin.GameObject.Cannon;
import me.nikolyukin.GameObject.Sensitive;
import me.nikolyukin.GameObject.Target;

public class Controller {

    private Cannon cannon;
    private List<Shape> mounts;

    private AnimationTimer timer;
    private boolean rotateClockwise, rotateCounterclockwise, goRight, goLeft;
    private boolean readyForSoot, changeAmmo;

    private final static int speedCoefficient = 300;
    private final static double rotateSpeed = 2;

    private final int speed;
    private final double width;
    private final double height;
    private List<Bullet> bullets = new ArrayList<>();
    private final List<Shape> barriers = new ArrayList<>();
    private final List<Sensitive> targets = new ArrayList<>();


    public Controller(Pane root, Group cannonSprite, List<Shape> mounts, Shape targetSprite, double height, double width) {
        this.cannon = new Cannon(cannonSprite);
        this.mounts = mounts;
        this.width = width;
        this.height = height;

        barriers.addAll(mounts);
        barriers.add(targetSprite);

        targets.add(new Target(targetSprite));

        speed = Integer.max(1, (int) round(width / speedCoefficient));

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (goRight) {
                    cannon.goTo(speed, mounts);
                }
                if (goLeft) {
                    cannon.goTo(-speed, mounts);
                }
                if (rotateClockwise) {
                    cannon.rotate(-rotateSpeed);
                }
                if (rotateCounterclockwise) {
                    cannon.rotate(rotateSpeed);
                }
                if(changeAmmo) {
                    cannon.nextAmmo();
                    changeAmmo = false;
                }
                if (readyForSoot) {
                    Bullet newBullet = cannon.shoot();
                    root.getChildren().add(newBullet.getSprite());
                    newBullet.getSprite().toBack();
                    bullets.add(newBullet);
                    readyForSoot = false;
                }

                var flyingBullets = new ArrayList<Bullet>();
                for (var bullet : bullets) {
                    if (bullet.fly(barriers)) {
                        flyingBullets.add(bullet);
                    } else {
                        root.getChildren().remove(bullet.getSprite());
                    }
                }
                bullets = flyingBullets;

                cannon.gravity(mounts);
            }
        };
    }

    public AnimationTimer getTimer() {
        return timer;
    }

    public void setRotateClockwise(boolean rotateClockwise) {
        this.rotateClockwise = rotateClockwise;
    }

    public void setRotateCounterclockwise(boolean rotateCounterclockwise) {
        this.rotateCounterclockwise = rotateCounterclockwise;
    }

    public void setGoRight(boolean goRight) {
        this.goRight = goRight;
    }

    public void setGoLeft(boolean goLeft) {
        this.goLeft = goLeft;
    }

    public void setReadyForSoot(boolean readyForSoot) {
        this.readyForSoot = readyForSoot;
    }

    public void setChangeAmmo(boolean changeAmmo) {
        this.changeAmmo = changeAmmo;
    }
}

package me.nikolyukin;


import static java.lang.Math.round;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import me.nikolyukin.GameObject.Bullet;
import me.nikolyukin.GameObject.Cannon;
import me.nikolyukin.GameObject.Explosion;
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
    private List<Sensitive> targets = new ArrayList<>();
    private List<Explosion> explosions = new ArrayList<>();


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
                cannon.gravity(mounts);

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
                        var explosion = bullet.boom();
                        explosion.hit(targets);
                        root.getChildren().add(explosion.getSprite());
                        explosions.add(explosion);

                    }
                }
                bullets = flyingBullets;

                var fadingExplosions = new ArrayList<Explosion>();
                for (var explosion : explosions) {
                    if (explosion.fading()) {
                        fadingExplosions.add(explosion);
                    } else {
                        root.getChildren().remove(explosion.getSprite());
                    }
                }
                explosions = fadingExplosions;

                var aliveTargets = new ArrayList<Sensitive>();
                for (var target : targets) {
                    if (target.isAlive()) {
                        aliveTargets.add(target);
                    } else {
                        root.getChildren().remove(target.getHitBox());
                    }
                }
                targets = aliveTargets;

                if (targets.isEmpty()) {
//                    var pane = new BoarderPane();
//                    root.getChildren().add(new StackPane())
                    timer.stop();
                }
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

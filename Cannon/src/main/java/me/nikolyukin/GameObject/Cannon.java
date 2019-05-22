package me.nikolyukin.GameObject;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static me.nikolyukin.Utility.checkAllCollision;
import static me.nikolyukin.Utility.checkCollision;
import static me.nikolyukin.Utility.moveX;
import static me.nikolyukin.Utility.moveY;
import static me.nikolyukin.Utility.pointByLengthAndAngle;
import static me.nikolyukin.Utility.rotateNode;


import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Cannon {
    private Group sprite;

    private Circle wheel;
    private Arc gun;
    private double angle;

    private final static double[] bulletSpeed = new double[]{10, 7, 5};
    private final static double[] ammoRadius = new double[]{2, 3, 5};
    private final static double[] ammoPower = new double[]{2, 5, 10};

    private int ammoType = 0;

    public Cannon(Group sprite) {
        this.sprite = sprite;
        wheel = (Circle) sprite.lookup("#wheel");
        gun = (Arc) sprite.lookup("#gun");
    }

    public void goTo(int dist, List<Shape> mounts) {
        boolean goRight = dist > 0;
        int step = ((goRight)? 1 : -1);
        for (int i = 0; i < abs(dist); i++) {
            for (var mount : mounts) {
                while (checkCollision(wheel, mount)) {
                    moveY(sprite,-1);
                }
            }
            moveX(sprite, step);
        }
    }

    public void gravity(List<Shape> mounts) {
        while (!checkAllCollision(wheel, mounts) && sprite.getTranslateY() < 0) {
            moveY(sprite,1);
        }
    }

    public void nextAmmo() {
        ammoType = (ammoType == 2)? 0 : ammoType + 1;
    }


    public Bullet shoot() {
        var bulletSprite = new Circle(0, 0, ammoRadius[ammoType], Color.LIGHTGREY);
        bulletSprite.setStroke(Color.BLACK);
        var bullet = new Bullet(bulletSprite, Point2D.ZERO, ammoPower[ammoType]);
        bullet.setSpeedVector(pointByLengthAndAngle(bulletSpeed[ammoType], angle));
        bullet.getSprite().setTranslateX(sprite.getLayoutX() + sprite.getTranslateX());
        bullet.getSprite().setTranslateY(sprite.getLayoutY() + sprite.getTranslateY());
        return bullet;
    }


    public void rotate(double angle) {
        rotateNode(sprite, angle);
        this.angle += PI * angle/180;
    }
}

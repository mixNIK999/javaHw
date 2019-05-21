package me.nikolyukin;

import java.util.Objects;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CannonApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(
            Objects.requireNonNull(getClass().getClassLoader().getResource("main.fxml")));

        var scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);

        var cannon = (Group) scene.lookup("#cannon");
        var landscape = (Parent) scene.lookup("#landscape");
        var controller = new Controller(cannon, landscape.getChildrenUnmodifiable());

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    controller.setPressedLeft(true);
                    break;
                case RIGHT:
                    controller.setPressedRight(true);
                    break;
//                case LEFT:
//                    controller.setPressedLeft(true);
//                    break;


            }
        });

        controller.getTimer().start();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

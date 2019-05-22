package me.nikolyukin;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class CannonApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane root = FXMLLoader.load(
            Objects.requireNonNull(getClass().getClassLoader().getResource("main.fxml")));

        var scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);

        var cannonSprite = (Group) scene.lookup("#cannon");
        var landscape = (Parent) scene.lookup("#landscape");
        var targetSprite = (Circle) scene.lookup("#target");
        List<Shape> mounts = landscape.getChildrenUnmodifiable().stream().map(e -> (Shape) e).collect(Collectors.toList());

        var controller = new Controller(root, cannonSprite, mounts, targetSprite, root.prefHeight(0), root.prefWidth(0));

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    controller.setGoLeft(true);
                    break;
                case RIGHT:
                    controller.setGoRight(true);
                    break;
                case UP:
                    controller.setRotateClockwise(true);
                    break;
                case DOWN:
                    controller.setRotateCounterclockwise(true);
                    break;
                case ENTER:
                    controller.setReadyForSoot(true);
                    break;
                case SPACE:
                    controller.setChangeAmmo(true);
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                    controller.setGoLeft(false);
                    break;
                case RIGHT:
                    controller.setGoRight(false);
                    break;
                case UP:
                    controller.setRotateClockwise(false);
                    break;
                case DOWN:
                    controller.setRotateCounterclockwise(false);
                    break;
            }
        });

        controller.getTimer().start();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

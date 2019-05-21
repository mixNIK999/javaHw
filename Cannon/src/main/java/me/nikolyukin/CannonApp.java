package me.nikolyukin;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Shape;
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
        List<Shape> mounts = landscape.getChildrenUnmodifiable().stream().map(e -> (Shape) e).collect(Collectors.toList());

        var controller = new Controller(cannon, mounts, root.prefHeight(0), root.prefWidth(0));

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    controller.setPressedLeft(true);
                    break;
                case RIGHT:
                    controller.setPressedRight(true);
                    break;
                case UP:
                    controller.setPressedUp(true);
                    break;
                case DOWN:
                    controller.setPressedDown(true);
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                    controller.setPressedLeft(false);
                    break;
                case RIGHT:
                    controller.setPressedRight(false);
                    break;
                case UP:
                    controller.setPressedUp(false);
                    break;
                case DOWN:
                    controller.setPressedDown(false);
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

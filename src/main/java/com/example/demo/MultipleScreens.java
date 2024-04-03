package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class MultipleScreens extends Application {

    private final ScheduledScoreboardExecutor executor1;
    private final ScheduledScoreboardExecutor executor2;

    public MultipleScreens() {
        this.executor1 = new ScheduledScoreboardExecutor(new Stage());
        this.executor2 = new ScheduledScoreboardExecutor(new Stage());
    }

    @Override
    public void start(Stage primaryStage) {
        executor1.start();
        executor2.start();
    }

    protected static void showScreen(Stage primaryStage, List<List<DisplayVehicle>> jsonDataLists) {
        VBox root = new VBox(10);

        for (List<DisplayVehicle> dataList : jsonDataLists) {
            for (DisplayVehicle data : dataList) {
                Label info = new Label("Номер машины: " + " " + data.getVehicleNumber() + " Номер дока: " + data.getDockNumber());
                root.getChildren().addAll(info);
            }
        }

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
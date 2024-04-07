package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MultipleScreens.class.getResource("/MultipleScreensController.fxml"));
            Parent root = fxmlLoader.load();

            MultipleScreensController controller = fxmlLoader.getController();

            List<String> vehicleNumbers = new ArrayList<>();
            List<String> dockNumbers = new ArrayList<>();
            for (List<DisplayVehicle> dataList : jsonDataLists) {
                for (DisplayVehicle data : dataList) {
                    vehicleNumbers.add(data.getVehicleNumber());
                    dockNumbers.add(data.getDockNumber());
                }
            }
            String[] vehicleNumbersArray = vehicleNumbers.toArray(new String[0]);
            String[] dockNumbersArray = dockNumbers.toArray(new String[0]);

            controller.setVehicleNumber1(vehicleNumbersArray[0]);
            controller.setVehicleNumber2(vehicleNumbersArray[1]);
            controller.setVehicleNumber3(vehicleNumbersArray[2]);

            controller.setDock1(dockNumbersArray[0]);
            controller.setDock2(dockNumbersArray[1]);
            controller.setDock3(dockNumbersArray[2]);

            primaryStage.setScene(new Scene(root, 500, 400));
            primaryStage.show();

            primaryStage.setOnCloseRequest(event -> {
                System.exit('0');
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
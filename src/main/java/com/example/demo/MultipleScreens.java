package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MultipleScreens extends Application {

    private final ScheduledScoreboardExecutor executor1;
    private final ScheduledScoreboardExecutor executor2;
    private final ScheduledScoreboardExecutor executor3;


    public MultipleScreens() {
        this.executor1 = new ScheduledScoreboardExecutor(new Stage());
        this.executor2 = new ScheduledScoreboardExecutor(new Stage());
        this.executor3 = new ScheduledScoreboardExecutor(new Stage());
    }

    @Override
    public void start(Stage primaryStage) {
        executor1.startScreen1();
        executor2.startScreen2();
        executor3.startScreen3();
    }

    protected static void showScreen1(Stage primaryStage, List<List<DisplayVehicle>> jsonDataLists) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MultipleScreens.class.getResource("/Screen1.fxml"));
            Parent root = fxmlLoader.load();

            Screen1Controller controller = fxmlLoader.getController();

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

            controller.setBlinkingLabel(vehicleNumbersArray[0]);
            controller.setVehicleNumber2(vehicleNumbersArray[1]);
            controller.setVehicleNumber3(vehicleNumbersArray[2]);

            controller.setDock2(dockNumbersArray[1]);
            controller.setDock3(dockNumbersArray[2]);

            primaryStage.setScene(new Scene(root, 500, 400));
            primaryStage.setResizable(false);
            primaryStage.show();

            primaryStage.setOnCloseRequest(event -> System.exit('0'));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void showScreen2(Stage primaryStage, List<List<DisplayVehicle>> jsonDataLists) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MultipleScreens.class.getResource("/Screen2.fxml"));
            Parent root = fxmlLoader.load();

            Screen2Controller controller = fxmlLoader.getController();

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
            primaryStage.setResizable(false);
            primaryStage.show();

            primaryStage.setOnCloseRequest(event -> System.exit('0'));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void showScreen3(Stage primaryStage, List<List<DisplayVehicle>> jsonDataLists) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MultipleScreens.class.getResource("/Screen3.fxml"));
            Parent root = fxmlLoader.load();

            Screen3Controller controller = fxmlLoader.getController();

            List<String> vehicleNumbers = new ArrayList<>();
            for (List<DisplayVehicle> dataList : jsonDataLists) {
                for (DisplayVehicle data : dataList) {
                    vehicleNumbers.add(data.getVehicleNumber());
                }
            }
            String[] vehicleNumbersArray = vehicleNumbers.toArray(new String[0]);

            controller.setWaiting(vehicleNumbersArray[0]);
            controller.setVehicleNumber1(vehicleNumbersArray[1]);
            controller.setVehicleNumber2(vehicleNumbersArray[2]);
            controller.setVehicleNumber3(vehicleNumbersArray[3]);
            controller.setVehicleNumber4(vehicleNumbersArray[4]);
            controller.setVehicleNumber5(vehicleNumbersArray[5]);
            controller.setVehicleNumber6(vehicleNumbersArray[6]);

            primaryStage.setScene(new Scene(root, 500, 400));
            primaryStage.setResizable(false);
            primaryStage.show();

            primaryStage.setOnCloseRequest(event -> System.exit('0'));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Screen2Controller {

    @FXML
    private Label VehicleNumber1;
    @FXML
    private Label VehicleNumber2;
    @FXML
    private Label VehicleNumber3;
    @FXML
    private Label Dock1;
    @FXML
    private Label Dock2;
    @FXML
    private Label Dock3;

    public void setVehicleNumber1(String vehicleNumber) {
        VehicleNumber1.setText(vehicleNumber);
    }

    public void setVehicleNumber2(String vehicleNumber) {
        VehicleNumber2.setText(vehicleNumber);
    }

    public void setVehicleNumber3(String vehicleNumber) {
        VehicleNumber3.setText(vehicleNumber);
    }

    public void setDock1(String dockNumber) {
        Dock1.setText(dockNumber);
    }

    public void setDock2(String dockNumber) {
        Dock2.setText(dockNumber);
    }

    public void setDock3(String dockNumber) {
        Dock3.setText(dockNumber);
    }

}
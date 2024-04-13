package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Screen3Controller {

    @FXML
    private Label waiting;
    @FXML
    private Label vehicle1;
    @FXML
    private Label vehicle2;
    @FXML
    private Label vehicle3;
    @FXML
    private Label vehicle4;
    @FXML
    private Label vehicle5;
    @FXML
    private Label vehicle6;

    public void setWaiting(String vehicleNumber) {
        waiting.setText(vehicleNumber);
    }

    public void setVehicleNumber1(String vehicleNumber) {
        vehicle1.setText(vehicleNumber);
    }

    public void setVehicleNumber2(String vehicleNumber) {
        vehicle2.setText(vehicleNumber);
    }

    public void setVehicleNumber3(String vehicleNumber) {
        vehicle3.setText(vehicleNumber);
    }

    public void setVehicleNumber4(String vehicleNumber) {
        vehicle4.setText(vehicleNumber);
    }

    public void setVehicleNumber5(String vehicleNumber) {
        vehicle5.setText(vehicleNumber);
    }

    public void setVehicleNumber6(String vehicleNumber) {
        vehicle6.setText(vehicleNumber);
    }
}

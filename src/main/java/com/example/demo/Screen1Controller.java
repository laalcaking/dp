package com.example.demo;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Screen1Controller {

    @FXML
    public Label blinkingLabel;
    @FXML
    public Label VehicleNumber3;
    @FXML
    public Label VehicleNumber2;
    @FXML
    private Label Dock2;
    @FXML
    private Label Dock3;

    @FXML
    public void initialize() {
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;
            private boolean visible = true;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 500_000_000L) { // Интервал 500 мс
                    blinkingLabel.setVisible(visible);
                    visible = !visible;
                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }

    public void setBlinkingLabel(String vehicleNumber) { blinkingLabel.setText(vehicleNumber); }

    public void setVehicleNumber2(String vehicleNumber) {
        VehicleNumber2.setText(vehicleNumber);
    }

    public void setVehicleNumber3(String vehicleNumber) {
        VehicleNumber3.setText(vehicleNumber);
    }

    public void setDock2(String dockNumber) {
        Dock2.setText(dockNumber);
    }

    public void setDock3(String dockNumber) {
        Dock3.setText(dockNumber);
    }
}

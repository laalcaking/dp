package com.example.demo;

import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;

import javafx.stage.Stage;
import lombok.Getter;
import lombok.SneakyThrows;

public class ScheduledScoreboardExecutor {

    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    final long UPDATE_TIME = 5 * 1_000;//millis
    final byte MAX_SCREEN_LINES_BASE = 6;
    public DisplayScreen nextDisplayScreen = DisplayScreen.FIRST;

    LinkedList<DisplayVehicle> queueVehicles = new LinkedList<>();

    private List<DisplayVehicle> currentDisplayList;


    void startScreen1() {
        executor.scheduleAtFixedRate(divideJsonForScreen1, 0, UPDATE_TIME, TimeUnit.MILLISECONDS);
    }

    void startScreen2() {
        executor.scheduleAtFixedRate(divideJsonForScreen2, 0, UPDATE_TIME, TimeUnit.MILLISECONDS);
    }

    void startScreen3() { executor.scheduleAtFixedRate(divideJsonForScreen3, 0, UPDATE_TIME, TimeUnit.MILLISECONDS); }

    void startScreen4() { executor.scheduleAtFixedRate(divideJsonForScreen4, 0, UPDATE_TIME, TimeUnit.MILLISECONDS); }

    @Getter
    private List<List<DisplayVehicle>> jsonData;

    private final Stage primaryStage;

    public ScheduledScoreboardExecutor (Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    private final Runnable divideJsonForScreen1 = new Runnable() {
        @SneakyThrows
        @Override
        public void run() {
            currentDisplayList = generateData();
            jsonData = divideData(DisplayScreen.FIRST, currentDisplayList);
            Platform.runLater(() -> MultipleScreens.showScreen1(primaryStage, jsonData));
        }
    };


    private final Runnable divideJsonForScreen2 = new Runnable() {
        @SneakyThrows
        @Override
        public void run() {
            currentDisplayList = generateData();
            jsonData = divideData(DisplayScreen.SECOND, currentDisplayList);
            Platform.runLater(() -> MultipleScreens.showScreen2(primaryStage, jsonData));
        }
    };

    private final Runnable divideJsonForScreen3 = new Runnable() {
        @SneakyThrows
        @Override
        public void run() {
            currentDisplayList = generateData();
            jsonData = divideData(DisplayScreen.THIRD, currentDisplayList);
            Platform.runLater(() -> MultipleScreens.showScreen3(primaryStage, jsonData));
        }
    };

    private final Runnable divideJsonForScreen4 = new Runnable() {
        @SneakyThrows
        @Override
        public void run() {
            currentDisplayList = generateData();
            jsonData = divideData(DisplayScreen.FOURTH, currentDisplayList);
            Platform.runLater(() -> MultipleScreens.showScreen4(primaryStage, jsonData));
        }
    };

    protected List<DisplayVehicle> generateData() throws Exception {

        currentDisplayList = new LinkedList<>();

        Random random = new Random();

        currentDisplayList.add(0, new DisplayVehicle("Вызов на приемку", "0", "0"));

        // Генерация 6 строк JSON
        for (int i = 2; i <= MAX_SCREEN_LINES_BASE; i++) {
            int randomNumber = random.nextInt(1, 44);
            String formattedNumber = DisplayVehicle.generateFormattedNumber();
            DisplayVehicle dv = new DisplayVehicle(formattedNumber, String.valueOf(randomNumber), String.valueOf(i));
            currentDisplayList.add(dv);
        }

        currentDisplayList.add( new DisplayVehicle( "Ожидайте вызов", "0", "0"));

        // Генерация 10 строк JSON
        for (int i = 0; i <= 9; i++) {
            int randomNumber = random.nextInt(1, 44);
            String formattedNumber = DisplayVehicle.generateFormattedNumber();
            DisplayVehicle dv = new DisplayVehicle(formattedNumber, String.valueOf(randomNumber), String.valueOf(i));
            currentDisplayList.add(dv);
        }

        return currentDisplayList;
    }

    protected List<List<DisplayVehicle>> divideData(DisplayScreen screen, List<DisplayVehicle> currentDisplayList) throws Exception {
        removeExpiredVehicles();

        List<List<DisplayVehicle>> dividedLists = new ArrayList<>();
        int endIndex;

        switch (screen) {
            case FIRST:
                endIndex = Math.min(3, currentDisplayList.size());
                dividedLists.add(currentDisplayList.subList(0, endIndex));
                break;
            case SECOND:
                endIndex = Math.min(6, currentDisplayList.size());
                dividedLists.add(currentDisplayList.subList(3, endIndex));
                break;
            case THIRD:
                endIndex = Math.min(11, currentDisplayList.size());
                dividedLists.add(currentDisplayList.subList(6, endIndex));
                break;
            case FOURTH:
                endIndex = Math.min(17, currentDisplayList.size());
                dividedLists.add(currentDisplayList.subList(11, endIndex));
                break;
            default:
                throw new Exception("Display screen not found");
        }

        return dividedLists;
    }


    private void removeExpiredVehicles() {
        for (int i = queueVehicles.size() - 1; i >= 0; i--) {
            if (queueVehicles.get(i).isExpired()) {
                queueVehicles.remove(i);
            }
        }
    }

    private boolean isNeedNextScreen() {
        return queueVehicles.size() >= MAX_SCREEN_LINES_BASE;
    }

}
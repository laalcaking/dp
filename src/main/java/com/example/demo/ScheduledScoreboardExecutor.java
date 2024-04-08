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
    public DisplayScreen currentDisplayScreen = DisplayScreen.FIRST;
    public DisplayScreen nextDisplayScreen = DisplayScreen.FIRST;

    LinkedList<DisplayVehicle> queueVehicles = new LinkedList<>();

    void start() {
        executor.scheduleAtFixedRate(generateJson, 0, UPDATE_TIME, TimeUnit.MILLISECONDS);
    }

    @Getter
    private List<List<DisplayVehicle>> jsonData;

    private final Stage primaryStage;

    public ScheduledScoreboardExecutor (Stage primaryStage){
        this.primaryStage = primaryStage;
    }


    private final Runnable generateJson = new Runnable() {
        @SneakyThrows
        @Override
        public void run() {
            jsonData = generateData();
            Platform.runLater(() -> {
                // Обновление сцены с новыми данными
                MultipleScreens.showScreen(primaryStage, jsonData);
            });
        }
    };

    private List<List<DisplayVehicle>> generateData() throws Exception {
        currentDisplayScreen = nextDisplayScreen;
        removeExpiredVehicles();

        LinkedList<DisplayVehicle> currentDisplayList = new LinkedList<>();

        Random random = new Random();

        // Генерация 6 строк JSON
        for (int i = 1; i <= MAX_SCREEN_LINES_BASE; i++) {
            int randomNumber = random.nextInt(1, 44);
            String formattedNumber = DisplayVehicle.generateFormattedNumber();
            DisplayVehicle dv = new DisplayVehicle(formattedNumber, String.valueOf(randomNumber), String.valueOf(i));
            currentDisplayList.add(dv);
        }

        List<List<DisplayVehicle>> dividedLists = new ArrayList<>();
        switch (currentDisplayScreen) {
            case FIRST:
                // Добавляем в список первые три элемента списка
                dividedLists.add(currentDisplayList.subList(0, 3));
                if (isNeedNextScreen()) {
                    nextDisplayScreen = DisplayScreen.SECOND;
                }
                break;
            case SECOND:
                // Добавляем в список следующие три элемента списка, начиная с индекса 3
                dividedLists.add(currentDisplayList.subList(3, 6));
                nextDisplayScreen = DisplayScreen.FIRST;
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
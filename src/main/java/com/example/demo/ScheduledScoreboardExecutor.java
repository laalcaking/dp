package com.example.demo;

import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;

import javafx.stage.Stage;
import lombok.Getter;
import lombok.SneakyThrows;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ScheduledScoreboardExecutor {

    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    final long EXPIRE_TIME = 1 * 60;//seconds
    final long UPDATE_TIME = 5 * 1_000;//millis
    final byte MAX_SCREEN_LINES_BASE = 6;
    final byte MAX_SCREEN_LINES_DUPLICATE = 3;
    public DisplayScreen currentDisplayScreen = DisplayScreen.FIRST;
    public DisplayScreen nextDisplayScreen = DisplayScreen.FIRST;

    LinkedList<DisplayVehicle> queueVehicles = new LinkedList<>();

    void start() {
        executor.scheduleAtFixedRate(generateJson, 0, UPDATE_TIME, TimeUnit.MILLISECONDS);
    }

    @Getter
    private List<List<DisplayVehicle>> jsonData;


    private MultipleScreens multipleScreens;
    private Stage primaryStage;

    public ScheduledScoreboardExecutor (Stage primaryStage){
        this.multipleScreens = multipleScreens;
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
            int randomNumber = random.nextInt(44);
            Vehicle someVehicle = new Vehicle();
            String formattedNumber = DisplayVehicle.generateFormattedNumber(someVehicle.getRegNumber());
            DisplayVehicle dv = new DisplayVehicle(formattedNumber, String.valueOf(randomNumber), String.valueOf(i));
            currentDisplayList.add(dv);
        }

        List<List<DisplayVehicle>> dividedLists = new ArrayList<>();
        switch (currentDisplayScreen) {
            case FIRST:
                // Добавляем в список первые три элемента списка
                dividedLists.add(currentDisplayList.subList(0, 3));
                if (isNeedNextScreen(1)) {
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
            if (queueVehicles.get(i).isExpired(EXPIRE_TIME)) {
                queueVehicles.remove(i);
            }
        }
    }

    private boolean isNeedNextScreen(int currentScreenNum) {
        return queueVehicles.size() >= MAX_SCREEN_LINES_BASE * currentScreenNum;
    }

    private String sendJson(LinkedList<DisplayVehicle> potentialJson) throws JsonProcessingException {
        for (int i = 1; i <= MAX_SCREEN_LINES_BASE; i++) {
            potentialJson.add(new DisplayVehicle("Vehicle " + i, String.valueOf(i), String.valueOf(i)));
        }

        potentialJson.add(0, new DisplayVehicle("Вызов на приемку", "", "1"));

        while (potentialJson.size() < MAX_SCREEN_LINES_BASE) {
            potentialJson.add(new DisplayVehicle("", "", String.valueOf(potentialJson.size() + 1)));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder jsonBase = new StringBuilder();
        StringBuilder jsonDuplicate = new StringBuilder();

        for (int i = 0; i < MAX_SCREEN_LINES_BASE; i++) {
            jsonBase.append(objectMapper.writeValueAsString(potentialJson.get(i)));
        }

        for (int i = 0; i< MAX_SCREEN_LINES_DUPLICATE; i++){
            jsonDuplicate.append(objectMapper.writeValueAsString(potentialJson.get(i)));
        }

        String baseTableJson = jsonBase.toString();
        String duplicateTableJson = jsonDuplicate.toString();

        return "Base Table Screen " + currentDisplayScreen + " :\n" + baseTableJson + "\n" + "Duplicate Table:\n" + duplicateTableJson;

    }
}
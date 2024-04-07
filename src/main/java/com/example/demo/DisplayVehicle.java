package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisplayVehicle {

    @JsonProperty("text")
    private String vehicleNumber;
    @JsonProperty("number")
    private String dockNumber;
    @JsonProperty("line")
    private String displayPos;
    @JsonIgnore
    private final LocalDateTime creationDate = LocalDateTime.now();

    boolean isExpired(Long expirationTime) {
        return (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - creationDate.toEpochSecond(ZoneOffset.UTC)) > expirationTime;
    }

    public DisplayVehicle(Vehicle vehicle, String dockNumber, String displayPos) {
        this.vehicleNumber = generateFormattedNumber(vehicle.getRegNumber());
        this.dockNumber = dockNumber;
        this.displayPos = displayPos;
    }

    protected static String generateFormattedNumber(String regNumber) {
        Random random = new Random();

        char firstLetter = (char) ('A' + random.nextInt(26));
        char secondLetter = (char) ('A' + random.nextInt(26));

        String digits = "";
        for (int i = 0; i < 4; i++) {
            digits += random.nextInt(10);
        }

        int lastDigit = random.nextInt(1, 7);

        return String.format("%c%c%s-%d", firstLetter, secondLetter, digits, lastDigit);
    }
}

package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

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



}

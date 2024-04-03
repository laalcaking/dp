package com.example.demo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@NoArgsConstructor
public class Dock {
    private int number = new Random().nextInt(1000);

    private Vehicle vehicle = new Vehicle(String.valueOf(new Random().nextInt(1000)));
}

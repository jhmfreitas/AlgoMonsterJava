package com.algo.monster.ood;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Solution {

    public static Spot[] parkingSlots;
    public static Integer availableSpots;

    public enum CarSize {
        SMALL(0), MEDIUM(1), LARGE(2);

        CarSize(int size){
            this.size = size;
        }
        private int size;

        public int getSize() {
            return size;
        }

        @Override
        public String toString() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }

    public static class Car {
        private CarSize size;
        private String color;
        private String brand;

        public Car(CarSize size, String color, String brand) {
            this.size = size;
            this.color = color;
            this.brand = brand;
        }

        public CarSize getSize() {
            return size;
        }

        public String getColor() {
            return color;
        }

        public String getBrand() {
            return brand;
        }

        @Override
        public String toString() {
            return String.format("%s %s %s", this.size, this.color, this.brand);
        }
    }

    public static class Spot {
        private final CarSize size;
        private Car car;

        public Spot(CarSize size, Car car) {
            this.size = size;
            this.car = car;
        }

        public CarSize getSize() {
            return size;
        }

        public Car getCar() {
            return car;
        }

        public boolean isEmpty() {
            return getCar() == null;
        }

        public void setCar(Car car) {
            this.car = car;
        }

        public boolean canFit(Car car) {
            return car.getSize().getSize() <= getSize().getSize();
        }
    }


    public static List<String> parkingSystem(List<String> spots, List<List<String>> instructions) {
        int spotsSize = spots.size();
        parkingSlots = new Spot[spotsSize];
        for (int i = 0; i < spotsSize ; i++) {
            parkingSlots[i] = new Spot(CarSize.valueOf(spots.get(i).toUpperCase()), null);
        }
        availableSpots = spotsSize;
        List<String> output = new ArrayList<>();

        for (List<String> instruction : instructions) {
            String operation = instruction.get(0);
            switch (operation) {
                case "park" :
                    Car car = new Car(CarSize.valueOf(instruction.get(2).toUpperCase()), instruction.get(3), instruction.get(4));
                    park(Integer.parseInt(instruction.get(1)), car);
                    break;
                case "remove" :
                    remove(Integer.parseInt(instruction.get(1)));
                    break;
                case "print" :
                    output.add(print(Integer.parseInt(instruction.get(1))));
                    break;
                case "print_free_spots" :
                    output.add("" + availableSpots);
                    break;
                default:
                    System.out.println("Skipping missing operation:" + operation);
            }
        }

        return output;
    }

    private static String print(int i) {
        return !parkingSlots[i].isEmpty() ? parkingSlots[i].getCar().toString() : "Empty";
    }

    private static void remove(int spot) {
        parkingSlots[spot].setCar(null);
        availableSpots++;
    }

    private static void park(Integer spot, Car car) {
        if(parkingSlots[spot].isEmpty() && parkingSlots[spot].canFit(car)) {
            parkingSlots[spot].setCar(car);
            availableSpots--;
        } else {
            Integer nextSpot = spot;
            while (!(parkingSlots[nextSpot].isEmpty() && parkingSlots[nextSpot].canFit(car))) {
                nextSpot++;
                if (nextSpot == parkingSlots.length) {
                    nextSpot = 0;
                }

                if (nextSpot.equals(spot)) {
                    nextSpot = -1;
                    break;
                }
            }

            if (nextSpot != -1) {
                parkingSlots[nextSpot].setCar(car);
                availableSpots--;
            }
        }
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> spots = splitWords(scanner.nextLine());
        int instructionsLength = Integer.parseInt(scanner.nextLine());
        List<List<String>> instructions = new ArrayList<>();
        for (int i = 0; i < instructionsLength; i++) {
            instructions.add(splitWords(scanner.nextLine()));
        }
        scanner.close();
        List<String> res = parkingSystem(spots, instructions);
        for (String line : res) {
            System.out.println(line);
        }
    }
}
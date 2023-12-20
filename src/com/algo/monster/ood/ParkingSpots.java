package com.algo.monster.ood;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class ParkingSpots {

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

    public static abstract class ParkingSystem {
        public Integer availableSpots;

        public abstract String print(int i);

        public abstract void remove(int spot);

        public abstract void park(Integer spot, Car car);

        public abstract String print_free_spots();
    }

    public static class RegularParkingLot extends ParkingSystem {
        public Spot[] parkingSlots;

        public RegularParkingLot(List<String> spots) {
            int size = spots.size();
            parkingSlots = new Spot[size];
            for (int i = 0; i < size ; i++) {
                parkingSlots[i] = new Spot(CarSize.valueOf(spots.get(i).toUpperCase()), null);
            }
            availableSpots = size;
        }

        @Override
        public String print(int i) {
            return !parkingSlots[i].isEmpty() ? parkingSlots[i].getCar().toString() : "Empty";
        }

        @Override
        public void remove(int spot) {
            parkingSlots[spot].setCar(null);
            availableSpots++;
        }

        @Override
        public void park(Integer spot, Car car) {
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

        @Override
        public String print_free_spots() {
            return availableSpots.toString();
        }

    }

    public static class ParkingInterval {
        private Integer start;
        private Integer end;
        private Car car;

        public ParkingInterval(Integer start, Integer end, Car car) {
            this.start = start;
            this.end = end;
            this.car = car;
        }

        public Integer getStart() {
            return start;
        }

        public Integer getEnd() {
            return end;
        }

        public Car getCar() {
            return car;
        }
    }

    public static class UnboundedParkingLot extends ParkingSystem {
        public ParkingInterval[] parkingSlots;
        public Integer smallCarSize;
        public Integer mediumCarSize;
        public Integer largeCarSize;

        public UnboundedParkingLot(int n, int a, int b, int c) {
            parkingSlots = new ParkingInterval[n];
            this.availableSpots = n;
            this.smallCarSize = a;
            this.mediumCarSize = b;
            this.largeCarSize = c;
        }


        @Override
        public String print(int i) {
            return parkingSlots[i].getCar().toString();
        }

        @Override
        public void remove(int spot) {

        }

        @Override
        public void park(Integer spot, Car car) {

        }

        @Override
        public String print_free_spots() {
            return null;
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


    public static List<String> parkingSystem(String lotType, List<String> params, List<List<String>> instructions) {
        List<String> output = new ArrayList<>();
        ParkingSystem parkingSystem = lotType.equals("Regular") ? new RegularParkingLot(params): new UnboundedParkingLot(Integer.parseInt(params.get(0)), Integer.parseInt(params.get(1)), Integer.parseInt(params.get(2)), Integer.parseInt(params.get(3)));
        for (List<String> instruction : instructions) {
            String operation = instruction.get(0);
            switch (operation) {
                case "park" :
                    Car car = new Car(CarSize.valueOf(instruction.get(2).toUpperCase()), instruction.get(3), instruction.get(4));
                    parkingSystem.park(Integer.parseInt(instruction.get(1)), car);
                    break;
                case "remove" :
                    parkingSystem.remove(Integer.parseInt(instruction.get(1)));
                    break;
                case "print" :
                    output.add(parkingSystem.print(Integer.parseInt(instruction.get(1))));
                    break;
                case "print_free_spots" :
                    output.add(parkingSystem.print_free_spots());
                    break;
                default:
                    System.out.println("Skipping missing operation:" + operation);
            }
        }

        return output;
    }



    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String lotType = scanner.nextLine();
        List<String> params = splitWords(scanner.nextLine());
        int instructionsLength = Integer.parseInt(scanner.nextLine());
        List<List<String>> instructions = new ArrayList<>();
        for (int i = 0; i < instructionsLength; i++) {
            instructions.add(splitWords(scanner.nextLine()));
        }
        scanner.close();
        List<String> res = parkingSystem(lotType, params, instructions);
        for (String line : res) {
            System.out.println(line);
        }
    }
}
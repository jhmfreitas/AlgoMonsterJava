package com.algo.monster.ood;

import java.util.*;

class VendingMachine {

    public static abstract class Coin {

        private int value;

        public Coin(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    public static class Penny extends Coin{
        public Penny() {
            super(1);
        }

        @Override
        public String toString() {
            return "penny";
        }
    }
    public static class Nickel extends Coin {
        public Nickel() {
            super(5);
        }

        @Override
        public String toString() {
            return "nickel";
        }
    }
    public static class Dime extends Coin {
        public Dime() {
            super(10);
        }

        @Override
        public String toString() {
            return "dime";
        }
    }
    public static class Quarter extends Coin{
        public Quarter() {
            super(25);
        }

        @Override
        public String toString() {
            return "quarter";
        }
    }

    public static class Machine {

        public static Map<String, Product> products = new HashMap<>();
        public List<Coin> coins;

        public Machine() {
            List<Coin> coins = Arrays.asList(new Penny(),new Nickel(),new Dime(),new Quarter());
            coins.sort((Coin a, Coin b) -> Integer.compare(b.value, a.value));
            this.coins = coins;
        }

        public Integer balance = 0;
        public void createProduct(String name, Integer price) {
            if (!products.containsKey(name)) {
                products.put(name, new Product(name, price));
            }
        }
        public void printProducts(List<String> output) {
            products.values().stream().sorted(Comparator.comparingInt(Product::getPrice)).forEach(p -> output.add(p.toString()));
        }
        public void insertCoin(String coinName, List<String> output) {
            Coin coin = null;
            if (coinName.equals("quarter")) {
                coin = new Quarter();
            } else if (coinName.equals("dime")) {
                coin = new Dime();
            } else if (coinName.equals("nickel")) {
                coin = new Nickel();
            } else if (coinName.equals("penny")) {
                coin = new Penny();
            }

            if (coin != null) {
                balance += coin.getValue();
                output.add("accepted");
            } else {
                output.add("rejected");
            }
        }

        public void purchaseProduct(String productName, List<String> output) {
            String out = "false";
            if (products.containsKey(productName) && balance > 0) {
                Product product = products.get(productName);
                if(product.getQuantity() > 0 && balance - product.getPrice() >= 0) {
                    out = String.valueOf(balance >= product.getPrice());
                    product.removeOne();
                    balance = balance - product.getPrice();
                }
            }
            output.add(out);
        }

        public void cleanCurrentState(List<String> output) {
            int balance = this.balance;
            this.balance = 0;
            for (Coin coin: coins) {
                int n = balance / coin.getValue();
                if (n != 0) {
                    output.add(String.format("%d %s", n, coin));
                }
                balance = balance % coin.getValue();
            }
        }

        public void restockProduct(String productName, Integer newQuantity) {
            if (products.containsKey(productName)) {
                products.get(productName).addStock(newQuantity);
            }
        }
    }

    public static class Product {
        private String name;

        private Integer price;

        private Integer quantity;

        public Product(String name, Integer price) {
            this.name = name;
            this.price = price;
            this.quantity = 0;
        }

        public Integer getPrice() {
            return price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Product product = (Product) o;
            return Objects.equals(name, product.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        @Override
        public String toString() {
            return String.format("%s %d %d", name, price, quantity);
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void addStock(Integer quantity) {
            this.quantity += quantity;
        }

        public void removeOne() {
            this.quantity -= 1;
        }
    }
    public static List<String> vendingMachine(List<List<String>> instructions) {
        List<String> output = new ArrayList<>();
        Machine machine = new Machine();
        for (List<String> instruction : instructions) {
            String command = instruction.get(0);
            if (command.equals("new_product")) {
                machine.createProduct(instruction.get(1), Integer.valueOf(instruction.get(2)));
            }else if (command.equals("print_products")) {
                machine.printProducts(output);
            }else if (command.equals("insert_coin")) {
                machine.insertCoin(instruction.get(1), output);
            } else if (command.equals("purchase")) {
                machine.purchaseProduct(instruction.get(1), output);
            } else if (command.equals("checkout")) {
                machine.cleanCurrentState(output);
            } else if (command.equals("restock")) {
                machine.restockProduct(instruction.get(1), Integer.valueOf(instruction.get(2)));
            }
        }
        return output;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int instructionsLength = Integer.parseInt(scanner.nextLine());
        List<List<String>> instructions = new ArrayList<>();
        for (int i = 0; i < instructionsLength; i++) {
            instructions.add(splitWords(scanner.nextLine()));
        }
        scanner.close();
        List<String> res = vendingMachine(instructions);
        for (String line : res) {
            System.out.println(line);
        }
    }
}

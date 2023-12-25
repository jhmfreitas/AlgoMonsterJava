package com.algo.monster.ood;

import java.util.*;

class CallCenterProject {
    public static int NUMBER_OF_RANKS = 3;

    public static class CallInstance {
        private String number;
        private Employee talkingTo;

        private int rank;

        public CallInstance(String number) {
            this.number = number;
            this.talkingTo = null;
            this.rank = 0;
        }

        public boolean startCall(Employee target, List<String> output) {
            if (talkingTo == null && target.talkingTo == null) {
                if(target instanceof Respondent) {
                    ((Respondent) target).increaseRating();
                }
                talkingTo = target;
                target.talkingTo = this;
                output.add(String.format("Connecting %s to %s", this, target));
                return true;
            }
            return false;
        }

        public boolean endCall(List<String> output) {
            if (talkingTo != null) {
                output.add(String.format("Call between %s and %s ended", this, this.talkingTo));
                rank = talkingTo.rank();
                talkingTo.talkingTo = null;
                talkingTo = null;
                return true;
            }
            return false;
        }

        public boolean escalateCall(List<String> output) {
            if (talkingTo != null && rank < NUMBER_OF_RANKS - 1) {
                if (endCall(output)) {
                    rank++;
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return number;
        }
    }

    public static class CallCenter {

        private List<List<Employee>> employees = new ArrayList<>();
        private Map<String, CallInstance> callMap = new HashMap<>();
        private List<Deque<CallInstance>> callQueue = new ArrayList<>();


        public CallCenter() {
            for (int i = 0; i < NUMBER_OF_RANKS; i++) {
                employees.add(new ArrayList<>());
                callQueue.add(new ArrayDeque<>());
            }
        }

        public void registerPerson(String name, String role) {
            Employee employee;
            if (role.equals("Respondent")) {
                employee = new Respondent(name);
            } else if (role.equals("Manager")) {
                employee = new Manager(name);
            } else if (role.equals("Director")) {
                employee = new Director(name);
            } else {
                return;
            }

            List<Employee> employeeList = employees.get(employee.rank());
            if (employeeList.stream().noneMatch(e -> e.getName().equals(name))) {
                employeeList.add(employee);
            }
        }

        public void addNumberToQueue(String number) {
            if (!callMap.containsKey(number)) {
                callMap.put(number, new CallInstance(number));
                callQueue.get(0).offer(callMap.get(number));
            }
        }

        public void resolveQueue(List<String> output) {
            for (int rank = NUMBER_OF_RANKS - 1; rank >= 0; rank--) {
                Deque<CallInstance> callQueue = this.callQueue.get(rank);
                List<Employee> employeeList = employees.get(rank);
                while (!callQueue.isEmpty()) {
                    CallInstance callInstance = callQueue.getFirst();
                    boolean resolved = false;

                    for (Employee e : employeeList) {
                        if (callInstance.startCall(e, output)) {
                            resolved = true;
                            break;
                        }
                    }

                    if (resolved) {
                        callQueue.poll();
                    } else {
                        break;
                    }
                }
            }
        }

        public void escalate(String number, List<String> output) {
            if (this.callMap.containsKey(number)) {
                CallInstance callInstance = this.callMap.get(number);
                if (callInstance.escalateCall(output)) {
                    this.callQueue.get(callInstance.rank).offer(callInstance);
                }
            }
        }

        public void work(String role, String name, List<String> output) {
            int rank;
            if (role.equals("Respondent")) {
                rank = 0;
            } else if (role.equals("Manager")) {
                rank = 1;
            } else if (role.equals("Director")) {
                rank = 2;
            } else {
                return;
            }

            Optional<Employee> employeeOptional = employees.get(rank).stream().filter(e -> e.getName().equals(name)).findFirst();
            if(employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();
                if (employee.talkingTo == null) {
                    employee.work(this, output);
                }
            }
        }

        private void promoteRespondent(Manager manager, List<String> output) {
            Respondent maxRespondent = null;
            for (Employee e : employees.get(0)) {
                if (maxRespondent == null || (maxRespondent.performanceRating < ((Respondent) e).performanceRating && e.talkingTo == null)) {
                    maxRespondent = ((Respondent)e);
                }
            }

            if(maxRespondent != null) {
                employees.get(0).remove(maxRespondent);
                employees.get(1).add(new Manager(maxRespondent.getName()));
                output.add(maxRespondent + " is promoted to Manager under the authority of " + manager);
            }
        }
    }

    public static abstract class Employee {
        public CallInstance talkingTo;
        private String name;

        public Employee(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Employee person = (Employee) o;
            return Objects.equals(name, person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        public abstract int rank();

        public abstract void work(CallCenter callCenter, List<String> output);

    }

    public static class Respondent extends Employee {
        private int performanceRating ;

        public Respondent(String name) {
            super(name);
            this.performanceRating = 0;
        }

        @Override
        public int rank() {
            return 0;
        }

        @Override
        public String toString() {
            return "Respondent " + getName();
        }

        public void increaseRating() {
            this.performanceRating++;
        }

        @Override
        public void work(CallCenter callCenter, List<String> output) {
            increaseRating();
        }
    }

    public static class Manager extends Employee {

        public Manager(String name) {
            super(name);
        }

        @Override
        public int rank() {
            return 1;
        }

        @Override
        public String toString() {
            return "Manager " + getName();
        }

        @Override
        public void work(CallCenter callCenter, List<String> output) {
            callCenter.promoteRespondent(this, output);
        }
    }

    public static class Director extends Employee {

        public Director(String name) {
            super(name);
        }

        @Override
        public int rank() {
            return 2;
        }

        @Override
        public String toString() {
            return "Director " + getName();
        }

        @Override
        public void work(CallCenter callCenter, List<String> output) {
            output.add(this + " holds a meeting");
        }
    }

    public static List<String> simulateCallCenter(List<List<String>> instructions) {
        List<String> output = new ArrayList<>();
        CallCenter callCenter = new CallCenter();

        for (List<String> instruction : instructions) {
            String command = instruction.get(0);
            switch (command) {
                case "hire":
                    callCenter.registerPerson(instruction.get(2), instruction.get(1));
                    callCenter.resolveQueue(output);
                    break;
                case "dispatch":
                    callCenter.addNumberToQueue(instruction.get(1));
                    callCenter.resolveQueue(output);
                    break;
                case "end":
                    String number = instruction.get(1);
                    if(callCenter.callMap.containsKey(number)) {
                        CallInstance callInstance = callCenter.callMap.get(number);
                        if(callInstance.endCall(output)) {
                            callCenter.callMap.remove(number);
                            callCenter.resolveQueue(output);
                        }
                    }
                    break;
                case "escalate":
                    callCenter.escalate(instruction.get(1), output);
                    callCenter.resolveQueue(output);
                    break;
                case "work":
                    callCenter.work(instruction.get(1), instruction.get(2), output);
                    break;
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
        List<String> res = simulateCallCenter(instructions);
        for (String line : res) {
            System.out.println(line);
        }
    }
}

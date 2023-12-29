package com.algo.monster.advanceddatastructures.unionfind;

import java.util.*;
import java.util.stream.Collectors;

class MergeUserAccounts {

    public static class UserEmailPair implements Comparable<UserEmailPair>{
        private String name;
        private String email;

        public UserEmailPair(String name, String email) {
            this.name = name;
            this.email = email;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserEmailPair that = (UserEmailPair) o;
            return Objects.equals(name, that.name) && Objects.equals(email, that.email);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, email);
        }

        @Override
        public int compareTo(UserEmailPair o) {
            if(!name.equals(o.name)){
                return name.compareTo(o.name);
            }
            return email.compareTo(o.email);
        }
    }

    public static UnionFind<UserEmailPair> dsu = new UnionFind<>();

    public static class UnionFind<T> {
        public Map<T, T> sets = new HashMap<T, T>();

        public T find(T x) {
            T y = sets.getOrDefault(x, x);

            if (y != x) {
                y  = find(y);
                // tree compression optimization
                // moves the nodes closer to the root
                sets.put(x, y);
            }

            return y;
        }

        public void union(T x, T y) {
            sets.put(find(x), find(y));
        }
    }

    public static List<List<String>> mergeAccounts(List<List<String>> accounts) {
        HashSet<UserEmailPair> allUserEmails = new HashSet<>();
        for (List<String> account : accounts) {
            String username = account.get(0);
            UserEmailPair emailParent = null;
            for (String email: account.subList(1, account.size())) {
                UserEmailPair userEmailPair = new UserEmailPair(username, email);
                allUserEmails.add(userEmailPair);
                if (emailParent == null) {
                    emailParent = userEmailPair;
                } else {
                    dsu.union(emailParent, userEmailPair);
                }
            }
        }

        HashMap<UserEmailPair, ArrayList<UserEmailPair>> accountAssociation = new HashMap<>();
        for (UserEmailPair userEmailPair : allUserEmails) {
            UserEmailPair ancestor = dsu.find(userEmailPair);
            if (!accountAssociation.containsKey(ancestor)) {
                accountAssociation.put(ancestor, new ArrayList<>());
            }
            accountAssociation.get(ancestor).add(userEmailPair);
        }

        ArrayList<List<String>> returnRes = new ArrayList<>();
        for (Map.Entry<UserEmailPair, ArrayList<UserEmailPair>> entry : accountAssociation.entrySet())  {
            List<String> user  = new ArrayList<>();
            user.add(entry.getKey().name);
            user.addAll(entry.getValue().stream().map(p -> p.email).sorted().collect(Collectors.toList()));
            returnRes.add(user);
        }

        returnRes.sort((o1, o2) -> {
            if (o1.get(0).equals(o2.get(0)))
                return o1.get(1).compareTo(o2.get(1));
            return o1.get(0).compareTo(o2.get(0));
        });

        return returnRes;
    }

    public static boolean containsEmail(List<String> emailListOne, List<String> emailListTwo) {
        return emailListOne.stream().anyMatch(emailListTwo::contains);
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int accountsLength = Integer.parseInt(scanner.nextLine());
        List<List<String>> accounts = new ArrayList<>();
        for (int i = 0; i < accountsLength; i++) {
            accounts.add(splitWords(scanner.nextLine()));
        }
        scanner.close();
        List<List<String>> res = mergeAccounts(accounts);
        for (List<String> row : res) {
            System.out.println(String.join(" ", row));
        }
    }
}

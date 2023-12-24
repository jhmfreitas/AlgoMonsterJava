package com.algo.monster.ood;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class PublicLibrary {
    public static Map<String, Book> books = new HashMap<>();
    public static Map<String, User> users = new HashMap<>();
    public static Map<String, User> bookLoans = new HashMap<>();

    public static class Tag {
        private String name;

        public Tag(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            return ((Tag) obj).getName().equals(this.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    public static abstract class Book {
        private String id;
        private String name;

        private Set<Tag> tags = new HashSet<>();

        public static final Set<String> disallowedTags = Set.of("traditional-book", "magazine");

        public Book( String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void addTag(Tag tag) {
            if(!disallowedTags.contains(tag.getName())) {
                this.tags.add(tag);
            }
        }

        public Set<Tag> getTags() {
            return tags;
        }
    }

    public static class TraditionalBook  extends Book {

        private String author;

        public TraditionalBook(String name,String author) {
            super(name);
            this.author = author;
            addTag(new Tag("traditional-book"));
        }

        @Override
        public String toString() {
            return String.format("\"%s\" by %s", getName(), this.author);
        }

        public static TraditionalBook parseRepresentation(String representation) {
            Pattern pattern = Pattern.compile("\"(.*)\" by (.*)");
            Matcher matcher = pattern.matcher(representation);
            if(matcher.find()) {
                return new TraditionalBook(matcher.group(1), matcher.group(2));
            } else {
                return null;
            }
        }

        public String getAuthor() {
            return author;
        }

    }

    public static class Magazine extends Book {
        private String issueNumber;

        public Magazine(String name, String issueNumber) {
            super(name);
            this.issueNumber = issueNumber;
            addTag(new Tag("magazine"));
        }

        public static Magazine parseRepresentation(String representation) {
            Pattern pattern = Pattern.compile("\"(.*)\" Issue (.*)");
            Matcher matcher = pattern.matcher(representation);
            if(matcher.find()) {
                return new Magazine(matcher.group(1), matcher.group(2));
            } else {
                return null;
            }
        }

        @Override
        public String toString() {
            return String.format("\"%s\" Issue %s", getName(), this.issueNumber);
        }
    }

    public static class User {
        private String name;
        private Set<Tag> favorites = new HashSet<>();

        private Book loanedBook;

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public Set<Tag> getFavorites() {
            return favorites;
        }

        public Book getLoanedBook() {
            return loanedBook;
        }

        public void setLoanedBook(Book loanedBook) {
            this.loanedBook = loanedBook;
        }

        public void addFavorite(Tag tag) {
            this.favorites.add(tag);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            return ((User) obj).getName().equals(this.name);
        }
    }

    public static List<String> simulateLibrary(List<String> instructions) {
        List<String> output = new ArrayList<>();
        for (String instruction: instructions) {
            String[] params = instruction.split(" ", 2);
            String command = params[0];
            switch (command) {
                case "register":
                    params = params[1].split(" ", 3);
                    String bookType = params[0];
                    String id = params[1];
                    String bookRepresentation = params[2];
                    registerBook(id, bookType, bookRepresentation);
                    break;
                case "lookup":
                    params = params[1].split(" ", 2);
                    String attribute = params[0];
                    String value = params[1];
                    if(attribute.equals("id")) {
                        output.add(findById(value));
                    } else if(attribute.equals("author")) {
                        output.add(findByAuthor(value));
                    } else if(attribute.equals("title")) {
                        output.add(findByTitle(value));
                    } else if(attribute.equals("tags")) {
                        output.add(findByTags(value));
                    } else if(attribute.equals("suggestion")) {
                        output.add(findSuggestion(value));
                    }
                    break;
                case "borrow":
                    params = params[1].split(" ", 2);
                    String bookId = params[0];
                    String personName = params[1];
                    Book book = books.get(bookId);
                    if (book != null && !bookLoans.containsKey(bookId)) {
                        if(bookLoans.values().stream().noneMatch(loan -> loan.getName().equals(personName))) {
                            bookLoans.put(bookId, users.computeIfAbsent(personName, c -> new User(personName)));
                        }
                    }
                    break;
                case "return" :
                    String pName = params[1];
                    bookLoans.entrySet().stream().filter(entry -> entry.getValue().getName().equals(pName))
                            .findFirst().ifPresent(e -> bookLoans.remove(e.getKey()));
                    break;
                case "tag" :
                    params = params[1].split(" ", 2);
                    tagBook(params[0], params[1].split(" "));
                    break;
                case "favorite" :
                    params = params[1].split(" ", 2);
                    String name = params[1];
                    User user = users.computeIfAbsent(name, c -> new User(name));
                    user.addFavorite(new Tag(params[0]));
                    break;
            }
        }
        return output;
    }

    private static String findSuggestion(String personName) {
        User user = users.get(personName);
        String output = "No such book exists";

        if(user != null) {
            Set<Tag> favorites = user.getFavorites();
            int maxScore = 0;
            for (Book book: books.values()) {
                int count = 0;
                for (Tag tag : favorites) {
                    if(book.getTags().contains(tag)) {
                        count++;
                    }
                }
                maxScore = Integer.max(maxScore, count);
            }

            if(maxScore == 0) {
                return "No such book exists";
            }

            List<Book> suggestions = new ArrayList<>();
            for (Book book: books.values()) {
                int count = 0;
                for (Tag tag : favorites) {
                    if(book.getTags().contains(tag)) {
                        count++;
                    }
                }

                if (count == maxScore) {
                    suggestions.add(book);
                }
            }

            if (suggestions.size() == 1) {
                Book book = suggestions.get(0);
                User borrower = bookLoans.get(book.getId());
                return book + "\nID: " + book.getId() + (borrower != null ? "\nBorrowed by: " + borrower.getName() : "");
            }

            long count = suggestions.stream().filter(b -> !bookLoans.containsKey(b.getId())).count();
            output = String.format("%d books are suggested for: %s\n%d book(s) available", suggestions.size(), personName, count);
        }

        return output;
    }

    private static void tagBook(String bookId, String[] tagsArray) {
        Book book = books.get(bookId);
        if (book != null) {
            Arrays.stream(tagsArray).forEach(t -> book.addTag(new Tag(t)));
        }
    }

    private static void registerBook(String id, String bookType, String bookRepresentation) {
        if(!books.containsKey(id)) {
            Book book = null;
            if(bookType.equals("book")) {
                book = TraditionalBook.parseRepresentation(bookRepresentation);
            } else if(bookType.equals("magazine")) {
                book = Magazine.parseRepresentation(bookRepresentation);
            }

            if(book != null) {
                book.setId(id);
                books.put(id, book);
            }
        }
    }

    private static String findByTags(String tags) {
        String[] tagsArray = tags.split(" ");
        Set<Tag> tagsToSearch = Arrays.stream(tagsArray).map(Tag::new).collect(Collectors.toSet());
        List<Book> matches = books.values().stream().filter(b -> b.getTags().size() >= tagsArray.length).filter(b -> b.getTags().containsAll(tagsToSearch)).collect(Collectors.toList());
        return getFilteredBook(tags, matches, "tag(s)");
    }

    private static String findByTitle(String title) {
        List<Book> traditionalBooks = books.values().stream().filter(b -> b.getName().equals(title)).collect(Collectors.toList());
        return getFilteredBook(title, traditionalBooks, "title");
    }

    private static String findById(String id) {
        Book book = books.get(id);
        return getFilteredBook("0",book != null ? List.of(book) : List.of(), "id");
    }

    private static String findByAuthor(String author) {
        List<Book> traditionalBooks = books.values().stream().filter(b -> b instanceof TraditionalBook)
                .map(b -> (TraditionalBook) b).filter(b -> b.getAuthor().equals(author)).collect(Collectors.toList());
        return getFilteredBook(author, traditionalBooks, "author");
    }

    private static String getFilteredBook(String value, List<Book> filteredBooks, String criteria) {
        if (filteredBooks.isEmpty()) {
            return "No such book exists";
        } else if (filteredBooks.size() == 1) {
            Book book = filteredBooks.get(0);
            User borrower = bookLoans.get(book.getId());
            return book + "\nID: " + book.getId() + (borrower != null ? "\nBorrowed by: " + borrower.getName() : "");
        } else {
            long count = filteredBooks.stream().filter(b -> !bookLoans.containsKey(b.getId())).count();
            return String.format("%d books match the " + criteria + ": %s\n%d book(s) available", filteredBooks.size(), value, count);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int instructionsLength = Integer.parseInt(scanner.nextLine());
        List<String> instructions = new ArrayList<>();
        for (int i = 0; i < instructionsLength; i++) {
            instructions.add(scanner.nextLine());
        }
        scanner.close();
        List<String> res = simulateLibrary(instructions);
        for (String line : res) {
            System.out.println(line);
        }
    }
}

package com.algo.monster.dfs;

import java.util.*;

/**
 *
 * Given a binary tree, write a serialize function that converts the tree into a string and a deserialize function that converts a string to a binary tree.
 * You may serialize the tree into any string representation you want as long as it can be deserialized.
 *
 * Time Complexity: O(n) to traverse n nodes/elements
 *
 * Space Complexity:
 *
 * serialize - O(h) stack memory where h is the height of the tree, which is worst case O(n)
 * deserialize - O(h) stack memory (worst case O(n)) and O(n) new nodes, O(n) dominates the space complexity
 *
 */
class SerializingDeserializingBinaryTree {
    public static String serialize(Node root) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        serializeDfs(root, stringJoiner);
        return stringJoiner.toString();
    }

    private static void serializeDfs(Node root, StringJoiner stringJoiner) {
        if(root == null) {
            stringJoiner.add("x");
            return;
        }
        stringJoiner.add(root.val + "");
        serializeDfs(root.left, stringJoiner);
        serializeDfs(root.right, stringJoiner);
    }

    public static Node deserialize(String root) {
        return deserializeDfs(Arrays.asList(root.split(" ")).iterator());
    }

    private static Node deserializeDfs(Iterator<String> list) {
        String value = list.next();
        if(value.equals("x")) {
            return null;
        }

        Node node = new Node(Integer.parseInt(value));
        node.left = deserializeDfs(list);
        node.right = deserializeDfs(list);
        return node;
    }

    /** Driver class, do not change **/
    static class Node {
        int val;
        Node left, right;

        public Node(int val) {
            this.val = val;
        }

        public static Node buildTree(Iterator<String> iter) {
            String nxt = iter.next();
            if (nxt.equals("x")) return null;
            Node node = new Node(Integer.parseInt(nxt));
            node.left = buildTree(iter);
            node.right = buildTree(iter);
            return node;
        }

        public static void printTree(Node root, List<String> out) {
            if (root == null) {
                out.add("x");
            } else {
                out.add(String.valueOf(root.val));
                printTree(root.left, out);
                printTree(root.right, out);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Node root = Node.buildTree(Arrays.stream(scanner.nextLine().split(" ")).iterator());
        scanner.close();
        Node newRoot = deserialize(serialize(root));
        ArrayList<String> out = new ArrayList<>();
        Node.printTree(newRoot, out);
        System.out.println(String.join(" ", out));
    }
}

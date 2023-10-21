package com.algo.monster.dfs;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Lowest common ancestor (LCA) of two nodes v and w in a tree is the lowest (i.e. deepest) node that has both v and w as descendants.
 * We also define each node to be a descendant of itself (so if v has a direct connection from w, w is the lowest common ancestor).
 *
 * You can assume each node value in the tree is unique and that both target nodes are guaranteed to exist in the tree.
 *
 * Given two nodes of a binary tree, find their lowest common ancestor.
 *
 * Time Complexity
 * The time complexity is O(n) where n is the number of nodes on the tree because in the worst case we need to traverse through each and every node.
 *
 * Space Complexity
 * The space complexity is O(h) because the stack memory depends on the height of the tree. But in the worst case, this is O(n).
 * A skewed tree such that all nodes have zero or one child has the height O(n).
 *
 */
class LowestCommonAncestorBT {
    public static Node lca(Node root, Node node1, Node node2) {
        if( root == null ) {
            return null;
        }

        if(root.val == node1.val || root.val == node2.val) {
            return root;
        }

        Node left = lca(root.left, node1, node2);
        Node right = lca(root.right, node1, node2);

        if(left != null && right != null) {
            return root;
        }

        if(left != null){
            return left;
        }

        return right;
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

        public static Node findNode(Node root, int target) {
            if (root == null) return null;
            if (root.val == target) return root;
            Node leftSearch = findNode(root.left, target);
            if (leftSearch != null) {
                return leftSearch;
            }
            return findNode(root.right, target);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Node root = Node.buildTree(Arrays.stream(scanner.nextLine().split(" ")).iterator());
        Node node1 = Node.findNode(root, Integer.parseInt(scanner.nextLine()));
        Node node2 = Node.findNode(root, Integer.parseInt(scanner.nextLine()));
        scanner.close();
        Node ans = lca(root, node1, node2);
        System.out.println(ans == null ? "null" : ans.val);
    }
}

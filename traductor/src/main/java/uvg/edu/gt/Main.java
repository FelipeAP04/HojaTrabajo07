package uvg.edu.gt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

class TreeNode {
    String key;
    String value;
    TreeNode left, right;

    public TreeNode(String key, String value) {
        this.key = key.toLowerCase();
        this.value = value;
        left = right = null;
    }
}

class BinarySearchTree {
    TreeNode root;

    public BinarySearchTree() {
        root = null;
    }

    void insert(String key, String value) {
        root = insertRec(root, key, value);
    }

    TreeNode insertRec(TreeNode root, String key, String value) {
        if (root == null) {
            root = new TreeNode(key, value);
            return root;
        }

        if (key.compareTo(root.key) < 0)
            root.left = insertRec(root.left, key, value);
        else if (key.compareTo(root.key) > 0)
            root.right = insertRec(root.right, key, value);

        return root;
    }

    void inorder() {
        inorderRec(root);
    }

    void inorderRec(TreeNode root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print("(" + root.key + ", " + root.value + ") ");
            inorderRec(root.right);
        }
    }

    String translate(String word) {
        return translateRec(root, word.toLowerCase());
    }

    String translateRec(TreeNode root, String word) {
        if (root == null)
            return "*" + word + "*";

        if (word.compareTo(root.key) < 0)
            return translateRec(root.left, word);
        else if (word.compareTo(root.key) > 0)
            return translateRec(root.right, word);

        return root.value;
    }
}

public class Main {
    public static void main(String[] args) {
        BinarySearchTree dictionary = new BinarySearchTree();

        // Leer el archivo diccionario.txt desde recursos de Maven
        try (InputStream inputStream = Main.class.getResourceAsStream("/diccionario.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String key = parts[0].trim().substring(1).toLowerCase();
                String value = parts[1].trim().substring(0, parts[1].trim().length() - 1);
                dictionary.insert(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("Diccionario ordenado: ");
        dictionary.inorder();
        System.out.println();

        // Leer el archivo texto.txt desde recursos de Maven
        try (InputStream inputStream = Main.class.getResourceAsStream("/texto.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    System.out.print(dictionary.translate(word) + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class TreeNode {
    String key;
    String value;
    TreeNode left, right;

    public TreeNode(String key, String value) {
        this.key = key.toLowerCase(); // Convertir la clave a minúsculas para ignorar mayúsculas y minúsculas
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

        // Insertar en el subárbol izquierdo si la clave es menor que la raíz
        if (key.compareTo(root.key) < 0)
            root.left = insertRec(root.left, key, value);
        // Insertar en el subárbol derecho si la clave es mayor que la raíz
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
        return translateRec(root, word.toLowerCase()); // Convertir la palabra a minúsculas para buscar en el diccionario
    }

    String translateRec(TreeNode root, String word) {
        if (root == null)
            return "*" + word + "*"; // Palabra no encontrada en el diccionario

        // Buscar en el subárbol izquierdo si la palabra es menor que la raíz
        if (word.compareTo(root.key) < 0)
            return translateRec(root.left, word);
        // Buscar en el subárbol derecho si la palabra es mayor que la raíz
        else if (word.compareTo(root.key) > 0)
            return translateRec(root.right, word);

        return root.value; // Palabra encontrada en el diccionario
    }
}

public class Main {
    public static void main(String[] args) {
        BinarySearchTree dictionary = new BinarySearchTree();

        // Leer el archivo diccionario.txt y construir el árbol binario de búsqueda
        try (BufferedReader br = new BufferedReader(new FileReader("diccionario.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String key = parts[0].trim().substring(1).toLowerCase(); // Eliminar paréntesis y convertir a minúsculas
                String value = parts[1].trim().substring(0, parts[1].trim().length() - 1);
                dictionary.insert(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Imprimir el diccionario en orden In-order
        System.out.print("Diccionario ordenado: ");
        dictionary.inorder();
        System.out.println();

        // Leer el archivo texto.txt y traducir cada palabra
        try (BufferedReader br = new BufferedReader(new FileReader("texto.txt"))) {
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

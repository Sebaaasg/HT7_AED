/**
 * REFERENCIA DE IMPLEMENTACIÓN:
 * La lógica base y la comprensión para la estructura de los nodos 
 * y las ramas del Árbol Binario de Búsqueda BST fueron tomadas de los 
 * conceptos descritos en el libro de texto "Java Structures", Capítulo 12.
 * 
 * NOTA:
 * A diferencia de la implementación original sugerida en el libro que 
 * hace uso de la recursividad, para esta hoja de trabajo tomé la decisión 
 * de rediseñar los métodos de inserción, búsqueda y el recorrido In-Order 
 * hacia un enfoque iterativo usando ciclos while y la estructura de datos
 * Pila. Esta adaptación la realicé para evitar problemas de desbordamiento
 * de memoria StackOverflow, ya que el árbol no se balancea solo, por lo que al hacer el
 * profiling con miles de datos, el árbol tendrá ramas larguísimas y al usar
 * recursividad, por cada nodo que se baje, Java abre un nuevo proceso en la memoria 
 * interna del procesador, llamado Call Stack. Dicha memoria es pequeña por 
 * lo que si se tienen muchos datos es muy probable que el programa colapse. 
 * Es por ello que decidí usar ciclos básicos while para garantizar mejor 
 * rendimiento de la CPU durante el profiling masivo con miles de datos.
 */

import java.util.Stack;

// E es el tipo de elemento que guardará el árbol
// tiene que ser Comparable para decidir si va a izquierda o derecha
public class BinaryTree<E extends Comparable<E>> {
    
    // Clase que representa cada nodo del arbol
    private class TreeNode {
        E data;
        TreeNode leftChild;
        TreeNode rightChild;

        // CONSTRUCTOR
        TreeNode(E data) {
            this.data = data;
            this.leftChild = null;
            this.rightChild = null;
        }
    }

    // raiz del arbol
    private TreeNode root;

    public BinaryTree() {
        this.root = null;
    }

    // metodo para insertar valores
    public void insert(E data) {
        TreeNode newNode = new TreeNode(data);

        // Si está vacío el nuevo nodo se convierte en la raíz
        if (root == null) {
            root = newNode;
            return; 
        }

        TreeNode currentNode = root;
        TreeNode parentNode = null;
        boolean isInserted = false; // Bandera para controlar el while

        
        while (!isInserted) {
            parentNode = currentNode; // Se guarda al padre antes de avanzar al hijo

            // Si el dato a insertar es menor, se va a la izquierda
            if (data.compareTo(currentNode.data) < 0) {
                currentNode = currentNode.leftChild;
                
                // Si hay un espacio vacío, se inserta
                if (currentNode == null) {
                    parentNode.leftChild = newNode;
                    isInserted = true; 
                }
            } 
            // Si el dato a insertar es mayor, se va a la derecha
            else if (data.compareTo(currentNode.data) > 0) {
                currentNode = currentNode.rightChild;
                
                // Si hay un espacio vacío, se inserta
                if (currentNode == null) {
                    parentNode.rightChild = newNode;
                    isInserted = true; 
                }
            } 
            // Si el dato es igual no se hace nada, ya que no se pueden tener duplicados
            else {
                isInserted = true;
            }
        }
    }

    // Método para buscar elementos
    public E search(E targetElement) {
        TreeNode currentNode = root;
        boolean isFound = false; // bandera para controlar el while

        // buscar mientras haya nodos
        while (currentNode != null && !isFound) {
            
            // Caso por si se encuentra
            if (currentNode.data.compareTo(targetElement) == 0) {
                isFound = true;
            } 
            // Caso si el elemento buscado es menor, se avanza a la izquierda
            else if (currentNode.data.compareTo(targetElement) > 0) {
                currentNode = currentNode.leftChild;
            } 
            // Caso si el elemento buscado es mayor, se avanza a la derecha
            else {
                currentNode = currentNode.rightChild;
            }
        }

        // se verifica si la bandera se activó
        if (isFound) {
            return currentNode.data;
        } else {
            return null; // Si termina y currentNode es null, no existía
        }
    }

    // metodo para recorrer el árbol In-Order usando un STACK
    public String getInOrderString() {
        StringBuilder stringBuilder = new StringBuilder();
        Stack<TreeNode> nodeStack = new Stack<>();
        
        TreeNode currentNode = root;
        boolean isTraversalFinished = false; // Bandera para controlar el while

        while (!isTraversalFinished) {
            
            // primero se va lo más a la izquierda posible, guardando el rastro en la pila
            if (currentNode != null) {
                nodeStack.push(currentNode);
                currentNode = currentNode.leftChild;
            } 
            // segundo, si se llega al final de la izquierda se saca de la pila, se lee y se mueve a la derecha
            else {
                if (!nodeStack.isEmpty()) {
                    currentNode = nodeStack.pop(); // sacar el último nodo guardado
                    stringBuilder.append(currentNode.data.toString()).append(" "); // Guardamos su texto
                    
                    currentNode = currentNode.rightChild; // se intenta ir a su derecha
                } 
                // Si currentNode es nulo y la pila está vacía, se termina
                else {
                    isTraversalFinished = true; // bandera de salida
                }
            }
        }

        return stringBuilder.toString();
    }
}
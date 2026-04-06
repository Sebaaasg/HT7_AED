// K es la llave y V es el valor
// K tiene que ser Comparable para que el árbol pueda ordenar las asociaciones
public class Association<K extends Comparable<K>, V> implements Comparable<Association<K, V>> {
    
    // La llave es la palabra en francés 
    private K key;
    // El valor es la palabra en español
    private V value;

    // CONSTRUCTOR
    public Association(K key, V value) {
        this.key = key;
        this.value = value;
    }

    // GETTERS
    public K getKey() { 
        return key; 
    }
    
    public V getValue() { 
        return value; 
    }

    // se compara esta asociación con otra usando sus llaves
    // permite que el árbol binario se ordene alfabéticamente por la palabra en francés
    @Override
    public int compareTo(Association<K, V> otherAssociation) {
        return this.key.compareTo(otherAssociation.key);
    }

    // Formato para mostrar en pantalla: (palabra, traduccion)
    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}
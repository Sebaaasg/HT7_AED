import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BinaryTreeTest {

    private BinaryTree<Association<String, String>> dictionaryTree;

    // @Before en JUnit 4 indica al programa que este método se tiene que ejecutar ANTES de cada prueba
    // para tener un arbol limpio siempre
    @Before
    public void setUpEnvironment() {
        dictionaryTree = new BinaryTree<>();
    }

    @Test
    public void testInsertAndSearchSuccess() {
        // Crear las asociaciones a probar
        Association<String, String> firstAssociation = new Association<>("maison", "casa");
        Association<String, String> secondAssociation = new Association<>("chien", "perro");
        
        // Insertar los datos en el árbol
        dictionaryTree.insert(firstAssociation);
        dictionaryTree.insert(secondAssociation);
        
        // buscar palabra "chien" 
        // solo con la llave para que el método search pueda comparar.
        Association<String, String> searchTarget = new Association<>("chien", null);
        Association<String, String> searchResult = dictionaryTree.search(searchTarget);
        
        // comprobar que el resultado sea el esperado
        assertNotNull("El elemento debería ser encontrado en el árbol y no ser nulo", searchResult);
        assertEquals("El valor traducido tiene qu ser perro", "perro", searchResult.getValue());
    }

    @Test
    public void testSearchNotFound() {
        // insertar una palabra diferente
        dictionaryTree.insert(new Association<>("ville", "pueblo"));
        
        // buscar una palabra que no está
        Association<String, String> searchTarget = new Association<>("chat", null);
        Association<String, String> searchResult = dictionaryTree.search(searchTarget);
        
        // comprobar que el sistema devuelva nulo
        assertNull("Si el elemento no fue insertado, la búsqueda retorna nulo de forma segura", searchResult);
    }
}
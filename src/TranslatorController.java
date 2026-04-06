import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TranslatorController {
    
    // Variables para conectar el controlador con el modelo y la vista
    private BinaryTree<Association<String, String>> dictionaryTree;
    private View userView;

    // CONSTRUCTOR: recibe la vista y prepara el árbol vacío
    public TranslatorController(View userView) {
        this.dictionaryTree = new BinaryTree<>();
        this.userView = userView;
    }

    // Método principal que dirige todo el proceso
    public void startTranslationProcess() {
        loadDictionaryFile("diccionario.txt");
        
        userView.showHeader("Recorrido In-Order del Diccionario");
        userView.showMessage(dictionaryTree.getInOrderString());
        userView.printSeparator();

        userView.showHeader("Traducción del archivo texto.txt");
        translateTargetFile("texto.txt");
    }

    // Método para leer el diccionario y guardar todo en el árbol binario
    private void loadDictionaryFile(String filePath) {

        // BufferedReader lee el texto línea por línea
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            
            // se lee hasta que sea null 
            while ((currentLine = bufferedReader.readLine()) != null) {
                
                // limpiar la línea quitando los paréntesis y los espacios extra al inicio o final
                currentLine = currentLine.replace("(", "").replace(")", "").trim();
                
                // dividir la línea en dos partes separadas por la coma
                String[] wordParts = currentLine.split(",");
                
                if (wordParts.length == 2) {
                    // convertir todo a minúsculas para que no importen las mayúsculas al buscar
                    String frenchWord = wordParts[0].trim().toLowerCase();
                    String spanishWord = wordParts[1].trim().toLowerCase();
                    
                    // insertar la nueva pareja de palabras en el árbol
                    dictionaryTree.insert(new Association<>(frenchWord, spanishWord));
                }
            }
            userView.showMessage("Diccionario cargado de forma correcta");
            
        } catch (IOException exception) {
            userView.showError("Problema al leer el archivo " + filePath + ": " + exception.getMessage());
        }
    }

    // Método para leer el texto en francés y traducirlo al español
    private void translateTargetFile(String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            
            while ((currentLine = bufferedReader.readLine()) != null) {
                // separar la frase en palabras individuales usando los espacios
                String[] individualWords = currentLine.split(" ");
                
                // preparador de texto para construir la oración final traducida
                StringBuilder translatedLineBuilder = new StringBuilder();

                for (String word : individualWords) {
                    // se limpia la palabra de signos de puntuación usando una expresión regular solo se dejan letras 
                    String cleanedWord = word.replaceAll("[^a-zA-ZáéíóúÁÉÍÓÚñÑ]", "").toLowerCase();
                    
                    // crear una asociación falsa solo con la palabra en francés para poder buscarla
                    Association<String, String> searchTarget = new Association<>(cleanedWord, null);
                    
                    // buscar esta asociación en el árbol
                    Association<String, String> searchResult = dictionaryTree.search(searchTarget);

                    // si el resultado no es nulo entonces se encontró la palabra
                    if (searchResult != null) {
                        translatedLineBuilder.append(searchResult.getValue()).append(" ");
                    } 
                    // si es nulo la palabra no existe
                    else {
                        translatedLineBuilder.append("*").append(word).append("* ");
                    }
                }
                
                // Enviar la línea completa a la vista para que la imprima
                userView.showMessage(translatedLineBuilder.toString().trim());
            }
        } catch (IOException exception) {
            userView.showError("Problema al leer el archivo " + filePath + ": " + exception.getMessage());
        }
    }
}
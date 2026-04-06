public class Main {
    public static void main(String[] args) {
        // iniciar vista
        View applicationView = new View();
        
        // iniciar controlador y se le da la vista para que pueda comunicarse
        TranslatorController mainController = new TranslatorController(applicationView);
        
        // iniciar todo
        mainController.startTranslationProcess();
    }
}
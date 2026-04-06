public class View {
    
    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String errorMessage) {
        System.err.println("ERROR: " + errorMessage);
    }

    public void showHeader(String title) {
        System.out.println("\n--- " + title + " ---");
    }

    public void printSeparator() {
        System.out.println("\n------------------------------------------------\n");
    }
}
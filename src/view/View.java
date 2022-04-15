package view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class View {

    private static final String LINKED_TREE_LOGO = """
              \s\s
              _      _       _            _   _______           \s
             | |    (_)     | |          | | |__   __|          \s
             | |     _ _ __ | | _____  __| |    | |_ __ ___  ___\s
             | |    | | '_ \\| |/ / _ \\/ _` |    | | '__/ _ \\/ _ \\
             | |____| | | | |   <  __/ (_| |    | | | |  __/  __/
             |______|_|_| |_|_|\\_\\___|\\__,_|    |_|_|  \\___|\\___|
                                                                \s
            """;
    private static final String LINKED_TREE_MENU = """
            1. Seguidors (Grafs)
            2. Feed (Arbres)
            3. Canvas (Arbres R)
            4. A ESPECIFICAR
            
            5. Sortir
            
            Escull una opció:\s""";

    private static final String CANVAS_MENU = """
            
                A. Afegir cercle
                B. Eliminar cercle
                C. Visualitzar
                D. Cerca per àrea
                E. Cerca especial
                
                F. Tornar enrere
            
            Quina funcionalitat vols executar?\s""";

    private static final String FEED_MENU = """
            
                A. Afegir algorisme
                B. Eliminar algorisme
                C. Llistar algorismes
                D. Cerca per timestamp (exacta)
                E. Cerca per timestamp (rang)
                F. Dibuixar Arbre d'Algorismes
       
                G. Tornar Enrere
            
            Quina funcionalitat vols executar?\s""";

    private static final String FOLLOWERS_MENU = """
                        
                A. Explorar la xarxa
                B. Recomanar usuaris
                C. Contextualitzar drama
                D. Networking
            
                E. Tornar Enrere
            
            Quina funcionalitat vols executar?\s""";

    private Scanner scanner;

    private static final String EXCEPTION_MESSAGE = """
        Error. Introdueix una opció valida:\s""";

    public View() {
        scanner = new Scanner(System.in);
    }

    public void printStartMenu(){
        System.out.println(LINKED_TREE_LOGO);
        System.out.print(LINKED_TREE_MENU);
    }

    /**
     * Metode que mostra per consola el missatge que li han enviat sense \n
     * @param message la String que es vol imprimir per pantalla sense \n
     */
    public void printMessage(String message){
        System.out.println(message);
    }

    public void printMessageWithoutLine(String message){
        System.out.print(message);
    }

    /**
     * Mostra per consola una linia en blanc
     */
    public void printMessage(){
        System.out.println();
    }

    /**
     * Prints the followers menu
     */
    public void printFollowersMenu() {
        System.out.print(FOLLOWERS_MENU);
    }

    /**
     * Prints the feed menu
     */
    public void printFeedMenu() {
        System.out.print(FEED_MENU);
    }

    /**
     * Prints canvas menu
     */
    public void printCanvasMenu() {
        System.out.print(CANVAS_MENU);
    }

    /**
     * Metode que demana a l'usuari un enter controlant l'excepcio de Input Mismatch
     * @return l'enter que ha escrit l'usuari
     */
    public int askForInteger() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.print(EXCEPTION_MESSAGE);
            } finally {
                scanner.nextLine();
            }
        }
    }

    /**
     * Metode que demana a l'usuari un decimal controlant l'excepcio de Input Mismatch
     * @return el decimal que ha escrit l'usuari
     */
    public float askForFloat() {
        while (true) {
            try {
                return scanner.nextFloat();
            } catch (InputMismatchException e) {
                System.out.print(EXCEPTION_MESSAGE);
            } finally {
                scanner.nextLine();
            }
        }
    }

    /**
     * Metode que demana a l'usuari un caracter
     * @return el caracter que ha escrit l'usuari
     */
    public char askForChar() {
        return scanner.next().charAt(0);
    }

    /**
     * Metode que demana a l'usuari una cadena
     * @return la string que ha escrit l'usuari
     */
    public String askForString() {
        return scanner.nextLine();
    }
}

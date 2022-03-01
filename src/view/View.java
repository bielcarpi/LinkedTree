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
            2. A ESPECIFICAR
            3. A ESPECIFICAR
            4. A ESPECIFICAR
            
            5. Sortir
            
            Escull una opció:\s""";

    private static final String FOLLOWERS_MENU = """
            
            
            .* Followers Menu *.
            
            A. Explorar la xarxa
            B. Recomanar usuaris
            C. Contextualitzar drama
            D. Networking
            
            E. Tornar Enrere
            
            Quina funcionalitat vols executar?\s""";

    private Scanner scanner;

    private static final String INTEGER_EXCEPTION_MESSAGE = """
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

    public void printFollowersMenu() {
        System.out.print(FOLLOWERS_MENU);
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
                System.out.println(INTEGER_EXCEPTION_MESSAGE);
            } finally {
                scanner.nextLine();
            }
        }
    }

    /**
     * Metode que demana a l'usuari un caracter
     * @return el caracter que ha escrit l'usuari
     */
    public char askForChar() { return scanner.next().charAt(0); }

}

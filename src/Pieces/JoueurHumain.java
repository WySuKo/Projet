package Pieces;

import java.util.Scanner;

public class JoueurHumain extends Joueur{
    //scanner
    private Scanner scanner;

    public JoueurHumain() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String obtenirCoup() {
        return scanner.nextLine();
    }
}

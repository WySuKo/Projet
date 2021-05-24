package bpo2.echecs.joueurs;

import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.jeu.Plateau;

import java.util.Scanner;

public class JoueurHumain extends Joueur {
    private final Scanner scanner;

    public JoueurHumain(Couleur couleurJoueur) {
        super(couleurJoueur);
        scanner = new Scanner(System.in);
    }

    @Override
    public String obtenirCoup(Plateau plateau) {
        System.out.print("> ");
        return scanner.nextLine();
    }
}

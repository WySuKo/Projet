package bpo2.echecs.joueurs;

import bpo2.echecs.jeu.CouleurPiece;
import bpo2.echecs.jeu.IJoueur;
import bpo2.echecs.jeu.Plateau;

import java.util.Scanner;

public class JoueurHumain extends Joueur {
    private final Scanner scanner;

    public JoueurHumain(CouleurPiece couleurJoueur) {
        super(couleurJoueur);
        scanner = new Scanner(System.in);
    }

    @Override
    public String obtenirCoup(Plateau plateau) {
        return scanner.nextLine();
    }
}

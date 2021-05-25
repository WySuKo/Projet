package bpo2.echecs.joueurs;

import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.jeu.Plateau;

import java.util.Scanner;

/***
 * Implémentation d'un joueur contrôle par un humain
 */
public class JoueurHumain extends Joueur {
    private final Scanner scanner;

    public JoueurHumain(Couleur couleurJoueur) {
        super(couleurJoueur);
        scanner = new Scanner(System.in);
    }

    /***
     * Renvoie le coup que le joueur joue, sous forme de chaîne de caractères
     * @param plateau
     * @return le coup joué
     */
    @Override
    public String obtenirCoup(Plateau plateau) {
        System.out.print("> ");
        return scanner.nextLine();
    }
}

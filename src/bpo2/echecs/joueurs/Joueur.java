package bpo2.echecs.joueurs;

import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.jeu.IJoueur;

public abstract class Joueur implements IJoueur {
    private Couleur couleurPieces;

    public Joueur(Couleur couleurPieces){
        this.couleurPieces = couleurPieces;
    }

    public Couleur getCouleurJoueur(){
        return couleurPieces;
    }
}

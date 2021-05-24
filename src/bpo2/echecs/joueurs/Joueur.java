package bpo2.echecs.joueurs;

import bpo2.echecs.jeu.CouleurPiece;
import bpo2.echecs.jeu.IJoueur;

public abstract class Joueur implements IJoueur {
    private CouleurPiece couleurPieces;

    public Joueur(CouleurPiece couleurPieces){
        this.couleurPieces = couleurPieces;
    }

    public CouleurPiece getCouleurJoueur(){
        return couleurPieces;
    }
}

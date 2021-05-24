package bpo2.echecs.pieces;

import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.jeu.IPiece;
import bpo2.echecs.jeu.Plateau;

public abstract class Piece implements IPiece {
    private final char caractere;
    private final Couleur couleur;
    private Case caseActuelle;

    public Piece(Couleur couleur, char caractere, Case caseDepart){
        this.couleur = couleur;
        this.caractere = couleur == Couleur.BLANC ? Character.toUpperCase(caractere) : caractere;
        this.caseActuelle = caseDepart;
    }

    public final char getRepresentation() {
        return caractere;
    }

    @Override
    public final Couleur getCouleur() {
        return couleur;
    }

    @Override
    public Case getPosition() {
        return caseActuelle;
    }

    @Override
    public boolean deplacable(Plateau plateau, Case caseArrivee){
        if(!plateau.caseValide(caseArrivee)) return false;

        if(caseActuelle.equals(caseArrivee))
            return false;

        return !plateau.caseOccupee(caseArrivee) || plateau.getPiece(caseArrivee).getCouleur() != getCouleur();
    }

    @Override
    public void deplacer(Case destination){
        caseActuelle = destination;
    }

    @Override
    public boolean estCritique() {
        return false;
    }
}



package bpo2.echecs.jeu;

import java.util.ArrayList;

public interface IPiece {
    boolean deplacable(Plateau plateau, Case caseArrivee);
    void deplacer(Case destination);
    ArrayList<Case> deplacementsPossibles(Plateau plateau);
    char getRepresentation();
    boolean estCritique();
    CouleurPiece getCouleur();
    Case getPosition();
    IPiece copieProfonde();
}

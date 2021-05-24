package bpo2.echecs.pieces;

import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.CouleurPiece;
import bpo2.echecs.jeu.IPiece;
import bpo2.echecs.jeu.Plateau;

import java.util.ArrayList;

public class Roi extends Piece {
    public Roi(CouleurPiece couleur, Case caseDepart) {
        super(couleur,'r', caseDepart);
    }

    @Override
    public boolean deplacable(Plateau plateau, Case caseArrivee) {
        if(!super.deplacable(plateau, caseArrivee))
            return false;

        if(Math.abs(caseArrivee.getX() - getPosition().getX()) > 1 || Math.abs(caseArrivee.getY()) - getPosition().getY() > 1)
            return false;

        return true;
    }

    @Override
    public IPiece copieProfonde() {
        return new Roi(getCouleur(), getPosition());
    }

    @Override
    public boolean estCritique() {
        return true;
    }

    @Override
    public ArrayList<Case> deplacementsPossibles(Plateau plateau){
        ArrayList<Case> deplacements = new ArrayList<>();
        int positionX = getPosition().getX();
        int positionY = getPosition().getY();

        //On ajoute toutes les cases entourant le roi, même celles qui sont invalides
        deplacements.add(new Case(positionX + 1, positionY));
        deplacements.add(new Case(positionX - 1, positionY));
        deplacements.add(new Case(positionX, positionY + 1));
        deplacements.add(new Case(positionX, positionY - 1));
        deplacements.add(new Case(positionX + 1, positionY + 1));
        deplacements.add(new Case(positionX + 1, positionY -1));
        deplacements.add(new Case(positionX - 1, positionY+1));
        deplacements.add(new Case(positionX - 1, positionY-1));

        //On retire de la liste les cases sur lesquelles on ne peut pas se déplacer
        //Utiliser removeIf permet à la fois de simplifier le code grâce à la lambda, mais aussi d'éviter l'erreur d'utiliser remove dans une boucle
        deplacements.removeIf(c -> !deplacable(plateau, c));

        return deplacements;
    }
}

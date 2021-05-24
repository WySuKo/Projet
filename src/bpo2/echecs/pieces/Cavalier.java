package bpo2.echecs.pieces;

import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.jeu.IPiece;
import bpo2.echecs.jeu.Plateau;

import java.util.ArrayList;

public class Cavalier extends Piece {
    public Cavalier(Couleur couleur, Case caseDepart) {
        super(couleur, 'c', caseDepart);
    }

    @Override
    public boolean deplacable(Plateau plateau, Case caseArrivee){
        if(!super.deplacable(plateau, caseArrivee))
            return false;

        int xDifference = caseArrivee.getX() - getPosition().getX();
        int yDifference = caseArrivee.getY() - getPosition().getY();

        return (Math.abs(xDifference) == 2 && Math.abs(yDifference) == 1) || (Math.abs(xDifference) == 1 && Math.abs(yDifference) == 2);
    }

    @Override
    public IPiece copieProfonde(){
        return new Cavalier(getCouleur(), getPosition());
    }

    @Override
    public ArrayList<Case> deplacementsPossibles(Plateau plateau){
        ArrayList<Case> deplacements = new ArrayList<>();
        int positionX = getPosition().getX();
        int positionY = getPosition().getY();

        //On ajoute toutes les cases entourant le cavalier, même celles qui sont invalides
        deplacements.add(new Case(positionX + 2, positionY + 1));
        deplacements.add(new Case(positionX + 2, positionY - 1));
        deplacements.add(new Case(positionX + 1, positionY + 2));
        deplacements.add(new Case(positionX + 1, positionY - 2));
        deplacements.add(new Case(positionX - 1, positionY + 2));
        deplacements.add(new Case(positionX - 1, positionY - 2));
        deplacements.add(new Case(positionX - 2, positionY + 1));
        deplacements.add(new Case(positionX - 2, positionY - 1));

        //On retire de la liste les cases sur lesquelles on ne peut pas se déplacer
        //Utiliser removeIf permet à la fois de simplifier le code grâce à la lambda, mais aussi d'éviter l'erreur d'utiliser remove dans une boucle
        deplacements.removeIf(c -> !deplacable(plateau, c));

        return deplacements;
    }
}

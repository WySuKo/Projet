package bpo2.echecs.tests;

import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.jeu.Plateau;
import bpo2.echecs.pieces.Cavalier;
import bpo2.echecs.pieces.Roi;
import bpo2.echecs.pieces.Tour;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDeplacementsCavalier {
    @Test
    public void testDeplacementsCavalier(){
        Plateau plateau = new Plateau();
        Cavalier cavalier = new Cavalier(Couleur.BLANC, new Case(4, 4));
        plateau.ajouterPiece(cavalier);

        ArrayList<Case> coups = cavalier.deplacementsPossibles(plateau);
        assertEquals(coups.size(), 8);

        Roi roi = new Roi(Couleur.BLANC, new Case(4, 3));
        plateau.ajouterPiece(roi);

        coups = cavalier.deplacementsPossibles(plateau);
        assertEquals(coups.size(), 8);

        Tour tour = new Tour(Couleur.BLANC, new Case(6, 5));
        plateau.ajouterPiece(tour);

        coups = cavalier.deplacementsPossibles(plateau);
        assertEquals(coups.size(), 7);

        assertTrue(coups.contains(new Case(6, 3)));
        assertTrue(coups.contains(new Case(5, 6)));
        assertTrue(coups.contains(new Case(3, 6)));
        assertTrue(coups.contains(new Case(2, 5)));
        assertTrue(coups.contains(new Case(2, 3)));
        assertTrue(coups.contains(new Case(5, 2)));
        assertTrue(coups.contains(new Case(3, 2)));
    }

    @Test
    public void testDeplacementCavalierCoin(){
        Plateau plateau = new Plateau();
        Cavalier cavalier = new Cavalier(Couleur.BLANC, new Case(0, 0));
        plateau.ajouterPiece(cavalier);

        ArrayList<Case> coups = cavalier.deplacementsPossibles(plateau);
        assertEquals(coups.size(), 2);

        assertTrue(coups.contains(new Case(2, 1)));
        assertTrue(coups.contains(new Case(1, 2)));
    }
}

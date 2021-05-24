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

public class TestDeplacementsTour {
    @Test
    public void testDeplacementTour(){
        Plateau plateau = new Plateau();
        Tour tour = new Tour(Couleur.BLANC, new Case(2, 3));
        plateau.ajouterPiece(tour);

        ArrayList<Case> coupsValides = tour.deplacementsPossibles(plateau);
        assertEquals(coupsValides.size(), 14);

        Roi roi = new Roi(Couleur.NOIR, new Case(5, 3));
        plateau.ajouterPiece(roi);

        coupsValides = tour.deplacementsPossibles(plateau);
        assertEquals(coupsValides.size(), 12);

        Cavalier cavalier = new Cavalier(Couleur.BLANC, new Case(2, 5));
        plateau.ajouterPiece(cavalier);

        coupsValides = tour.deplacementsPossibles(plateau);
        assertEquals(coupsValides.size(), 9);

        assertTrue(coupsValides.contains(new Case(3, 3)));
        assertTrue(coupsValides.contains(new Case(4, 3)));
        assertTrue(coupsValides.contains(new Case(5, 3)));
        assertTrue(coupsValides.contains(new Case(2, 4)));
        assertTrue(coupsValides.contains(new Case(2, 2)));
        assertTrue(coupsValides.contains(new Case(2, 1)));
        assertTrue(coupsValides.contains(new Case(2, 0)));
        assertTrue(coupsValides.contains(new Case(1, 3)));
        assertTrue(coupsValides.contains(new Case(0, 3)));
    }

    @Test
    public void testDeplacementTourCoin(){
        Plateau plateau = new Plateau();
        Tour tour = new Tour(Couleur.BLANC, new Case(0, 0));
        plateau.ajouterPiece(tour);

        ArrayList<Case> coupsValides = tour.deplacementsPossibles(plateau);
        assertEquals(coupsValides.size(), 14);

        Roi roi = new Roi(Couleur.NOIR, new Case(0, 4));
        plateau.ajouterPiece(roi);

        Cavalier cavalier = new Cavalier(Couleur.BLANC, new Case(5, 0));
        plateau.ajouterPiece(cavalier);

        coupsValides = tour.deplacementsPossibles(plateau);
        assertEquals(coupsValides.size(), 8);

        assertTrue(coupsValides.contains(new Case(0, 1)));
        assertTrue(coupsValides.contains(new Case(0, 2)));
        assertTrue(coupsValides.contains(new Case(0, 3)));
        assertTrue(coupsValides.contains(new Case(0, 4)));
        assertTrue(coupsValides.contains(new Case(1, 0)));
        assertTrue(coupsValides.contains(new Case(2, 0)));
        assertTrue(coupsValides.contains(new Case(3, 0)));
        assertTrue(coupsValides.contains(new Case(4, 0)));
    }
}

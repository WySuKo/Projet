package bpo2.echecs.tests;

import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.jeu.Plateau;
import bpo2.echecs.pieces.Cavalier;
import bpo2.echecs.pieces.Roi;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDeplacementsRoi {
    @Test
    public void testCoupsPossibleRoiCentre(){
        Plateau plateau = new Plateau();
        plateau.ajouterPiece(new Roi(Couleur.BLANC, new Case(2, 2)));
        ArrayList<Case> deplacements = plateau.getPiece(new Case(2, 2)).deplacementsPossibles(plateau);

        assertEquals(deplacements.size(), 8);

        plateau.ajouterPiece(new Cavalier(Couleur.BLANC, new Case(3, 2)));
        deplacements = plateau.getPiece(new Case(2, 2)).deplacementsPossibles(plateau);
        assertEquals(deplacements.size(), 7);

        plateau.ajouterPiece(new Cavalier(Couleur.NOIR, new Case(2, 1)));
        deplacements = plateau.getPiece(new Case(2, 2)).deplacementsPossibles(plateau);
        assertEquals(deplacements.size(), 7);

        assertTrue(deplacements.contains(new Case(1, 1)));
        assertTrue(deplacements.contains(new Case(1, 2)));
        assertTrue(deplacements.contains(new Case(1, 3)));
        assertTrue(deplacements.contains(new Case(2, 1)));
        assertTrue(deplacements.contains(new Case(2, 3)));
        assertTrue(deplacements.contains(new Case(3, 1)));
        assertTrue(deplacements.contains(new Case(3, 3)));
    }

    @Test
    public void testCoupsPossibleRoiBordLigne(){
        Plateau plateau = new Plateau();
        plateau.ajouterPiece(new Roi(Couleur.BLANC, new Case(0, 4)));
        ArrayList<Case> deplacements = plateau.getPiece(new Case(0, 4)).deplacementsPossibles(plateau);

        assertEquals(deplacements.size(), 5);

        assertTrue(deplacements.contains(new Case(0, 5)));
        assertTrue(deplacements.contains(new Case(0, 3)));
        assertTrue(deplacements.contains(new Case(1, 3)));
        assertTrue(deplacements.contains(new Case(1, 4)));
        assertTrue(deplacements.contains(new Case(1, 5)));
    }

    @Test
    public void testDeplacementRoiCoin(){
        Plateau plateau = new Plateau();
        plateau.ajouterPiece(new Roi(Couleur.BLANC, new Case(0, 7)));
        ArrayList<Case> deplacements = plateau.getPiece(new Case(0, 7)).deplacementsPossibles(plateau);

        assertEquals(deplacements.size(), 3);

        assertTrue(deplacements.contains(new Case(0, 6)));
        assertTrue(deplacements.contains(new Case(1, 6)));
        assertTrue(deplacements.contains(new Case(1, 7)));
    }
}

package bpo2.echecs.tests;

import bpo2.echecs.exceptions.ErreurJeuException;
import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.jeu.Plateau;
import bpo2.echecs.pieces.Cavalier;
import bpo2.echecs.pieces.Roi;
import bpo2.echecs.pieces.Tour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestEchecAuRoi {
    @Test
    public void testEchec(){
        Plateau plateau = creationPlateau();

        assertFalse(plateau.echec(Couleur.BLANC));
        assertTrue(plateau.echec(Couleur.NOIR));

        try{
            plateau.deplacer(new Case(7, 3), new Case(6, 3));
            plateau.deplacer(new Case(6, 4), new Case(4, 3));
        }catch (ErreurJeuException e) {}


        assertTrue(plateau.echec(Couleur.BLANC));
        assertFalse(plateau.echec(Couleur.NOIR));
    }

    @Test
    public void testPrevisionEchec(){
        Plateau plateau = creationPlateau();

        try {
            assertTrue(plateau.echecApresDeplacement(Couleur.BLANC, new Case(6, 4), new Case(4, 3)));
            assertFalse(plateau.echecApresDeplacement(Couleur.NOIR, new Case(7, 3), new Case(6, 3)));
        }catch (ErreurJeuException e) {}

        assertTrue(plateau.caseOccupee(new Case(6, 4)));
        assertFalse(plateau.caseOccupee(new Case(4, 3)));
        assertTrue(plateau.caseOccupee(new Case(7, 3)));
        assertFalse(plateau.caseOccupee(new Case(6, 3)));
    }

    static Plateau creationPlateau() {
        Plateau plateau = new Plateau();
        plateau.ajouterPiece(new Roi(Couleur.BLANC, new Case(2, 2)));
        plateau.ajouterPiece(new Roi(Couleur.NOIR, new Case(7, 6)));
        plateau.ajouterPiece(new Tour(Couleur.BLANC, new Case(7, 3)));
        plateau.ajouterPiece(new Tour(Couleur.NOIR, new Case(3, 4)));
        plateau.ajouterPiece(new Cavalier(Couleur.BLANC, new Case(1, 6)));
        plateau.ajouterPiece(new Cavalier(Couleur.NOIR, new Case(6, 4)));
        return plateau;
    }
}

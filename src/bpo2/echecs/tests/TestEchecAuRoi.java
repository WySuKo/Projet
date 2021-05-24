package bpo2.echecs.tests;

import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.CouleurPiece;
import bpo2.echecs.jeu.Plateau;
import bpo2.echecs.pieces.Cavalier;
import bpo2.echecs.pieces.Roi;
import bpo2.echecs.pieces.Tour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEchecAuRoi {
    @Test
    public void testEchec(){
        Plateau plateau = creationPlateau();

        assertFalse(plateau.echec(CouleurPiece.BLANC));
        assertTrue(plateau.echec(CouleurPiece.NOIRE));

        plateau.deplacer(new Case(7, 3), new Case(6, 3));
        plateau.deplacer(new Case(6, 4), new Case(4, 3));

        assertTrue(plateau.echec(CouleurPiece.BLANC));
        assertFalse(plateau.echec(CouleurPiece.NOIRE));
    }

    @Test
    public void testPrevisionEchec(){
        Plateau plateau = creationPlateau();

        assertTrue(plateau.echecApresDeplacement(CouleurPiece.BLANC, new Case(6, 4), new Case(4, 3)));
        assertFalse(plateau.echecApresDeplacement(CouleurPiece.NOIRE, new Case(7, 3), new Case(6, 3)));

        assertTrue(plateau.caseOccupee(new Case(6, 4)));
        assertFalse(plateau.caseOccupee(new Case(4, 3)));
        assertTrue(plateau.caseOccupee(new Case(7, 3)));
        assertFalse(plateau.caseOccupee(new Case(6, 3)));
    }

    static Plateau creationPlateau() {
        Plateau plateau = new Plateau();
        plateau.ajouterPiece(new Roi(CouleurPiece.BLANC, new Case(2, 2)));
        plateau.ajouterPiece(new Roi(CouleurPiece.NOIRE, new Case(7, 6)));
        plateau.ajouterPiece(new Tour(CouleurPiece.BLANC, new Case(7, 3)));
        plateau.ajouterPiece(new Tour(CouleurPiece.NOIRE, new Case(3, 4)));
        plateau.ajouterPiece(new Cavalier(CouleurPiece.BLANC, new Case(1, 6)));
        plateau.ajouterPiece(new Cavalier(CouleurPiece.NOIRE, new Case(6, 4)));
        return plateau;
    }
}

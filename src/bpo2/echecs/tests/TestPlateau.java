package bpo2.echecs.tests;

import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.CouleurPiece;
import bpo2.echecs.jeu.IPiece;
import bpo2.echecs.jeu.Plateau;
import bpo2.echecs.pieces.Roi;
import bpo2.echecs.pieces.Tour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlateau {
    @Test
    public void testCreationPlateau(){
        Plateau plateau = new Plateau();
        assertEquals(Plateau.TAILLE, 8);

        for(int i = 0; i < Plateau.TAILLE; ++i)
            for(int j = 0; j < Plateau.TAILLE; j++)
                assertNull(plateau.getPiece(new Case(i, j)));
    }

    @Test
    public void testAjoutPiece(){
        Plateau plateau = new Plateau();
        Roi roi = new Roi(CouleurPiece.BLANC, new Case(2, 2));
        plateau.ajouterPiece(roi);

        assertEquals(plateau.getPiece(roi.getPosition()), roi);
        assertTrue(plateau.caseOccupee(roi.getPosition()));
    }

    @Test
    public void testSuppressionPiece(){
        Plateau plateau = new Plateau();
        Roi roi = new Roi(CouleurPiece.BLANC, new Case(2, 2));
        plateau.ajouterPiece(roi);
        plateau.retirerPiece(roi.getPosition());

        assertNull(plateau.getPiece(roi.getPosition()));
        assertFalse(plateau.caseOccupee(roi.getPosition()));
    }

    @Test
    public void testValiditeCases(){
        Plateau plateau = new Plateau();

        Case premiere = new Case(1, 1);
        Case deuxieme = new Case(-1, 7);
        Case troisieme = new Case(-3, 12);
        Case quatrieme = new Case(7, 6);
        Case cinquieme = new Case(8, 1);
        Case sixieme = new Case(0, 1);

        assertTrue(plateau.caseValide(premiere));
        assertFalse(plateau.caseValide(deuxieme));
        assertFalse(plateau.caseValide(troisieme));
        assertTrue(plateau.caseValide(quatrieme));
        assertFalse(plateau.caseValide(cinquieme));
        assertTrue(plateau.caseValide(sixieme));
    }

    @Test
    public void testRepresentationPlateau(){
        Plateau plateau = TestEchecAuRoi.creationPlateau();

        assertEquals(plateau.toString(),
                "    a   b   c   d   e   f   g   h\n" +
                "   --- --- --- --- --- --- --- ---\n" +
                "8 |   |   |   |   |   |   |   |   | 8\n" +
                "   --- --- --- --- --- --- --- ---\n" +
                "7 |   |   |   |   |   |   | C |   | 7\n" +
                "   --- --- --- --- --- --- --- ---\n" +
                "6 |   |   | R |   |   |   |   |   | 6\n" +
                "   --- --- --- --- --- --- --- ---\n" +
                "5 |   |   |   |   | t |   |   |   | 5\n" +
                "   --- --- --- --- --- --- --- ---\n" +
                "4 |   |   |   |   |   |   |   |   | 4\n" +
                "   --- --- --- --- --- --- --- ---\n" +
                "3 |   |   |   |   |   |   |   |   | 3\n" +
                "   --- --- --- --- --- --- --- ---\n" +
                "2 |   |   |   |   | c |   |   |   | 2\n" +
                "   --- --- --- --- --- --- --- ---\n" +
                "1 |   |   |   | T |   |   | r |   | 1\n" +
                "   --- --- --- --- --- --- --- ---\n" +
                "    a   b   c   d   e   f   g   h\n");

        plateau.retirerPiece(new Case(1, 6));
        plateau.deplacer(new Case(3, 4), new Case(3, 7));

        assertEquals(plateau.toString(),
                "    a   b   c   d   e   f   g   h\n" +
                        "   --- --- --- --- --- --- --- ---\n" +
                        "8 |   |   |   |   |   |   |   |   | 8\n" +
                        "   --- --- --- --- --- --- --- ---\n" +
                        "7 |   |   |   |   |   |   |   |   | 7\n" +
                        "   --- --- --- --- --- --- --- ---\n" +
                        "6 |   |   | R |   |   |   |   |   | 6\n" +
                        "   --- --- --- --- --- --- --- ---\n" +
                        "5 |   |   |   |   |   |   |   | t | 5\n" +
                        "   --- --- --- --- --- --- --- ---\n" +
                        "4 |   |   |   |   |   |   |   |   | 4\n" +
                        "   --- --- --- --- --- --- --- ---\n" +
                        "3 |   |   |   |   |   |   |   |   | 3\n" +
                        "   --- --- --- --- --- --- --- ---\n" +
                        "2 |   |   |   |   | c |   |   |   | 2\n" +
                        "   --- --- --- --- --- --- --- ---\n" +
                        "1 |   |   |   | T |   |   | r |   | 1\n" +
                        "   --- --- --- --- --- --- --- ---\n" +
                        "    a   b   c   d   e   f   g   h\n");
    }

    @Test
    public void testCopieProfondePlateau(){
        Plateau plateau = new Plateau();
        plateau.ajouterPiece(new Roi(CouleurPiece.BLANC, new Case(2, 2)));
        plateau.ajouterPiece(new Tour(CouleurPiece.NOIRE, new Case(3, 4)));

        Plateau plateauCopie = plateau.copieProfonde();
        IPiece roiCopie = plateauCopie.getPiece(new Case(2, 2));
        assertTrue(roiCopie.estCritique());
        assertEquals(roiCopie.getCouleur(), CouleurPiece.BLANC);
        assertEquals(roiCopie.getPosition(), new Case(2, 2));

        IPiece tourCopie = plateauCopie.getPiece(new Case(3, 4));
        assertFalse(tourCopie.estCritique());
        assertEquals(tourCopie.getCouleur(), CouleurPiece.NOIRE);
        assertEquals(tourCopie.getPosition(), new Case(3, 4));

        assertEquals(plateau.toString(), plateauCopie.toString());

        //on déplace les pièces
        plateauCopie.deplacer(roiCopie.getPosition(), new Case(3, 2));
        plateauCopie.deplacer(tourCopie.getPosition(), new Case(3, 7));

        assertEquals(roiCopie.getPosition(), new Case(3, 2));
        assertEquals(tourCopie.getPosition(), new Case(3, 7));

        //on compare le résultat grâce au toString
        assertNotEquals(plateau.toString(), plateauCopie.toString());
    }
}

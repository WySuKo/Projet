package bpo2.echecs.tests;

import bpo2.echecs.exceptions.CaseInvalideException;
import bpo2.echecs.exceptions.ErreurJeuException;
import bpo2.echecs.exceptions.PieceNonDeplacableException;
import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.jeu.IPiece;
import bpo2.echecs.jeu.Plateau;
import bpo2.echecs.pieces.Cavalier;
import bpo2.echecs.pieces.Roi;
import bpo2.echecs.pieces.Tour;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        Roi roi = new Roi(Couleur.BLANC, new Case(2, 2));
        plateau.ajouterPiece(roi);

        assertEquals(plateau.getPiece(roi.getPosition()), roi);
        assertTrue(plateau.caseOccupee(roi.getPosition()));

        //On teste l'ajout d'une pièce sur une case déjà occupée
        Tour tour = new Tour(Couleur.BLANC, new Case(2, 2));
        plateau.ajouterPiece(tour);

        assertEquals(plateau.getPiecesBlanches().size(), 1);
        assertTrue(plateau.getPiece(new Case(2, 2)) instanceof Tour);
    }

    @Test
    public void testDeplacementPiece(){
        Plateau plateau = new Plateau();
        Roi roi = new Roi(Couleur.BLANC, new Case(2, 2));
        plateau.ajouterPiece(roi);

        try {
            plateau.deplacer(new Case(2, 2), new Case(2, 3));
        } catch (CaseInvalideException | PieceNonDeplacableException e) {
            e.printStackTrace();
        }

        //On vérifie que la nouvelle case est bien occupée
        assertTrue(plateau.caseOccupee(new Case(2, 3)));
        //et que l'ancienne case est devenue libre
        assertFalse(plateau.caseOccupee(new Case(2, 2)));
        //et qu'il n'y a pas de doublon dans la liste des pièces
        assertEquals(plateau.getPiecesBlanches().size(), 1);
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
        try {
            plateau.deplacer(new Case(3, 4), new Case(3, 7));
        }catch (ErreurJeuException e) {}

        assertEquals(plateau.toString(),
                "    a   b   c   d   e   f   g   h\n" +
                        "   --- --- --- --- --- --- --- ---\n" +
                        "8 |   |   |   |   |   |   |   |   | 8\n" +
                        "   --- --- --- --- --- --- --- ---\n" +
                        "7 |   |   |   |   |   |   | C |   | 7\n" +
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
        plateau.ajouterPiece(new Roi(Couleur.BLANC, new Case(2, 2)));
        plateau.ajouterPiece(new Tour(Couleur.NOIR, new Case(3, 4)));

        Plateau plateauCopie = plateau.copieProfonde();
        IPiece roiCopie = plateauCopie.getPiece(new Case(2, 2));
        assertTrue(roiCopie.estCritique());
        assertEquals(roiCopie.getCouleur(), Couleur.BLANC);
        assertEquals(roiCopie.getPosition(), new Case(2, 2));

        IPiece tourCopie = plateauCopie.getPiece(new Case(3, 4));
        assertFalse(tourCopie.estCritique());
        assertEquals(tourCopie.getCouleur(), Couleur.NOIR);
        assertEquals(tourCopie.getPosition(), new Case(3, 4));

        assertEquals(plateau.toString(), plateauCopie.toString());

        //on déplace les pièces
        try {
            plateauCopie.deplacer(roiCopie.getPosition(), new Case(3, 2));
            plateauCopie.deplacer(tourCopie.getPosition(), new Case(3, 7));
        }catch (ErreurJeuException e) {}

        assertEquals(roiCopie.getPosition(), new Case(3, 2));
        assertEquals(tourCopie.getPosition(), new Case(3, 7));

        //on compare le résultat grâce au toString
        assertNotEquals(plateau.toString(), plateauCopie.toString());
    }

    @Test
    public void testPiecesMenacantes(){
        Plateau plateau = new Plateau();
        Roi roi = new Roi(Couleur.NOIR, new Case(2, 2));
        Cavalier cavalier = new Cavalier(Couleur.BLANC, new Case(4, 3));
        Tour tour = new Tour(Couleur.BLANC, new Case(5, 2));
        Tour tourAlliee = new Tour(Couleur.NOIR, new Case(1, 2));

        plateau.ajouterPiece(roi);
        plateau.ajouterPiece(cavalier);
        plateau.ajouterPiece(tour);
        plateau.ajouterPiece(tourAlliee);

        ArrayList<IPiece> piecesMenacantes = plateau.piecesMenacantes(roi);
        assertEquals(piecesMenacantes.size(), 2);

        try {
            plateau.deplacer(cavalier.getPosition(), new Case(6, 4));
        } catch (CaseInvalideException | PieceNonDeplacableException e) {
            e.printStackTrace();
        }

        piecesMenacantes = plateau.piecesMenacantes(roi);
        assertEquals(piecesMenacantes.size(), 1);

        assertEquals(piecesMenacantes.get(0).getPosition(), new Case(5, 2));

    }
}

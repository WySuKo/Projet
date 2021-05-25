package bpo2.echecs.tests;

import bpo2.echecs.jeu.Case;
import bpo2.echecs.jeu.Couleur;
import bpo2.echecs.jeu.Plateau;
import bpo2.echecs.pieces.Cavalier;
import bpo2.echecs.pieces.Roi;
import bpo2.echecs.pieces.Tour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEchecEtMat {
    @Test
    public void testEchecEtMat(){
        Plateau plateau = new Plateau();
        Roi roi = new Roi(Couleur.BLANC, new Case(2, 2));
        Tour tour1 = new Tour(Couleur.NOIR, new Case(0, 2));


        plateau.ajouterPiece(roi);
        plateau.ajouterPiece(tour1);

        assertFalse(plateau.echecEtMat(Couleur.BLANC));

        Tour tour2 = new Tour(Couleur.NOIR, new Case(0, 1));
        Tour tour3 = new Tour(Couleur.NOIR, new Case(0, 3));

        plateau.ajouterPiece(tour2);
        plateau.ajouterPiece(tour3);

        assertTrue(plateau.echecEtMat(Couleur.BLANC));

        Tour tourBlanche = new Tour(Couleur.BLANC, new Case(1, 7));
        plateau.ajouterPiece(tourBlanche);

        assertFalse(plateau.echecEtMat(Couleur.BLANC));
    }

    @Test
    public void testEchecEtMatPrisePieceMenacante(){
        Plateau plateau = new Plateau();
        Roi roi = new Roi(Couleur.BLANC, new Case(0, 0));
        Tour tour1 = new Tour(Couleur.BLANC, new Case(0, 1));
        Tour tour2 = new Tour(Couleur.BLANC, new Case(1, 1));

        Tour tourNoire = new Tour(Couleur.NOIR, new Case(7, 0));
        Tour tour3 = new Tour(Couleur.BLANC, new Case(7, 7)); //sans cette tour, le roi serait en échec et mat

        plateau.ajouterPiece(roi);
        plateau.ajouterPiece(tour1);
        plateau.ajouterPiece(tour2);
        plateau.ajouterPiece(tour3);
        plateau.ajouterPiece(tourNoire);

        assertFalse(plateau.echecEtMat(Couleur.BLANC));
    }

    @Test
    public void testEchecEtMatSacrifice(){
        Plateau plateau = new Plateau();
        Roi roi = new Roi(Couleur.BLANC, new Case(0, 0));
        Cavalier cavalier = new Cavalier(Couleur.BLANC, new Case(0, 1));
        Tour tour = new Tour(Couleur.BLANC, new Case(1, 1));
        Tour tourNoire = new Tour(Couleur.NOIR, new Case(7, 0));

        plateau.ajouterPiece(roi);
        plateau.ajouterPiece(cavalier);
        plateau.ajouterPiece(tour);
        plateau.ajouterPiece(tourNoire);

        assertFalse(plateau.echecEtMat(Couleur.BLANC));
    }
}

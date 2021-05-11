package Pieces;

public class Roi extends Piece{

    public Roi(PieceCouleur couleur, int x, int y){
        super(couleur,'r', x, y);
    }

    @Override
    public boolean deplacable(int x, int y, Plateau plateau) {
        if(!super.deplacable(x, y, plateau))
            return false;

        if(Math.abs(x - getX()) >1 || Math.abs(y - getY()) > 1)
            return false;

        return true;
    }
}

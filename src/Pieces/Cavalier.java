package Pieces;

public class Cavalier extends Piece{
    public Cavalier(Couleur couleur, int x, int y){
        super(couleur, 'c', x, y);
    }

    @Override
    public boolean deplacable(int x, int y, Plateau plateau){
        if(!super.deplacable(x, y, plateau)) return false;

        if((Math.abs(x-getX()) == 2 && Math.abs(y-getY()) == 1) || (Math.abs(x-getX()) == 1 && Math.abs(y - getY()) == 2)) return true;
        return false;
    }
}

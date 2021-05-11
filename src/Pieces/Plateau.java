package Pieces;

public class Plateau {
    public Piece[][] getPlateau() {
        return plateau;
    }

    private Piece[][] plateau;
    public static final char[] lettres = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    public static final int TAILLE = 8;

    public Plateau(){
        this.plateau = new Piece [TAILLE][TAILLE];
    }

    public void ajouterPiece(Piece piece){
        plateau[piece.getX()][piece.getY()] =  piece;
    }

    public void deplacerPiece(Piece piece, int x, int y)
    {
        if(!piece.deplacable(x, y, this))
            return;

        plateau[piece.getX()][piece.getY()] = null;
        plateau[x][y] = piece;
        piece.setX(x);
        piece.setY(y);
    }

    private String genererLigne(){
        return "   " +
                "--- ".repeat(8);
    }

    public String toString(){
        StringBuilder aff = new StringBuilder();
        aff.append("    ");
        for(int i = 0; i < 8; i++)
            aff.append(lettres[i]).append("   ");
        aff.append("\n");

        for(int i = 0; i < 8; i++){
            aff.append(genererLigne()).append("\n");
            aff.append(8 - i);
            for(int j = 0; j < 8; j++){
                aff.append(" | ").append(plateau[i][j] != null? plateau[i][j].getCaractere() : " ");
            }
            aff.append(" | ").append(8-i).append("\n");
        }
        aff.append(genererLigne());
        aff.append("\n    ");

        for(int i = 0; i < 8; i++)
            aff.append(lettres[i]).append("   ");
        aff.append("\n");

        return aff.toString();
    }
}

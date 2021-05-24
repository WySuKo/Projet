package bpo2.echecs.exceptions;

public class PieceNonDeplacableException extends Exception{
    public PieceNonDeplacableException(String raison){
        super(raison);
    }
}

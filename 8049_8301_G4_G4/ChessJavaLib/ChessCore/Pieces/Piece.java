package ChessCore.Pieces;

import ChessCore.*;

public abstract class Piece {
    private final Player owner;
    private int posX;
    private int posY;

    protected Piece(Player owner) {
        this.owner = owner;
    }
    
    public Piece(int posX, int posY,Player owner){
        this.posX = posX;
        this.posY = posY;
        this.owner = owner;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Player getOwner() {
        return owner;
    }

    public abstract boolean isValidMove(Move move, ChessGame game);
    public abstract boolean isAttackingSquare(Square pieceSquare, Square squareUnderAttack, ChessBoard board);
}

package ChessCore;

import ChessCore.Pieces.*;

public final class ClassicBoardInitializer implements BoardInitializer {

    private static final BoardInitializer instance = new ClassicBoardInitializer();

    private ClassicBoardInitializer() {
    }

    public static BoardInitializer getInstance() {
        return instance;
    }

    @Override
    public Piece[][] initialize() {
        PieceFactory factory = new PieceFactory();
        Piece[][] initialState = {
            {factory.createPiece(Rook.class, Player.WHITE),
                factory.createPiece(Knight.class, Player.WHITE),
                factory.createPiece(Bishop.class, Player.WHITE),
                factory.createPiece(Queen.class, Player.WHITE),
                factory.createPiece(King.class, Player.WHITE),
                factory.createPiece(Bishop.class, Player.WHITE),
                factory.createPiece(Knight.class, Player.WHITE),
                factory.createPiece(Rook.class, Player.WHITE)},
            {factory.createPiece(Pawn.class, Player.WHITE), factory.createPiece(Pawn.class, Player.WHITE), factory.createPiece(Pawn.class, Player.WHITE), factory.createPiece(Pawn.class, Player.WHITE),
                factory.createPiece(Pawn.class, Player.WHITE), factory.createPiece(Pawn.class, Player.WHITE), factory.createPiece(Pawn.class, Player.WHITE), factory.createPiece(Pawn.class, Player.WHITE)},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {factory.createPiece(Pawn.class, Player.BLACK), factory.createPiece(Pawn.class, Player.BLACK), factory.createPiece(Pawn.class, Player.BLACK), factory.createPiece(Pawn.class, Player.BLACK),
                factory.createPiece(Pawn.class, Player.BLACK), factory.createPiece(Pawn.class, Player.BLACK), factory.createPiece(Pawn.class, Player.BLACK), factory.createPiece(Pawn.class, Player.BLACK)},
            {factory.createPiece(Rook.class, Player.BLACK),
                factory.createPiece(Knight.class, Player.BLACK),
                factory.createPiece(Bishop.class, Player.BLACK),
                factory.createPiece(Queen.class, Player.BLACK),
                factory.createPiece(King.class, Player.BLACK),
                factory.createPiece(Bishop.class, Player.BLACK),
                factory.createPiece(Knight.class, Player.BLACK),
                factory.createPiece(Rook.class, Player.BLACK)}
        };
        return initialState;
    }
}

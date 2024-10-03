package ChessCore;

import ChessCore.Pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class ChessGame {

    private ChessBoard board;
    private GameStatus gameStatus = GameStatus.IN_PROGRESS;
    private Player whoseTurn = Player.WHITE;

    private Move lastMove;
    private boolean canWhiteCastleKingSide = true;
    private boolean canWhiteCastleQueenSide = true;
    private boolean canBlackCastleKingSide = true;
    private boolean canBlackCastleQueenSide = true;
    private boolean castledKING = false;
    private boolean catleQueen = false;
    private ChessBoard copiedBoard;
    private Stack<ChessBoard> gameState;
    private Stack<Move> moveHistory;
    private CareTaker careTaker;

    protected ChessGame(BoardInitializer boardInitializer) {
        this.board = new ChessBoard(boardInitializer.initialize());
        this.gameState = new Stack<>();
        this.moveHistory = new Stack<>();
        copiedBoard = new ChessBoard(board);
        careTaker = new CareTaker();
        //    saveGameState();
    }

    public boolean isCanWhiteCastleKingSide() {
        return canWhiteCastleKingSide;
    }

    public boolean isCanWhiteCastleQueenSide() {
        return canWhiteCastleQueenSide;
    }

    public boolean CastleKingSide() {
        return castledKING;
    }

    public boolean CastleQueenSide() {
        return catleQueen;
    }

    public boolean isCanBlackCastleKingSide() {
        return canBlackCastleKingSide;
    }

    public boolean isCanBlackCastleQueenSide() {
        return canBlackCastleQueenSide;
    }

    public boolean isValidMove(Move move) {
        if (isGameEnded()) {
            return false;
        }
        Piece pieceAtFrom = board.getPieceAtSquare(move.getFromSquare());
        if (pieceAtFrom == null || pieceAtFrom.getOwner() != whoseTurn || !pieceAtFrom.isValidMove(move, this)) {
            return false;
        }
        Piece pieceAtTo = board.getPieceAtSquare(move.getToSquare());
        // A player can't capture his own piece.
        if (pieceAtTo != null && pieceAtTo.getOwner() == whoseTurn) {
            return false;
        }
        return isValidMoveCore(move);
    }

    public Move getLastMove() {
        return lastMove;
    }

    public Player getWhoseTurn() {
        return whoseTurn;
    }

    public ChessBoard getBoard() {
        return board;
    }

    protected abstract boolean isValidMoveCore(Move move);

    public boolean isTherePieceInBetween(Move move) {
        return board.isTherePieceInBetween(move);
    }

    public boolean hasPieceIn(Square square) {
        return board.getPieceAtSquare(square) != null;
    }

    public boolean hasPieceInSquareForPlayer(Square square, Player player) {
        Piece piece = board.getPieceAtSquare(square);
        return piece != null && piece.getOwner() == player;
    }

    public boolean makeMove(Move move) {
        if (!isValidMove(move)) {
            return false;
        }
        castledKING = false;
        catleQueen = false;
        copiedBoard = new ChessBoard(board);
        this.gameState.add(copiedBoard);
        Square fromSquare = move.getFromSquare();
        Piece fromPiece = board.getPieceAtSquare(fromSquare);

        // If the king has moved, castle is not allowed.
        if (fromPiece instanceof King) {
            if (fromPiece.getOwner() == Player.WHITE) {
                canWhiteCastleKingSide = false;
                canWhiteCastleQueenSide = false;
            } else {
                canBlackCastleKingSide = false;
                canBlackCastleQueenSide = false;
            }
        }

        // If the rook has moved, castle is not allowed on that specific side..
        if (fromPiece instanceof Rook) {
            if (fromPiece.getOwner() == Player.WHITE) {
                if (fromSquare.getFile() == BoardFile.A && fromSquare.getRank() == BoardRank.FIRST) {
                    canWhiteCastleQueenSide = false;
                } else if (fromSquare.getFile() == BoardFile.H && fromSquare.getRank() == BoardRank.FIRST) {
                    canWhiteCastleKingSide = false;
                }
            } else {
                if (fromSquare.getFile() == BoardFile.A && fromSquare.getRank() == BoardRank.EIGHTH) {
                    canBlackCastleQueenSide = false;
                } else if (fromSquare.getFile() == BoardFile.H && fromSquare.getRank() == BoardRank.EIGHTH) {
                    canBlackCastleKingSide = false;
                }
            }
        }

        // En-passant.
        // if (fromPiece instanceof Pawn &&
        // move.getAbsDeltaX() == 1 &&
        //!hasPieceIn(move.getToare())Square())) 
        if (isEnPassant(move)) {
            board.setPieceAtSquare(lastMove.getToSquare(), null);
        }

        // Promotion
        if (fromPiece instanceof Pawn) {
            BoardRank toSquareRank = move.getToSquare().getRank();
            if (toSquareRank == BoardRank.FIRST || toSquareRank == BoardRank.EIGHTH) {
                Player playerPromoting = toSquareRank == BoardRank.EIGHTH ? Player.WHITE : Player.BLACK;
                PawnPromotion promotion = move.getPawnPromotion();
                switch (promotion) {
                    case Queen:
                        fromPiece = new Queen(playerPromoting);
                        break;
                    case Rook:
                        fromPiece = new Rook(playerPromoting);
                        break;
                    case Knight:
                        fromPiece = new Knight(playerPromoting);
                        break;
                    case Bishop:
                        fromPiece = new Bishop(playerPromoting);
                        break;
                    case None:
                        throw new RuntimeException("Pawn moving to last rank without promotion being set. This should NEVER happen!");
                }
            }
        }

        // Castle
        if (fromPiece instanceof King
                && move.getAbsDeltaX() == 2) {

            Square toSquare = move.getToSquare();
            if (toSquare.getFile() == BoardFile.G && toSquare.getRank() == BoardRank.FIRST) {
                // White king-side castle.
                // Rook moves from H1 to F1
                castledKING = true;
                Square h1 = new Square(BoardFile.H, BoardRank.FIRST);
                Square f1 = new Square(BoardFile.F, BoardRank.FIRST);
                Piece rook = board.getPieceAtSquare(h1);
                board.setPieceAtSquare(h1, null);
                board.setPieceAtSquare(f1, rook);
            } else if (toSquare.getFile() == BoardFile.G && toSquare.getRank() == BoardRank.EIGHTH) {
                // Black king-side castle.
                // Rook moves from H8 to F8
                castledKING = true;
                Square h8 = new Square(BoardFile.H, BoardRank.EIGHTH);
                Square f8 = new Square(BoardFile.F, BoardRank.EIGHTH);
                Piece rook = board.getPieceAtSquare(h8);
                board.setPieceAtSquare(h8, null);
                board.setPieceAtSquare(f8, rook);
            } else if (toSquare.getFile() == BoardFile.C && toSquare.getRank() == BoardRank.FIRST) {
                // White queen-side castle.
                // Rook moves from A1 to D1
                catleQueen = true;
                Square a1 = new Square(BoardFile.A, BoardRank.FIRST);
                Square d1 = new Square(BoardFile.D, BoardRank.FIRST);
                Piece rook = board.getPieceAtSquare(a1);
                board.setPieceAtSquare(a1, null);
                board.setPieceAtSquare(d1, rook);
            } else if (toSquare.getFile() == BoardFile.C && toSquare.getRank() == BoardRank.EIGHTH) {
                // Black queen-side castle.
                // Rook moves from A8 to D8
                catleQueen = true;
                Square a8 = new Square(BoardFile.A, BoardRank.EIGHTH);
                Square d8 = new Square(BoardFile.D, BoardRank.EIGHTH);
                Piece rook = board.getPieceAtSquare(a8);
                board.setPieceAtSquare(a8, null);
                board.setPieceAtSquare(d8, rook);
            }
        }

        board.setPieceAtSquare(fromSquare, null);
        board.setPieceAtSquare(move.getToSquare(), fromPiece);

        whoseTurn = Utilities.revertPlayer(whoseTurn);
        lastMove = move;

        updateGameStatus();
        moveHistory.add(move);
        careTaker.addMemento(new Memento(move, copiedBoard));
        return true;
    }

    private void updateGameStatus() {
        Player whoseTurn = getWhoseTurn();
        boolean isInCheck = Utilities.isInCheck(whoseTurn, getBoard());
        boolean hasAnyValidMoves = hasAnyValidMoves();
        if (isInCheck) {
            if (!hasAnyValidMoves && whoseTurn == Player.WHITE) {
                gameStatus = GameStatus.BLACK_WON;
            } else if (!hasAnyValidMoves && whoseTurn == Player.BLACK) {
                gameStatus = GameStatus.WHITE_WON;
            } else if (whoseTurn == Player.WHITE) {
                gameStatus = GameStatus.WHITE_UNDER_CHECK;
            } else {
                gameStatus = GameStatus.BLACK_UNDER_CHECK;
            }
        } else if (!hasAnyValidMoves) {
            gameStatus = GameStatus.STALEMATE;
        } else {
            gameStatus = GameStatus.IN_PROGRESS;
        }

        // Note: Insufficient material can happen while a player is in check. Consider this scenario:
        // Board with two kings and a lone pawn. The pawn is promoted to a Knight with a check.
        // In this game, a player will be in check but the game also ends as insufficient material.
        // For this case, we just mark the game as insufficient material.
        // It might be better to use some sort of a "Flags" enum.
        // Or, alternatively, don't represent "check" in gameStatus
        // Instead, have a separate isWhiteInCheck/isBlackInCheck methods.
        if (isInsufficientMaterial()) {
            gameStatus = GameStatus.INSUFFICIENT_MATERIAL;
        }

    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public boolean isGameEnded() {
        return gameStatus == GameStatus.WHITE_WON
                || gameStatus == GameStatus.BLACK_WON
                || gameStatus == GameStatus.STALEMATE
                || gameStatus == GameStatus.INSUFFICIENT_MATERIAL;
    }

    private boolean isInsufficientMaterial() {
        /*
        If both sides have any one of the following, and there are no pawns on the board:

        A lone king
        a king and bishop
        a king and knight
         */
        int whiteBishopCount = 0;
        int blackBishopCount = 0;
        int whiteKnightCount = 0;
        int blackKnightCount = 0;

        for (BoardFile file : BoardFile.values()) {
            for (BoardRank rank : BoardRank.values()) {
                Piece p = getPieceAtSquare(new Square(file, rank));
                if (p == null || p instanceof King) {
                    continue;
                }

                if (p instanceof Bishop) {
                    if (p.getOwner() == Player.WHITE) {
                        whiteBishopCount++;
                    } else {
                        blackBishopCount++;
                    }
                } else if (p instanceof Knight) {
                    if (p.getOwner() == Player.WHITE) {
                        whiteKnightCount++;
                    } else {
                        blackKnightCount++;
                    }
                } else {
                    // There is a non-null piece that is not a King, Knight, or Bishop.
                    // This can't be insufficient material.
                    return false;
                }
            }
        }

        boolean insufficientForWhite = whiteKnightCount + whiteBishopCount <= 1;
        boolean insufficientForBlack = blackKnightCount + blackBishopCount <= 1;
        return insufficientForWhite && insufficientForBlack;
    }

    private boolean hasAnyValidMoves() {
        for (BoardFile file : BoardFile.values()) {
            for (BoardRank rank : BoardRank.values()) {
                if (!getAllValidMovesFromSquare(new Square(file, rank)).isEmpty()) {
                    return true;
                }
            }
        }

        return false;
    }

    public List<Square> getAllValidMovesFromSquare(Square square) {
        ArrayList<Square> validMoves = new ArrayList<>();

        for (var i : BoardFile.values()) {
            for (var j : BoardRank.values()) {
                var sq = new Square(i, j);
                if (isValidMove(new Move.MoveBuilder(square, sq).build())) //              if (isValidMove(new Move(square, sq, PawnPromotion.Queen)))
                {
                    validMoves.add(sq);
                }
            }
        }

        return validMoves;
    }

    public Piece getPieceAtSquare(Square square) {
        return board.getPieceAtSquare(square);
    }

    public boolean isPromotionMove(Move move) {
        Square sourceSquare = move.getFromSquare();
        Square destinationSquare = move.getToSquare();

        if (!(board.getPieceAtSquare(sourceSquare) instanceof Pawn)) {
            return false;
        }

        if (isValidMove(move)) {
            {
                if ((move.getToSquare().getRank().getValue() == 7 && board.getPieceAtSquare(sourceSquare).getOwner().equals(Player.WHITE))
                        || (move.getToSquare().getRank().getValue() == 0 && board.getPieceAtSquare(sourceSquare).getOwner().equals(Player.BLACK))) {
                    return true;
                }

            }
        }
        return false;
    }

    public boolean isEnPassant(Move move) {
        if (board.getPieceAtSquare(move.getFromSquare()) instanceof Pawn
                && move.getAbsDeltaX() == 1
                && !hasPieceIn(move.getToSquare())) {
            return true;
        }
        return false;
    }

    /*public void undoMove() {
        if (!isGameEnded()) {
            if (!gameState.isEmpty()) {
                ChessBoard previousState = gameState.pop();
                 board = previousState;
            }
        }
    }*/
    public void saveGameState() {
        gameState.push(copiedBoard);
    }

    public boolean undoMove(Move move) {
        if (move != null && !isGameEnded()) {
            Square from = move.getFromSquare();
            Square to = move.getToSquare();
            Piece movedPiece = board.getPieceAtSquare(to);

            if (movedPiece instanceof King) {
                if (isCanWhiteCastleKingSide() || isCanWhiteCastleQueenSide() || isCanBlackCastleKingSide() || isCanBlackCastleQueenSide()) {
                    undoCastling(move);
                }
            } else if (movedPiece instanceof Pawn) {
                if (isPromotionMove(move)) {
                    undoPawnPromotion(move);
                } else if (isEnPassant(move)) {
                    undoEnPassant(move);
                }
            }
//            Memento lastMemento = careTaker.popLastMemento();
            ChessBoard previousBoard = gameState.pop();
            Piece capturedPiece = previousBoard.getPieceAtSquare(to);
            if (capturedPiece != null) {
                board.setPieceAtSquare(to, capturedPiece);
            } else {
                board.setPieceAtSquare(to, null);
            }

            board.setPieceAtSquare(from, movedPiece);
            whoseTurn = Utilities.revertPlayer(whoseTurn);

            if (!moveHistory.empty()) {
                moveHistory.pop();
                if (!moveHistory.empty()) {
                    lastMove = moveHistory.peek();
                } else {
                    lastMove = null;
                }
            }
            updateGameStatus();
            return true;
        }
        return false;
    }

    public void undoCastling(Move move) {
        Square fromSquare = move.getFromSquare();
        Square toSquare = move.getToSquare();

        if (getWhoseTurn() == Player.WHITE) {
            // If the move is a white king-side castling
            if (toSquare.getFile() == BoardFile.G) {
                // Move the king back to its original position
                Square kingToSquare = new Square(BoardFile.E, fromSquare.getRank());
                Piece king = board.getPieceAtSquare(toSquare);
                board.setPieceAtSquare(toSquare, null);
                board.setPieceAtSquare(kingToSquare, king);

                // Move the rook back to its original position
                Square rookToSquare = new Square(BoardFile.H, fromSquare.getRank());
                Square rookFromSquare = new Square(BoardFile.F, fromSquare.getRank());
                Piece rook = board.getPieceAtSquare(rookFromSquare);
                board.setPieceAtSquare(rookToSquare, rook);
                board.setPieceAtSquare(rookFromSquare, null);

                // If the move is a white queen-side castling
            } else if (toSquare.getFile() == BoardFile.C) {
                // Move the king back to its original position
                Square kingToSquare = new Square(BoardFile.E, fromSquare.getRank());
                Piece king = board.getPieceAtSquare(toSquare);
                board.setPieceAtSquare(toSquare, null);
                board.setPieceAtSquare(kingToSquare, king);

                // Move the rook back to its original position
                Square rookToSquare = new Square(BoardFile.A, fromSquare.getRank());
                Square rookFromSquare = new Square(BoardFile.D, fromSquare.getRank());
                Piece rook = board.getPieceAtSquare(rookFromSquare);
                board.setPieceAtSquare(rookToSquare, rook);
                board.setPieceAtSquare(rookFromSquare, null);
            }
        }

        if (getWhoseTurn() == Player.BLACK) {
            // If the move is a black king-side castling
            if (toSquare.getFile() == BoardFile.G) {
                // Move the king back to its original position
                Square kingToSquare = new Square(BoardFile.E, fromSquare.getRank());
                Piece king = board.getPieceAtSquare(toSquare);
                board.setPieceAtSquare(toSquare, null);
                board.setPieceAtSquare(kingToSquare, king);

                // Move the rook back to its original position
                Square rookToSquare = new Square(BoardFile.H, fromSquare.getRank());
                Square rookFromSquare = new Square(BoardFile.F, fromSquare.getRank());
                Piece rook = board.getPieceAtSquare(rookFromSquare);

                board.setPieceAtSquare(rookToSquare, rook);
                board.setPieceAtSquare(rookFromSquare, null);

                // If the move is a black queen-side castling
            } else if (toSquare.getFile() == BoardFile.C) {
                // Move the king back to its original position
                Square kingToSquare = new Square(BoardFile.E, fromSquare.getRank());
                Piece king = board.getPieceAtSquare(toSquare);
                board.setPieceAtSquare(toSquare, null);
                board.setPieceAtSquare(kingToSquare, king);

                // Move the rook back to its original position
                Square rookToSquare = new Square(BoardFile.A, fromSquare.getRank());
                Square rookFromSquare = new Square(BoardFile.D, fromSquare.getRank());
                Piece rook = board.getPieceAtSquare(rookFromSquare);
                board.setPieceAtSquare(rookToSquare, rook);
                board.setPieceAtSquare(rookFromSquare, null);
            }
        }
    }

    public void undoPawnPromotion(Move move) {
        Square fromSquare = move.getFromSquare();
        Square toSquare = move.getToSquare();
        //Piece promotedPiece = move.getPawnPromotion();

        // Move the promoted piece (queen, rook, knight, or bishop) back to a pawn
        Piece promotedPiece = board.getPieceAtSquare(toSquare);
        board.setPieceAtSquare(toSquare, new Pawn(promotedPiece.getOwner()));

        // Move the original pawn back to its position
        //board.setPieceAtSquare(fromSquare, promotedPiece);
    }

    public void undoEnPassant(Move move) {
        Square toSquare = move.getToSquare();
        Square capturedPawnSquare = new Square(toSquare.getFile(), move.getFromSquare().getRank());

        // Restore the captured pawn
        Piece capturedPawn = new Pawn(Utilities.revertPlayer(whoseTurn));
        board.setPieceAtSquare(capturedPawnSquare, capturedPawn);
    }
}

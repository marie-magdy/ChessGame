/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessCore.GUI;

import ChessCore.BoardFile;
import ChessCore.BoardRank;
import ChessCore.ChessBoard;
import ChessCore.ChessGame;
import ChessCore.Move;
import ChessCore.PawnPromotion;
import static ChessCore.PawnPromotion.Bishop;
import ChessCore.Pieces.Bishop;
import ChessCore.Pieces.King;
import ChessCore.Pieces.Knight;
import ChessCore.Pieces.Pawn;
import ChessCore.Pieces.Piece;
import ChessCore.Pieces.Queen;
import ChessCore.Pieces.Rook;
import ChessCore.Player;
import ChessCore.Square;
import ChessCore.Utilities;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author DELL
 */
public class Input extends MouseAdapter {

    private Square from;
    private ChessGame game;
    private BoardPanel Drawedboard;
    private PieceImage selectedPiece;
    private Square to;

    public Input(ChessGame game, BoardPanel Drawedboard) {
        this.game = game;
        this.Drawedboard = Drawedboard;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int row = e.getY() / 50;
        int col = e.getX() / 50;
        row = 7 - row;
        from = createSquare(row, col);
        selectedPiece = Drawedboard.getPieceAtSquare(row, col);

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (selectedPiece != null) {
            selectedPiece.setX(e.getX() - (50 / 2));
            selectedPiece.setY(e.getY() - (50 / 2));
            Drawedboard.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int rowFlipped = e.getY() / 50;
        int col = e.getX() / 50;
        int row = 7 - rowFlipped;
        to = createSquare(row, col);
        if (selectedPiece != null) {
            Move move = new Move.MoveBuilder(from, to)
                    .build();

            boolean isEnpassant = game.isEnPassant(move);
            boolean isPromotionMove = isPromotionMove(move);
            boolean isCastlingKingSide = (game.isCanBlackCastleKingSide() || game.isCanWhiteCastleKingSide());
            boolean isCastlingQueenSide = (game.isCanBlackCastleQueenSide() || game.isCanWhiteCastleQueenSide());
            if (isPromotionMove) {
                ListOfPromotedPiecces list = new ListOfPromotedPiecces(game, Drawedboard, from, to, selectedPiece);
                list.setVisible(true);
                return;
            }
            Move lastMove = game.getLastMove();
            if (game.makeMove(move)) {
                if (isEnpassant) {
                    Drawedboard.removePieceAtSquare(lastMove.getToSquare().getRank().getValue(),
                            lastMove.getToSquare().getFile().getValue());
                }
                if (isCastlingKingSide) {
                    BufferedImage rook = null;
                    if (game.getWhoseTurn().equals(Player.BLACK)) {
                        try {
                            rook = ImageIO.read(getClass().getClassLoader().getResource("ressources/WhiteRook.png"));
                        } catch (IOException ex) {
                            System.out.println("Eror reading image");
                        }
                        Image rookScaled = rook.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
                        PieceImage rookImage = new PieceImage(rookScaled, 5 * 50, 7 * 50, 0, 5);
                        Square rookSquare = new Square(BoardFile.F, BoardRank.FIRST);
                        if (game.getBoard().getPieceAtSquare(rookSquare) instanceof Rook && game.getBoard().getPieceAtSquare(to) instanceof King) {
                            Drawedboard.removePiece(Drawedboard.getPieceAtSquare(0, 7));
                            Drawedboard.addPieceAtSquare(rookImage);
                        }
                    }

                    if (game.getWhoseTurn().equals(Player.WHITE)) {
                        try {
                            rook = ImageIO.read(getClass().getClassLoader().getResource("ressources/BlackRook.png"));
                        } catch (IOException ex) {
                            System.out.println("Eror reading image");
                        }
                        Image rookScaled = rook.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
                        PieceImage rookImage = new PieceImage(rookScaled, 2 * 50, 7 * 50, 0, 2);
                        Square rookSquare = new Square(BoardFile.F, BoardRank.EIGHTH);
                        if (game.getBoard().getPieceAtSquare(rookSquare) instanceof Rook && game.getBoard().getPieceAtSquare(to) instanceof King) {
                            Drawedboard.removePiece(Drawedboard.getPieceAtSquare(0, 0));
                            Drawedboard.addPieceAtSquare(rookImage);
                        }
                    }
                }

                if (isCastlingQueenSide) {
                    BufferedImage rook = null;
                    if (game.getWhoseTurn().equals(Player.BLACK)) {
                        try {
                            rook = ImageIO.read(getClass().getClassLoader().getResource("ressources/WhiteRook.png"));
                        } catch (IOException ex) {
                            System.out.println("Eror reading image");
                        }
                        Image rookScaled = rook.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
                        PieceImage rookImage = new PieceImage(rookScaled, 3 * 50, 7 * 50, 0, 3);
                        Square rookSquare = new Square(BoardFile.D, BoardRank.FIRST);
                        if (game.getBoard().getPieceAtSquare(rookSquare) instanceof Rook && game.getBoard().getPieceAtSquare(to) instanceof King) {
                            Drawedboard.removePiece(Drawedboard.getPieceAtSquare(0, 0));
                            Drawedboard.addPieceAtSquare(rookImage);
                        }
                    }

                    if (game.getWhoseTurn().equals(Player.WHITE)) {
                        try {
                            rook = ImageIO.read(getClass().getClassLoader().getResource("ressources/BlackRook.png"));
                        } catch (IOException ex) {
                            System.out.println("Eror reading image");
                        }
                        Image rookScaled = rook.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
                        PieceImage rookImage = new PieceImage(rookScaled, 4 * 50, 7 * 50, 0, 4);
                        Square rookSquare = new Square(BoardFile.D, BoardRank.EIGHTH);
                        if (game.getBoard().getPieceAtSquare(rookSquare) instanceof Rook && game.getBoard().getPieceAtSquare(to) instanceof King) {
                            Drawedboard.removePiece(Drawedboard.getPieceAtSquare(0, 7));
                            Drawedboard.addPieceAtSquare(rookImage);
                        }
                    }
                }
                Drawedboard.removePieceAtSquare(row, col);
                selectedPiece.setRow(row);
                selectedPiece.setCol(col);
                selectedPiece.setX(col * 50);
                selectedPiece.setY(rowFlipped * 50);
                Drawedboard.flipBoard();
            } else {
                System.out.println("input invalid move");
                int originalX = selectedPiece.getCol();
                int originalY = 7 - selectedPiece.getRow();
                selectedPiece.setX(originalX * 50);
                selectedPiece.setY(originalY * 50);
            }
            Drawedboard.repaint();
        }
    }

    public Square createSquare(int row, int col) {
        if (game.getWhoseTurn().equals(Player.BLACK)) {
            row = 7 - row;
            col = 7 - col;
        }
        BoardFile boardCol = BoardFile.values()[col];
        BoardRank boardRow = BoardRank.values()[row];
        return new Square(boardCol, boardRow);
    }

    public PieceImage getSelectedPiece() {
        return selectedPiece;
    }

    private boolean isPromotionMove(Move move) {
        return game.getBoard().getPieceAtSquare(from) instanceof Pawn
                && ((move.getToSquare().getRank().getValue() == 7 && game.getBoard().getPieceAtSquare(move.getFromSquare()).getOwner().equals(Player.WHITE))
                || (move.getToSquare().getRank().getValue() == 0 && game.getBoard().getPieceAtSquare(move.getFromSquare()).getOwner().equals(Player.BLACK)));
    }

    public void undoMove() {
        Move undoMove = game.getLastMove();
        if (undoMove == null) {
            return;
        }
        Square fromSquare = undoMove.getFromSquare();
        Square toSquare = undoMove.getToSquare();

        boolean isEnpassant = game.isEnPassant(undoMove);
        boolean isPromotionMove = isPromotionMove(undoMove);
        boolean isCastlingKingSide = game.CastleKingSide();
        boolean isCastlingQueenSide = game.CastleQueenSide();
        if (game.undoMove(undoMove)) {
            Drawedboard.flipBoard();
            int fromRow = undoMove.getFromSquare().getRank().getValue();
            int fromCol = undoMove.getFromSquare().getFile().getValue();
            int toRow = undoMove.getToSquare().getRank().getValue();
            int toCol = undoMove.getToSquare().getFile().getValue();

            if (game.getWhoseTurn().equals(Player.BLACK)) {
                fromRow = 7 - fromRow;
                fromCol = 7 - fromCol;
                toRow = 7 - toRow;
                toCol = 7 - toCol;
            }
            
            if (isCastlingKingSide) {
                Square kingToSquare = new Square(BoardFile.E, fromSquare.getRank());
                PieceImage king = Drawedboard.getPieceAtSquare(toSquare.getRank().getValue(), toSquare.getFile().getValue());
                king.setRow(0);
                king.setCol(4);
                king.setX(4 * 50);
                king.setY((7 - 0) * 50);
                Drawedboard.addPieceAtSquare(king);

                // Move the rook back to its original position
                Square rookToSquare = new Square(BoardFile.H, fromSquare.getRank());
                Square rookFromSquare = new Square(BoardFile.F, fromSquare.getRank());
                PieceImage rook = Drawedboard.getPieceAtSquare(rookFromSquare.getRank().getValue(), rookFromSquare.getFile().getValue());
                rook.setRow(0);
                rook.setCol(7);
                rook.setX(7 * 50);
                rook.setY((7 - 0) * 50);
                Drawedboard.addPieceAtSquare(rook);
                Drawedboard.removePieceAtSquare(fromSquare.getRank().getValue(), fromSquare.getFile().getValue());
                Drawedboard.removePieceAtSquare(rookFromSquare.getRank().getValue(), rookFromSquare.getFile().getValue());
            } else if (isCastlingQueenSide) {
                Square kingToSquare = new Square(BoardFile.E, fromSquare.getRank());
                PieceImage king = Drawedboard.getPieceAtSquare(toSquare.getRank().getValue(), toSquare.getFile().getValue());
                king.setRow(0);
                king.setCol(4);
                king.setX(4 * 50);
                king.setY((7 - 0) * 50);
                Drawedboard.addPieceAtSquare(king);

                // Move the rook back to its original position
                Square rookToSquare = new Square(BoardFile.A, fromSquare.getRank());
                Square rookFromSquare = new Square(BoardFile.D, fromSquare.getRank());
                PieceImage rook = Drawedboard.getPieceAtSquare(rookFromSquare.getRank().getValue(), rookFromSquare.getFile().getValue());
                rook.setRow(0);
                rook.setCol(0);
                rook.setX(0 * 50);
                rook.setY((7 - 0) * 50);
                Drawedboard.addPieceAtSquare(rook);
                Drawedboard.removePieceAtSquare(fromSquare.getRank().getValue(), fromSquare.getFile().getValue());
                Drawedboard.removePieceAtSquare(rookFromSquare.getRank().getValue(), rookFromSquare.getFile().getValue());
            } else if (isEnpassant) {
                Piece capturedPawn = new Pawn(Utilities.revertPlayer(game.getWhoseTurn()));
                //todo    Drawedboard.setPieceAtSquare(capturedPawnSquare, capturedPawn);
            } else if (isPromotionMove) {
                PieceImage promotedPiece = Drawedboard.getPieceAtSquare(toSquare.getRank().getValue(), toSquare.getFile().getValue());

                BufferedImage pawn = null;
                if (game.getWhoseTurn() == Player.BLACK) {
                    try {
                        pawn = ImageIO.read(getClass().getClassLoader().getResource("ressources/WhitePawn.png"));
                    } catch (IOException ex) {
                        System.out.println("Eror reading image");
                    }
                    Image pawnScaled = pawn.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
                    PieceImage pawnImage = new PieceImage(pawnScaled, 5 * 50, 7 * 50, 0, 5);

                    pawnImage.setRow(toSquare.getRank().getValue());
                    pawnImage.setCol(toSquare.getFile().getValue());
                    pawnImage.setX(toSquare.getFile().getValue() * 50);
                    pawnImage.setY((7 - toSquare.getRank().getValue()) * 50);

                    Drawedboard.addPieceAtSquare(pawnImage);
                } else if (game.getWhoseTurn() == Player.WHITE) {
                    try {
                        pawn = ImageIO.read(getClass().getClassLoader().getResource("ressources/BlackPawn.png"));
                    } catch (IOException ex) {
                        System.out.println("Eror reading image");
                    }
                    Image pawnScaled = pawn.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
                    PieceImage pawnImage = new PieceImage(pawnScaled, 5 * 50, 7 * 50, 0, 5);

                    pawnImage.setRow(toSquare.getRank().getValue());
                    pawnImage.setCol(toSquare.getFile().getValue());
                    pawnImage.setX(toSquare.getFile().getValue() * 50);
                    pawnImage.setY((7 - toSquare.getRank().getValue()) * 50);
                    Drawedboard.addPieceAtSquare(pawnImage);
                }
            } else {
                PieceImage piece = Drawedboard.getPieceAtSquare(toRow, toCol);
                piece.setRow(fromRow);
                piece.setCol(fromCol);
                piece.setX(fromCol * 50);
                piece.setY((7 - fromRow) * 50);

                Piece pieceCaptured = game.getPieceAtSquare(toSquare);
                if (pieceCaptured != null) {
                    Image capturedImage = null;
                    try {
                        capturedImage = createCapturedImage(pieceCaptured.getClass());
                    } catch (IOException ex) {
                        System.out.println("Eror reading image");
                    }
                    PieceImage capturedPieceImage = new PieceImage(capturedImage, toCol * 50, (7 - toRow) * 50, toRow, toCol);
                    capturedPieceImage.setRow(toRow);
                    capturedPieceImage.setCol(toCol);
                    capturedPieceImage.setX(toCol * 50);
                    capturedPieceImage.setY((7 - toRow) * 50);
                    Drawedboard.addPieceAtSquare(capturedPieceImage);
                }
                Drawedboard.repaint();
            }
        }
    }

    private Image createCapturedImage(Class classCaptured) throws IOException {
        String resourceName;
        if (classCaptured.equals(Pawn.class)) {
            resourceName = game.getWhoseTurn().equals(Player.WHITE)
                    ? "ressources/BlackPawn.png"
                    : "ressources/WhitePawn.png";
        } else if (classCaptured.equals(Knight.class)) {
            resourceName = game.getWhoseTurn().equals(Player.WHITE)
                    ? "ressources/BlackKnight.png"
                    : "ressources/WhiteKnight.png";
        } else if (classCaptured.equals(Rook.class)) {
            resourceName = game.getWhoseTurn().equals(Player.WHITE)
                    ? "ressources/BlackRook.png"
                    : "ressources/WhiteRook.png";
        } else if (classCaptured.equals(Knight.class)) {
            resourceName = game.getWhoseTurn().equals(Player.WHITE)
                    ? "ressources/BlackKnight.png"
                    : "ressources/WhiteKnight.png";

        } else if (classCaptured.equals(Queen.class)) {
            resourceName = game.getWhoseTurn().equals(Player.WHITE)
                    ? "ressources/BlackQueen.png"
                    : "ressources/WhiteQueen.png";
        } else if (classCaptured.equals(Bishop.class)) {
            resourceName = game.getWhoseTurn().equals(Player.WHITE)
                    ? "ressources/BlackBishop.png"
                    : "ressources/WhiteBishop.png";
        } else {
            return null;
        }
        BufferedImage buffered = ImageIO.read(getClass().getClassLoader().getResource(resourceName));
        return buffered.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
    }
}

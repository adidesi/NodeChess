package com.adidesi95.nodechess.model;

import com.adidesi95.nodechess.MainGame;
import com.adidesi95.nodechess.controller.MultiplatformBoardController;
import com.adidesi95.nodechess.controller.UniplatformBoardController;
import com.adidesi95.nodechess.model.pieces.*;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;


public class Board extends Table {

    private final Tile[][] tiles = new Tile[8][8];
    private final Piece[][] pieces = new Piece[8][8];
    private King whiteKing;
    private King blackKing;
    public int round;
    public Piece selectedPiece;

    public Tile getTileAt(int x, int y) {
        return this.tiles[x][y];
    }

    public Piece getPieceAt(int x, int y) {
        return this.pieces[x][y];
    }

    public Board(boolean connectibility) {
        this.setBounds(0, 0, MainGame.UWIDTH, MainGame.UWIDTH);
        this.setClip(true);
        if(connectibility)
            this.addListener(new MultiplatformBoardController(this));
        else
            this.addListener(new UniplatformBoardController(this));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.tiles[i][j] = new Tile(i, j, ((i + j) % 2) == 0);
                this.addActor(this.tiles[i][j]);
            }
        }
    }

    public void removePieceAt(int x, int y) {
        Piece piece = this.pieces[x][y];


        if (piece != null) {
            piece.remove();
            this.pieces[x][y] = null;
        }
    }

    public void addPiece(Piece piece) {
        this.addActor(piece);
        this.pieces[(int) piece.getX()][(int) piece.getY()] = piece;
    }

    public void relocatePieceAt(int xOld, int yOld, int x, int y) {
        Piece piece = this.pieces[xOld][yOld];

        this.pieces[xOld][yOld] = null;
        piece.setX(x);
        piece.setY(y);
        this.pieces[x][y] = piece;
    }

    public void populate() {
        /* Add pawns. */
        for (int i = 0; i < 8; i++) {
            this.addPiece(new Pawn(i, 1, true));
            this.addPiece(new Pawn(i, 6, false));
        }

		/* Add rooks. */
        this.addPiece(new Rook(0, 0, true));
        this.addPiece(new Rook(7, 0, true));
        this.addPiece(new Rook(0, 7, false));
        this.addPiece(new Rook(7, 7, false));

		/* Add knights. */
        this.addPiece(new Knight(1, 0, true));
        this.addPiece(new Knight(6, 0, true));
        this.addPiece(new Knight(1, 7, false));
        this.addPiece(new Knight(6, 7, false));

		/* Add bishops. */
        this.addPiece(new Bishop(2, 0, true));
        this.addPiece(new Bishop(5, 0, true));
        this.addPiece(new Bishop(2, 7, false));
        this.addPiece(new Bishop(5, 7, false));

		/* Add queens. */
        this.addPiece(new Queen(3, 0, true));
        this.addPiece(new Queen(3, 7, false));

		/* Set and add kings. */
        this.whiteKing = new King(4, 0, true);
        this.blackKing = new King(4, 7, false);
        this.addPiece(this.whiteKing);
        this.addPiece(this.blackKing);
    }

    public boolean isMoveSafe(Piece piece, int x, int y) {
        Piece capturedPiece = this.getPieceAt(x, y);
        King king = piece.isWhite ? this.whiteKing : this.blackKing;
        int xOld = (int) piece.getX();
        int yOld = (int) piece.getY();
        boolean isSafe ;
		/* Simulate move. */
        if (capturedPiece != null) {
            this.removePieceAt(x, y);
        }
        this.relocatePieceAt(xOld, yOld, x, y);
		/* Check king's safety. */
        isSafe = this.isTileSafe((int) king.getX(), (int) king.getY(),king.isWhite);
		/* Revert changes made while checking then return the result. */
        this.relocatePieceAt(x, y, xOld, yOld);
        if (capturedPiece != null) {
            this.addPiece(capturedPiece);
        }
        return isSafe;
    }

    public boolean isTileSafe(int x, int y, boolean forWhite) {
        for (Piece[] row : this.pieces) {
            for (Piece piece : row) {
				/*
				 * If piece belongs to the opponent then check if it threatens
				 * this tile by tracing its possible capture moves.
				 */
                if ((piece != null) && (piece.isWhite != forWhite)) {
                    Array<Tile> threatenedTiles;
                    if (piece.canCaptureWithMove) {
                        threatenedTiles = piece.getValidMoveTiles(this);
                        threatenedTiles.addAll(piece.getCaptureOnlyTiles(this,
                                false));
                    } else {
                        threatenedTiles = piece.getCaptureOnlyTiles(this, false);
                    }
                    for (Tile tile : threatenedTiles) {
                        if ((x == tile.getX()) && (y == tile.getY())) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


}

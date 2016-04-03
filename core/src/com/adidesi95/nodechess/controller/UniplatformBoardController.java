package com.adidesi95.nodechess.controller;

import com.adidesi95.nodechess.model.Board;
import com.adidesi95.nodechess.model.Tile;
import com.adidesi95.nodechess.model.pieces.Piece;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;

/**
 * Created by DESAIs on 2/11/2016.
 */
public class UniplatformBoardController extends ActorGestureListener{
    private final Board board;
    private final Array<Tile> highlightedTiles = new Array<Tile>();

    public UniplatformBoardController(Board board) {
        this.board = board;
    }

    @Override
    public void tap(InputEvent event, float x, float y, int count, int button) {
        Actor target = event.getTarget(); // Tapped actor.
        int tx = (int) target.getX(); // Tapped tile x.
        int ty = (int) target.getY(); // Tapped tile y.

        if (target.getClass().getSuperclass().equals(Piece.class)) {
            Piece piece = (Piece) target;

            if ((((this.board.round % 2) == 0) && piece.isWhite) || (((this.board.round % 2) == 1) && !piece.isWhite)) {
                this.selectPiece(piece);
            } else {
                this.movePiece(this.board.selectedPiece, tx, ty);
            }
        } else {
            this.movePiece(this.board.selectedPiece, tx, ty);
        }
    }

    private void movePiece(Piece piece, int x, int y) {

		/* Check move validity. */
        if ((piece == null) || !this.board.getTileAt(x, y).getHighlighted()) {
            return;
        }

        int xOld = (int) piece.getX();
        int yOld = (int) piece.getY();

		/* Remove highlights. */
        this.removeMoveHighlights();

		/* Capture. */
        if (this.board.getPieceAt(x, y) != null) {
            this.board.removePieceAt(x, y);
        }

		/* Move. */
        this.board.relocatePieceAt(xOld, yOld, x, y);
        this.board.selectedPiece.moved(board);

		/* Deselect and advance round. */
        this.board.selectedPiece = null;
        this.board.round++;
    }

    private void selectPiece(Piece piece) {
        this.removeMoveHighlights();
        this.board.selectedPiece = piece;
        this.addMoveHighlightsForPiece(piece);
    }

    private void removeMoveHighlights() {

        while (this.highlightedTiles.size > 0) {
            this.highlightedTiles.pop().setAttackedAndHighlighted(false);
        }
    }

    private void addMoveHighlightsForPiece(Piece piece) {
        Array<Tile> tiles = piece.getValidMoveTiles(this.board);

        tiles.addAll(piece.getCaptureOnlyTiles(this.board, true));
        Piece otherPiece;
        for (Tile tile : tiles) {
            int tx = (int) tile.getX();
            int ty = (int) tile.getY();

			/* Make sure the move doesn't put the king in check. */
            if (this.board.isMoveSafe(piece, tx, ty)) {
                tile.setIsHighlighted(true);
                otherPiece =board.getPieceAt((int)tile.getX(),(int)tile.getY());
                if(otherPiece!=null&&piece.isWhite!=otherPiece.isWhite)
                    tile.setAttackedAndHighlighted(true);
                this.highlightedTiles.add(tile);

            }
        }
    }

}

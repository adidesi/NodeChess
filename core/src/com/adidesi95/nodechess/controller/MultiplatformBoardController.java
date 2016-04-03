package com.adidesi95.nodechess.controller;

import com.adidesi95.nodechess.model.Board;
import com.adidesi95.nodechess.model.Tile;
import com.adidesi95.nodechess.model.pieces.Piece;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;

import org.json.JSONException;
import org.json.JSONObject;


import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by DESAIs on 2/11/2016.
 */
public class MultiplatformBoardController extends ActorGestureListener{
    private final Board board;
    private final Array<Tile> highlightedTiles = new Array<Tile>();
    private Socket socket;
    private String roomid = "0";
    private int roundSocket;



    public MultiplatformBoardController(Board board) {
        this.board = board;
        connectSocket();
        configSocketEvents();
        this.board.round =roundSocket;
    }

    @Override
    public void tap(InputEvent event, float x, float y, int count, int button) {
        Actor target = event.getTarget(); // Tapped actor.
        int tx = (int) target.getX(); // Tapped tile x.
        int ty = (int) target.getY(); // Tapped tile y.

        if (target.getClass().getSuperclass().equals(Piece.class)) {
            Piece piece = (Piece) target;

            if (((((this.board.round % 2) == 0) && piece.isWhite) || (((this.board.round % 2) == 1) && !piece.isWhite))
                    && this.board.round == roundSocket) {
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
        this.board.round = (this.board.round + 1 ) % 2;

        JSONObject data = new JSONObject();
        try {
            data.put("roomid",roomid);
            data.put("oldX", xOld);
            data.put("oldY",yOld);
            data.put("newX",x);
            data.put("newY",y);
            data.put("piece",piece.getType().toString());
            socket.emit("playerMoved", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void opponentMovePiece(Piece piece, int x, int y, int xOld, int yOld) {

		/* Check move validity. */
        if (piece == null) {
            return;
        }

		/* Capture. */
        if (this.board.getPieceAt(x, y) != null) {
            this.board.removePieceAt(x, y);
        }

		/* Move. */
        this.board.relocatePieceAt(xOld, yOld, x, y);

		/* Deselect and advance round. */
        this.board.round = (this.board.round + 1 ) % 2;

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


    public void connectSocket(){
        try {
            socket= IO.socket("http://localhost:20202");
            socket.connect();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void configSocketEvents() {
        socket.on(Socket.EVENT_CONNECT,new Emitter.Listener(){
            @Override
            public void call(Object... args) {

            }
        }).on("opponentMoved", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    Piece tempPiece = board.getPieceAt( data.getInt("oldX"), data.getInt("oldY"));
                    opponentMovePiece(tempPiece, data.getInt("newX"), data.getInt("newY"),
                             data.getInt("oldX"),  data.getInt("oldY"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).on("joinedRoom", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                Gdx.app.log("NODECHESSROOMJOINED",data.toString());
                try {
                    roomid = data.getString("roomid");
                    roundSocket = data.getInt("turn");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

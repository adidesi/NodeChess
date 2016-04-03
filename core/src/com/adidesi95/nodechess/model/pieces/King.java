/*
 * Copyright 2013 Baris Sencan (baris.sencan@me.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.adidesi95.nodechess.model.pieces;


import com.adidesi95.nodechess.model.Board;
import com.adidesi95.nodechess.model.Move;
import com.adidesi95.nodechess.model.Tile;
import com.badlogic.gdx.utils.Array;

public class King extends Piece {

	private boolean moved;


	public King(int x, int y, boolean isWhite) {
		super(x, y, isWhite, isWhite ? "whiteking" : "blackking");
		super.setType(Pieces.KING);

		/* Add valid moves. */
		this.validMoves.add(new Move(0, 1, false));
		this.validMoves.add(new Move(1, 1, false));
		this.validMoves.add(new Move(1, 0, false));
		this.validMoves.add(new Move(1, -1, false));
		this.validMoves.add(new Move(0, -1, false));
		this.validMoves.add(new Move(-1, -1, false));
		this.validMoves.add(new Move(-1, 0, false));
		this.validMoves.add(new Move(-1, 1, false));
	}

	@Override
	public void moved(Board board) {
		int x = (int) this.getX();

		/* Handle castling move. */
		if (!this.moved) {
			this.moved = true;

			if ((x == 6) || (x == 2)) {
				int y = (int) this.getY();
				Piece rook = (x == 2) ? board.getPieceAt(0, y) : board.getPieceAt(7, y);

				if ((rook == null) || !(rook instanceof Rook)) {
					return;
				}

				if ((x == 2) && (board.getPieceAt(1, y) == null)
						&& (board.getPieceAt(3, y) == null)) {
					board.relocatePieceAt(0, y, 3, y);
				} else if ((x == 6) && (board.getPieceAt(5, y) == null)) {
					board.relocatePieceAt(7, y, 5, y);
				}
			}
		}
	}


	@Override
	public Array<Tile> getValidMoveTiles(Board board) {
		Array<Tile> tiles = super.getValidMoveTiles(board);
		int x = (int) this.getX();
		int y = (int) this.getY();

		//TODO CASTLING
		/* Show castling moves if available. */
		if (!this.moved) {

			if ((board.getPieceAt(x + 1, y) == null)
					&& (board.getPieceAt(x + 2, y) == null)
					&& (board.getPieceAt(x + 3,y)).getType().equals(Pieces.ROOK)
					&& (board.getPieceAt(x + 3,y)).getMoved()) {
				this.validMoves.add(new Move(2, 0, false));
			}

			if ((board.getPieceAt(x - 1, y) == null)
					&& (board.getPieceAt(x - 2, y) == null)
					&& (board.getPieceAt(x - 3, y) == null)
					&& (board.getPieceAt(x - 4,y).getType().equals(Pieces.ROOK))
					&& (board.getPieceAt(x - 4,y).getMoved())) {
				this.validMoves.add(new Move(-2, 0, false));
			}
		}
		return tiles;
	}


}

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


import com.adidesi95.nodechess.model.Move;

public class Queen extends Piece {

	public Queen(int x, int y, boolean isWhite) {
		super(x, y, isWhite, isWhite ? "whitequeen" : "blackqueen");
		super.setType(Pieces.QUEEN);

		/* Add valid moves. */
		this.validMoves.add(new Move(0, 1, true));
		this.validMoves.add(new Move(1, 1, true));
		this.validMoves.add(new Move(1, 0, true));
		this.validMoves.add(new Move(1, -1, true));
		this.validMoves.add(new Move(0, -1, true));
		this.validMoves.add(new Move(-1, -1, true));
		this.validMoves.add(new Move(-1, 0, true));
		this.validMoves.add(new Move(-1, 1, true));
	}
}

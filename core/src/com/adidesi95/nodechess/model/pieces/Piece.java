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

import com.adidesi95.nodechess.Assets;
import com.adidesi95.nodechess.model.Board;
import com.adidesi95.nodechess.model.Move;
import com.adidesi95.nodechess.model.Tile;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Piece extends Actor {

	public boolean isWhite;
	public boolean canCaptureWithMove = true;
	private Pieces type;
	private boolean moved = false;

	protected Array<Move> validMoves = new Array<Move>();

	protected Array<Move> captureOnlyMoves = new Array<Move>();

	private final TextureRegion textureRegion;

	public Piece(int x, int y, boolean isWhite, String regionName) {
		this.setBounds(x, y, 1, 1);
		this.isWhite = isWhite;
		this.textureRegion = Assets.gameAtlas.findRegion(regionName);
	}

	public Array<Tile> getValidMoveTiles(Board board) {
		Array<Tile> tiles = new Array<Tile>();
		int x = (int) this.getX(), y = (int) this.getY();
		boolean isLooping;

		for (Move move : this.validMoves) {
			isLooping = true;
			for (int i = 1; isLooping; i++) {
				int tx = x + (move.xOffset * i);
				//TODO the player have opposite sittings. Think bout it!
				int ty = y + ((this.isWhite ? move.yOffset : -move.yOffset) * i);
				if ((tx > -1) && (tx < 8) && (ty > -1) && (ty < 8)) {
					Tile tile = board.getTileAt(tx, ty);
					Piece otherPiece = board.getPieceAt(tx, ty);
					if (otherPiece != null) {
						if (otherPiece.isWhite != this.isWhite && this.canCaptureWithMove) {
							tiles.add(tile);
						}
						isLooping = false;
					} else {
						tiles.add(tile);
					}
				} else {
					isLooping = false;
				}
				if (!move.isVector) {
					isLooping = false;
				}
			}
		}
		return tiles;
	}

	public Array<Tile> getCaptureOnlyTiles(Board board, boolean check) {
		Array<Tile> tiles = new Array<Tile>();
		int x = (int) this.getX();
		int y = (int) this.getY();

		for (Move move : this.captureOnlyMoves) {
			int tx = x + move.xOffset; // Tile x.
			int ty = y + (this.isWhite ? move.yOffset : -move.yOffset); // Tile
																		// y.
			if ((tx > -1) && (tx < 8) && (ty > -1) && (ty < 8)) {
				Tile tile = board.getTileAt(tx, ty);
				Piece otherPiece = board.getPieceAt(tx, ty);

				if (!check
						|| ((otherPiece != null) && (otherPiece.isWhite != this.isWhite))) {
					tiles.add(tile);
				}
			}
		}
		return tiles;
	}

	public void moved(Board board) {
		if(!moved)
			moved= true;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(this.textureRegion, this.getX(), this.getY(), 1, 1);
	}

	public Pieces getType(){
		return type;
	}

	public void setType(Pieces type) {
		this.type = type;
	}

	public boolean getMoved(){
		return moved;
	}
}

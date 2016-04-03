package com.adidesi95.nodechess.model;

import com.adidesi95.nodechess.Assets;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by DESAIs on 2/11/2016.
 */
public class Tile extends Actor {

    private final TextureRegion tileTexture;
    private final TextureRegion moveTileTexture;
    private final TextureRegion attackTileTexture;
    private final TextureRegion promoteTileTexture;

    private boolean highlighted, attacked, promoted;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(highlighted){
            if(promoted)
                batch.draw(this.promoteTileTexture, this.getX(), this.getY(), 1, 1);
            else if(attacked)
                batch.draw(this.attackTileTexture, this.getX(), this.getY(), 1, 1);
            else
                batch.draw(this.moveTileTexture, this.getX(), this.getY(), 1, 1);
        }else{
            batch.draw(this.tileTexture, this.getX(), this.getY(), 1, 1);
        }
    }

    public Tile(int x,int y,boolean isWhite){
        this.setBounds(x, y, 1, 1);

        moveTileTexture = Assets.gameAtlas.findRegion("tilemove");
        attackTileTexture = Assets.gameAtlas.findRegion("tileattack");
        promoteTileTexture = Assets.gameAtlas.findRegion("tilepromote");
        if(isWhite)
            tileTexture = Assets.gameAtlas.findRegion("tilewhite");
        else
            tileTexture = Assets.gameAtlas.findRegion("tileblack");
    }

    public void dispose(){
        tileTexture.getTexture().dispose();
        attackTileTexture.getTexture().dispose();
        promoteTileTexture.getTexture().dispose();
        moveTileTexture.getTexture().dispose();
    }

    public void setPromotedAndHighlighted(boolean isPromoted) {
        this.promoted = isPromoted;
        this.highlighted = isPromoted;
    }

    public void setAttackedAndHighlighted(boolean isAttacked) {
        this.attacked = isAttacked;
        this.highlighted = isAttacked;
    }

    public void setIsHighlighted(boolean isHighlighted) {
        this.highlighted = isHighlighted;
    }


    public boolean getHighlighted() {
        return highlighted;
    }
}

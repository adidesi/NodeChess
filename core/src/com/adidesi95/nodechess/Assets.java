package com.adidesi95.nodechess;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by DESAIs on 2/11/2016.
 */
public class Assets {

    public static Skin skin;
    public static Music menuMusic;
    public static TextureAtlas gameAtlas;

    public static void loadGame(){
        gameAtlas = new TextureAtlas("atlas/nodeChessAtlas.pack");
    }

    public static void disposeGame() {
        gameAtlas.dispose();
    }
}

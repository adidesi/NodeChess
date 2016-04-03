package com.adidesi95.nodechess.screens;

import com.adidesi95.nodechess.Assets;
import com.adidesi95.nodechess.model.Board;
import com.adidesi95.nodechess.view.UniplatformGameScreenRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 * Created by DESAIs on 2/11/2016.
 */
public class UniplatformGameScreen implements Screen {


    private boolean connectibility = false;;

    private UniplatformGameScreenRenderer renderer;
    @Override
    public void show() {
        Board board; // Can't call the constructor here. Assets have to be loaded first.

        Assets.loadGame();
        board = new Board(connectibility);
        board.populate();
        this.renderer = new UniplatformGameScreenRenderer(board);
        this.renderer.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        this.renderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        this.renderer.setSize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        this.renderer.dispose();
        Assets.disposeGame();
    }

    @Override
    public void dispose() {

    }
}

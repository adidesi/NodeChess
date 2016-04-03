package com.adidesi95.nodechess.screens;

import com.adidesi95.nodechess.Assets;
import com.adidesi95.nodechess.view.MenuScreenRenderer;
import com.badlogic.gdx.Screen;

/**
 * Created by DESAIs on 2/11/2016.
 */
public class MenuScreen implements Screen {

    private MenuScreenRenderer renderer;

    @Override
    public void show() {
        Assets.loadGame();
        this.renderer = new MenuScreenRenderer();
    }

    @Override
    public void render(float delta) {
        this.renderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        renderer.dispose();
        Assets.disposeGame();
    }
}

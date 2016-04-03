package com.adidesi95.nodechess.view;

import com.adidesi95.nodechess.model.Board;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;


/**
 * Created by DESAIs on 2/11/2016.
 */
public class MultiplatformGameScreenRenderer implements Renderer {
    private final Stage stage= new Stage(new FitViewport(8, 8));


    public MultiplatformGameScreenRenderer(Board board) {
        Gdx.input.setInputProcessor(this.stage);
        this.stage.addActor(board);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.draw();
    }

    @Override
    public void setSize(int width, int height) {
        this.stage.getViewport().update(width, height, false);
        Gdx.graphics.requestRendering();
    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }
}

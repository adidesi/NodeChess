package com.adidesi95.nodechess.view;

import com.adidesi95.nodechess.Assets;
import com.adidesi95.nodechess.MainGame;
import com.adidesi95.nodechess.screens.MultiplatformGameScreen;
import com.adidesi95.nodechess.screens.UniplatformGameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by DESAIs on 2/11/2016.
 */
public class MenuScreenRenderer implements Renderer{
    private TextButton buttonMultiplayer, buttonSinglePlayer, buttonExit, buttonDebug;
    private Stage stage;
    private Skin skin;
    private Table table;
    private BitmapFont white;

    public MenuScreenRenderer() {

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Assets.gameAtlas);
        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        white = new BitmapFont(Gdx.files.internal("myFont.fnt"),false);

        TextButton.TextButtonStyle buttonExitStyle = new TextButton.TextButtonStyle();
        buttonExitStyle.up = skin.getDrawable("tileattack");
        buttonExitStyle.down = skin.getDrawable("tilewhite");
        buttonExitStyle.checkedOffsetX = 1;
        buttonExitStyle.checkedOffsetY = -1;
        buttonExitStyle.font = white;
        buttonExit = new TextButton("EXIT GAME",buttonExitStyle);
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                Gdx.app.exit();
                            }
                        },
                        100
                );
            }
        });

        TextButton.TextButtonStyle buttonMultiplayerStyle = new TextButton.TextButtonStyle();
        buttonMultiplayerStyle.down = skin.getDrawable("tilewhite");
        buttonMultiplayerStyle.checkedOffsetX = 1;
        buttonMultiplayerStyle.checkedOffsetY = -1;
        buttonMultiplayerStyle.font = white;
        buttonMultiplayerStyle.up = skin.getDrawable("tilepromote");
        buttonMultiplayer = new TextButton("MULTI PLATFORM",buttonMultiplayerStyle);
        buttonMultiplayer.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.game.setScreen(new MultiplatformGameScreen());
            }
        });

        TextButton.TextButtonStyle buttonSinglePlayerStyle = new TextButton.TextButtonStyle();
        buttonSinglePlayerStyle.up = skin.getDrawable("tilemove");
        buttonSinglePlayerStyle.down = skin.getDrawable("tilewhite");
        buttonSinglePlayerStyle.checkedOffsetX = 1;
        buttonSinglePlayerStyle.checkedOffsetY = -1;
        buttonSinglePlayerStyle.font = white;
        buttonSinglePlayer = new TextButton("SINGLE PLATFORM",buttonSinglePlayerStyle);
        buttonSinglePlayer.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.game.setScreen(new UniplatformGameScreen());
            }
        });


        table.add(buttonSinglePlayer);
        table.row();
        table.add(buttonMultiplayer);
        table.row();
        table.add(buttonExit);
        table.row();
        /*table.add(buttonDebug);
        table.row();*/
        stage.addActor(table);

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
        skin.dispose();
        white.dispose();
    }
}

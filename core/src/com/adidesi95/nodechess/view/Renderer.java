package com.adidesi95.nodechess.view;

/**
 * Created by DESAIs on 2/11/2016.
 */
public interface Renderer {

    void render(float deltaTime);
    void setSize(int width, int height);
    void dispose();

}

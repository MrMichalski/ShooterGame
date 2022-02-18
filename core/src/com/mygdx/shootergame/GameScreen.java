package com.mygdx.shootergame;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {


    //Viewing
    private Camera camera;
    private Viewport viewport;

    //Graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private TextureRegion background, playerShipTextureRegion, enemyShipTextureRegion, PlayerLaserTextureRegion, EnemyLaserTextureRegion, shieldTextureRegion, backgroundTextureRegion;

    //Maths
    private int backgroundOffset;

    //Game parameters
    private final int screenWidth = 128;
    private final int screenHeight = 256;




    GameScreen() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(screenWidth, screenHeight, camera);

        textureAtlas = new TextureAtlas("textures.atlas");
        background = textureAtlas.findRegion("background");
        backgroundOffset = 0;

        batch = new SpriteBatch();
    }




    @Override
    public void render(float delta) {
        backgroundOffset++;
        if (backgroundOffset == screenHeight) {
            backgroundOffset = 0;
        }
        batch.begin();
        batch.draw(background, 0, backgroundOffset, screenWidth, screenHeight);
        batch.draw(background, 0, backgroundOffset - screenHeight, screenWidth, screenHeight);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
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
    public void show() {

    }

    @Override
    public void dispose() {

    }
}

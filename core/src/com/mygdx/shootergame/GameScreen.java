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
    private TextureRegion playerShipTextureRegion, enemyShipTextureRegion, playerLaserTextureRegion, enemyLaserTextureRegion, playerShieldTextureRegion, enemyShieldTextureRegion, backgroundTextureRegion;


    //Maths
    private int backgroundOffset;

    //Game parameters
    private final int screenWidth = 480;
    private final int screenHeight = 800;

    //Game objects
    private Ship playerShip;
    private Ship enemyShip;




    GameScreen() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(screenWidth, screenHeight, camera);

        textureAtlas = new TextureAtlas("textures.atlas");
        backgroundTextureRegion = textureAtlas.findRegion("background");
        playerShieldTextureRegion = textureAtlas.findRegion("shield1");
        enemyShieldTextureRegion = textureAtlas.findRegion("shield1");
        enemyShieldTextureRegion.flip(true, true);
        playerShipTextureRegion = textureAtlas.findRegion("playerShip1_green");
        enemyShipTextureRegion = textureAtlas.findRegion("playerShip3_red");
        playerLaserTextureRegion = textureAtlas.findRegion("laserBlue01");
        enemyLaserTextureRegion = textureAtlas.findRegion("laserRed01");


        playerShip = new Ship(screenWidth / 2, screenHeight *1/5, 50, 50, 10, 5, playerShipTextureRegion, playerShieldTextureRegion);
        enemyShip = new Ship(screenWidth / 2, screenHeight *4/5, 30, 30, 10, 3, enemyShipTextureRegion, enemyShieldTextureRegion);


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
        batch.draw(backgroundTextureRegion, 0, backgroundOffset, screenWidth, screenHeight);
        batch.draw(backgroundTextureRegion, 0, backgroundOffset - screenHeight, screenWidth, screenHeight);

        //ships
        playerShip.draw(batch);
        enemyShip.draw(batch);


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

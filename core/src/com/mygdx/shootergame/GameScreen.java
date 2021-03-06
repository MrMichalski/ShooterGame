package com.mygdx.shootergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.ListIterator;

public class GameScreen implements Screen {

    //Viewing
    private Camera camera;
    private Viewport viewport;

    //Graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private TextureRegion playerShipTextureRegion, enemyShipTextureRegion, playerLaserTextureRegion, enemyLaserTextureRegion, playerShieldTextureRegion, enemyShieldTextureRegion, backgroundTextureRegion;
    BitmapFont font;

    //Maths
    private int backgroundOffset;
    private float timeBetweenEnemySpawns = 3f;
    private int enemyShipsSpawnCount = 2;
    private int maxEnemyShipsCount = 3;
    private boolean limitEnemyShipSpawn = true;
    private float enemySpawnTimer = 3;

    //Game parameters
    private final int screenWidth = 480;
    private final int screenHeight = 800;
    private final float touchMovementThreshold = 5f;
    private boolean exitOnGameOver = false;

    //Game objects
    private PlayerShip playerShip;
    private LinkedList<EnemyShip> enemyShipList;
    private LinkedList<Laser> playerLaserList;
    private LinkedList<Laser> enemyLaserList;




    GameScreen() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(screenWidth, screenHeight, camera);

        textureAtlas = new TextureAtlas("textures.atlas");
        backgroundTextureRegion = textureAtlas.findRegion("background");
        playerShieldTextureRegion = textureAtlas.findRegion("shield2");
        enemyShieldTextureRegion = textureAtlas.findRegion("shield1");
        enemyShieldTextureRegion.flip(true, true);
        playerShipTextureRegion = textureAtlas.findRegion("playerShip1_green");
        enemyShipTextureRegion = textureAtlas.findRegion("playerShip3_red");
        playerLaserTextureRegion = textureAtlas.findRegion("laserBlue01");
        enemyLaserTextureRegion = textureAtlas.findRegion("laserRed01");
        enemyLaserTextureRegion.flip(true, true);

        font = new BitmapFont();

        enemyShipList = new LinkedList<>();

        playerShip = new PlayerShip(screenWidth / 2, screenHeight *1/5, 50, 50, 350, 5, playerShipTextureRegion, playerShieldTextureRegion, 0.2f ,4, 14, 300,playerLaserTextureRegion);


        playerLaserList = new LinkedList<>();
        enemyLaserList = new LinkedList<>();

        backgroundOffset = 0;

        batch = new SpriteBatch();
    }




    @Override
    public void render(float deltaTime) {

        batch.begin();

        renderBackground();

        detectInput(deltaTime);

        spawnEnemyShips(deltaTime);

        //ships
        playerShip.update(deltaTime);
        playerShip.draw(batch);

        ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();
        while (enemyShipListIterator.hasNext()) {
            EnemyShip enemyShip = enemyShipListIterator.next();
            moveEnemies(enemyShip, deltaTime);
            enemyShip.draw(batch);
            enemyShip.update(deltaTime);
        }

        //lasers
        renderLasers(deltaTime);

        //Collision detection
        detectCollisions();

        font.draw(batch,stats(), 5, 15);
        batch.end();
    }

    private String stats() {
        Integer shield = playerShip.shield;

        return  "Pozosta??o " + shield.toString() + " warstw tarczy.";
    }

    private void spawnEnemyShips(float deltaTime) {
        enemySpawnTimer += deltaTime;
        if (enemySpawnTimer > timeBetweenEnemySpawns) {
                if(limitEnemyShipSpawn == true) {
                        for (int i = 0; i < enemyShipsSpawnCount; i++) {
                            if(enemyShipList.size() < maxEnemyShipsCount) {
                                enemyShipList.add(new EnemyShip(screenWidth / 2, screenHeight *4/5, 30, 30, 100, 2, enemyShipTextureRegion, enemyShieldTextureRegion, 0.5f, 3, 10, 200 ,enemyLaserTextureRegion));
                            }
                        }
                }
                else {
                    for (int i = 0; i < maxEnemyShipsCount; i++) {
                        enemyShipList.add(new EnemyShip(screenWidth / 2, screenHeight *4/5, 30, 30, 100, 3, enemyShipTextureRegion, enemyShieldTextureRegion, 0.5f, 3, 10, 200 ,enemyLaserTextureRegion));
                    }
                }
            enemySpawnTimer = 0;
        }
    }

    void moveEnemies(EnemyShip enemyShip, float deltaTime) {
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -enemyShip.boundingBox.x;
        downLimit = (float)screenHeight/2 - enemyShip.boundingBox.y;
        rightLimit = screenWidth - enemyShip.boundingBox.x - enemyShip.boundingBox.width;
        upLimit = screenHeight - enemyShip.boundingBox.y - enemyShip.boundingBox.height;

        float xMove  = enemyShip.getDirectionVector().x * enemyShip.movementSpeed * deltaTime;
        float yMove  = enemyShip.getDirectionVector().y * enemyShip.movementSpeed * deltaTime;

        if(xMove > 0 ) {
            xMove = Math.min(xMove, rightLimit);
        }
        else {
            xMove = Math.max(xMove, leftLimit);
        }

        if(yMove > 0 ) {
            yMove = Math.min(yMove, upLimit);
        }
        else {
            yMove = Math.max(yMove, downLimit);
        }

        enemyShip.translate(xMove, yMove);
    }

    void detectCollisions() {
        ListIterator<Laser> laserListIterator = playerLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            //Dla ka??dego lasera przelatuje po li??cie statk??w przeciwnika i sprawdza czy jest kolizja
            ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();
            while (enemyShipListIterator.hasNext()) {
                EnemyShip enemyShip = enemyShipListIterator.next();
                if (enemyShip.intersectsWithRectangle(laser.boundingBox)) {
                    //tu s?? efekty uderzenia oponenta przez laser gracza, przekazuejmy laser gdyby??my chcieli zrobi?? kiedy?? wi??cej rodzaj??w laser??w
                    if(enemyShip.hitAndCheckIfDestroyed(laser)) {
                        enemyShipListIterator.remove();
                    }
                    laserListIterator.remove();
                    break;
                }
            }

        }
        laserListIterator = enemyLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            if (playerShip.intersectsWithRectangle(laser.boundingBox)) {
                //tu s?? efekty uderzenia gracza przez laser oponenta
                if(playerShip.hitAndCheckIfDestroyed(laser)) {
                    //tu b??dzie to co si?? stanie jak zginiemy
                    if(exitOnGameOver) {
                        System.exit(-1);
                    }
                }
                laserListIterator.remove();
            }
        }
    }

    private void renderBackground() {
        backgroundOffset--;
        if (backgroundOffset == screenHeight*(-1)) {
            backgroundOffset = 0;
        }
        batch.draw(backgroundTextureRegion, 0, backgroundOffset, screenWidth, screenHeight);
        batch.draw(backgroundTextureRegion, 0, backgroundOffset + screenHeight, screenWidth, screenHeight);
    }

    private void detectInput(float deltaTime) {
        //Keyboard input

        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -playerShip.boundingBox.x;
        downLimit = -playerShip.boundingBox.y;
        rightLimit = screenWidth - playerShip.boundingBox.x - playerShip.boundingBox.width;
        upLimit = (float)screenHeight/2 - playerShip.boundingBox.y - playerShip.boundingBox.height;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightLimit > 0) {
            //float xChange = playerShip.movementSpeed*deltaTime;
            //xChange = Math.min(xChange, rightLimit);
            //playerShip.translate(xChange, 0f);
            playerShip.translate(Math.min(playerShip.movementSpeed*deltaTime, rightLimit), 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && upLimit > 0) {
            playerShip.translate(0f, Math.min(playerShip.movementSpeed*deltaTime, upLimit));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftLimit < 0) {
            playerShip.translate(Math.max(-playerShip.movementSpeed*deltaTime, leftLimit), 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && downLimit < 0) {
            playerShip.translate(0f, Math.max(-playerShip.movementSpeed*deltaTime, downLimit));
        }

        //Touch input
        if(Gdx.input.isTouched()) {
            //Touch position in pixels
            float xPixels = Gdx.input.getX();
            float yPixels = Gdx.input.getY();

            //Converting do world position
            Vector2 touchPoint = new Vector2(xPixels, yPixels);
            touchPoint = viewport.unproject(touchPoint);
            //Metoda unproject konwertuje po??o??enie w pixelach na po??o??enie w lokalnych jednostkach wielko??ci

            //Calculating x and y diff
            Vector2 playerShipCentre = new Vector2(playerShip.boundingBox.x + playerShip.boundingBox.width/2, playerShip.boundingBox.y + playerShip.boundingBox.height/2);

            float touchDistance = touchPoint.dst(playerShipCentre);

            if(touchDistance > touchMovementThreshold) {
                float xTouchDifference = touchPoint.x - playerShipCentre.x;
                float yTouchDifference = touchPoint.y - playerShipCentre.y;

                float xMove  = xTouchDifference / touchDistance * playerShip.movementSpeed * deltaTime;
                float yMove  = yTouchDifference / touchDistance * playerShip.movementSpeed * deltaTime;

                if(xMove > 0 ) {
                    xMove = Math.min(xMove, rightLimit);
                }
                else {
                    xMove = Math.max(xMove, leftLimit);
                }

                if(yMove > 0 ) {
                    yMove = Math.min(yMove, upLimit);
                }
                else {
                    yMove = Math.max(yMove, downLimit);
                }

                playerShip.translate(xMove, yMove);

            }



        }


    }

    void renderLasers(float deltaTime) {
        //Creating new lasers:

        //PlayerLasers
        if(playerShip.canFireLaser()) {
            Laser[] lasers = playerShip.fireLaser();
            for (Laser laser: lasers) {
                playerLaserList.add(laser);
            }
        }
        //EnemyLasers
        ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();
        while (enemyShipListIterator.hasNext()) {
            EnemyShip enemyShip = enemyShipListIterator.next();
            if(enemyShip.canFireLaser()) {
                Laser[] lasers = enemyShip.fireLaser();
                for (Laser l: lasers) {
                    enemyLaserList.add(l);
                }
            }
        }



        //Draw and deleting lasers:

        //PlayerLasers
        ListIterator<Laser> iterator = playerLaserList.listIterator();
        while(iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y += laser.movementSpeed * deltaTime;
            if(laser.boundingBox.y > screenHeight) {
                iterator.remove();
            }
        }

        //EnemyLasers
        iterator = enemyLaserList.listIterator();
        while(iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y -= laser.movementSpeed * deltaTime;
            if(laser.boundingBox.y + laser.boundingBox.height < 0) {
                iterator.remove();
            }
        }
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

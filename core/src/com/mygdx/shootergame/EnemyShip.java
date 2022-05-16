package com.mygdx.shootergame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Stack;

public class EnemyShip extends Ship{

    Vector2 directionVector;
    float timeSinceLastDirectionChange;
    float directionChangeFrequency = 0.5f;

    public EnemyShip(int xCentre, int yCentre, int width, int height, int movementSpeed, int shield, TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion, float timeBetweenShoots, float laserWidth, float laserHeight, float laserMovementSpeed, TextureRegion laserTextureRegion) {
        super(xCentre, yCentre, width, height, movementSpeed, shield, shipTextureRegion, shieldTextureRegion, timeBetweenShoots, laserWidth, laserHeight, laserMovementSpeed, laserTextureRegion);

        directionVector = new Vector2(0, -1);
    }

    public Vector2 getDirectionVector() {
        return directionVector;
    }

    private void randomizeDriectionVector() {
        double bearing = Main.random.nextDouble()* 6.283185; //Ta liczba to 2*Math.PI
        directionVector.x = (float)Math.sin(bearing);
        directionVector.y = (float)Math.cos(bearing);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeSinceLastDirectionChange += deltaTime;
        if(timeSinceLastDirectionChange > directionChangeFrequency) {
            randomizeDriectionVector();
            timeSinceLastDirectionChange -= directionChangeFrequency;
        }
    }

    @Override
    public Laser[] fireLaser() {
        Laser[] laser = new Laser[2];

        laser[0] = new Laser(boundingBox.x + boundingBox.width * 0.07f, boundingBox.y, laserWidth, laserHeight, laserMovementSpeed, laserTextureRegion);
        laser[1] = new Laser(boundingBox.x + boundingBox.width * 0.93f,boundingBox.y, laserWidth, laserHeight, laserMovementSpeed, laserTextureRegion);
        timeSinceLastShot = 0;
        return laser;
    }
}

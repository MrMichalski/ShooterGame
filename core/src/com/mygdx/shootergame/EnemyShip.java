package com.mygdx.shootergame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyShip extends Ship{

    public EnemyShip(int xCentre, int yCentre, int width, int height, int movementSpeed, int shield, TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion, float timeBetweenShoots, float laserWidth, float laserHeight, float laserMovementSpeed, TextureRegion laserTextureRegion) {
        super(xCentre, yCentre, width, height, movementSpeed, shield, shipTextureRegion, shieldTextureRegion, timeBetweenShoots, laserWidth, laserHeight, laserMovementSpeed, laserTextureRegion);
    }

    @Override
    public Laser[] fireLaser() {
        Laser[] laser = new Laser[2];

        laser[0] = new Laser(xPosition + width*0.07f,yPosition, laserWidth, laserHeight, laserMovementSpeed, laserTextureRegion);
        laser[1] = new Laser(xPosition + width*0.93f,yPosition, laserWidth, laserHeight, laserMovementSpeed, laserTextureRegion);
        timeSinceLastShot = 0;
        return laser;
    }
}

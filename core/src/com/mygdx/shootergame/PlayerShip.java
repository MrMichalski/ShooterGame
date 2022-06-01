package com.mygdx.shootergame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.shootergame.Laser;
import com.mygdx.shootergame.Ship;

public class PlayerShip extends Ship {
    public PlayerShip(int xCentre, int yCentre, int width, int height, int movementSpeed, int shield, TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion, float timeBetweenShoots, float laserWidth, float laserHeight, float laserMovementSpeed, TextureRegion laserTextureRegion) {
        super(xCentre, yCentre, width, height, movementSpeed, shield, shipTextureRegion, shieldTextureRegion, timeBetweenShoots, laserWidth, laserHeight, laserMovementSpeed, laserTextureRegion);
    }

    @Override
    public Laser[] fireLaser() {
        Laser[] laser = new Laser[2];

        laser[0] = new Laser(boundingBox.x + boundingBox.width * 0.1f, boundingBox.y + boundingBox.height * 0.45f, laserWidth, laserHeight, laserMovementSpeed, laserTextureRegion);
        laser[1] = new Laser(boundingBox.x + boundingBox.width * 0.9f, boundingBox.y + boundingBox.height * 0.45f, laserWidth, laserHeight, laserMovementSpeed, laserTextureRegion);
        timeSinceLastShot = 0;
        return laser;
    }
}

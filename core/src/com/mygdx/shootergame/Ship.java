package com.mygdx.shootergame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Ship {

    //properties
    int movementSpeed;
    int shield;

    //Size and place
    int xPosition, width;
    int yPosition, height;

    //Graphics
    TextureRegion shipTextureRegion;
    TextureRegion shieldTextureRegion;
    TextureRegion laserTextureRegion;

    //Information about laser
    float laserWidth, laserHeight;
    float laserMovementSpeed;
    float timeBetweenShoots;
    float timeSinceLastShot = 0;

    public Ship( int xCentre, int yCentre,int width, int height, int movementSpeed, int shield,  TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion, float timeBetweenShoots, float laserWidth, float laserHeight, float laserMovementSpeed, TextureRegion laserTextureRegion) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;
        this.xPosition = xCentre - width/2;
        this.width = width;
        this.yPosition = yCentre - height/2;
        this.height = height;
        this.shipTextureRegion = shipTextureRegion;
        this.shieldTextureRegion = shieldTextureRegion;
        this.timeBetweenShoots = timeBetweenShoots;
        this.laserWidth= laserWidth;
        this.laserHeight = laserHeight;
        this.laserMovementSpeed = laserMovementSpeed;
        this.laserTextureRegion = laserTextureRegion;
    }


    public void update(float deltaTime) {
        //timeSinceLastShot = timeSinceLastShot + deltaTime;
        timeSinceLastShot += deltaTime;
    }

    public boolean canFireLaser() {
        return (timeSinceLastShot - timeBetweenShoots >= 0);
    }

    public abstract Laser[] fireLaser();

public void draw(Batch batch) {
        batch.draw(shipTextureRegion, xPosition, yPosition, width, height);
        if(shield >= 0) {
            batch.draw(shieldTextureRegion, xPosition - ((width * 3/2 - width)/2), yPosition, width * 3/2, height );
        }
}
}

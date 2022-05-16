package com.mygdx.shootergame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Ship {

    //properties
    int movementSpeed;
    int shield;

    //Size and place
    //Xposition, yposition, width and heigth used to be here
    Rectangle boundingBox;

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

        this.boundingBox = new Rectangle(xCentre - width/2, yCentre - height/2, width, height);

        this.shipTextureRegion = shipTextureRegion;
        this.shieldTextureRegion = shieldTextureRegion;
        this.laserTextureRegion = laserTextureRegion;

        this.timeBetweenShoots = timeBetweenShoots;
        this.laserWidth= laserWidth;
        this.laserHeight = laserHeight;
        this.laserMovementSpeed = laserMovementSpeed;

    }


    public void update(float deltaTime) {
        //timeSinceLastShot = timeSinceLastShot + deltaTime;
        timeSinceLastShot += deltaTime;
    }

    public boolean canFireLaser() {
        return (timeSinceLastShot - timeBetweenShoots >= 0);
    }

    public abstract Laser[] fireLaser();

    public boolean intersectsWithRectangle(Rectangle rectangle) {
        return boundingBox.overlaps(rectangle);
    }

    public boolean hitAndCheckIfDestroyed(Laser laser) {
        if(shield > 0) {
            shield--;
            return false;
        }
        else {
            shield--;
            return true;
        }
    }

    public void translate(float xChange, float yChange) {
        boundingBox.setPosition(boundingBox.x + xChange, boundingBox.y + yChange);
    }

public void draw(Batch batch) {
        batch.draw(shipTextureRegion, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        if(shield > 0) {
            batch.draw(shieldTextureRegion, boundingBox.x - ((boundingBox.width * 3/2 - boundingBox.width)/2), boundingBox.y, boundingBox.width * 3/2, boundingBox.height );
        }
}
}

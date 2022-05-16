package com.mygdx.shootergame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Laser {

    Rectangle boundingBox;
    float movementSpeed;
    TextureRegion textureRegion;

    public Laser(float xCentre, float yPosition, float width, float height, float movementSpeed, TextureRegion textureRegion) {
        this.boundingBox = new Rectangle(xCentre - width/2, yPosition - height/2, width, height);
        this.movementSpeed = movementSpeed;
        this.textureRegion = textureRegion;
    }

    public void draw(Batch batch) {
        batch.draw(textureRegion, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

    //Rectangle getBoundingBox() {
    //    return new Rectangle(xPosition, yPosition, width, height);
    //}
}

package com.mygdx.shootergame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Laser {

    float xPosition, yPosition;
    float width, height;
    float movementSpeed;
    TextureRegion textureRegion;

    public Laser(float xCentre, float yPosition, float width, float height, float movementSpeed, TextureRegion textureRegion) {
        this.xPosition = xCentre - width/2;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.movementSpeed = movementSpeed;
        this.textureRegion = textureRegion;
    }

    public void draw(Batch batch) {
        batch.draw(textureRegion, xPosition, yPosition, width, height);
    }
}

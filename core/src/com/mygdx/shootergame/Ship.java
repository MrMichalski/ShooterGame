package com.mygdx.shootergame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ship {

    //properties
    int movementSpeed;
    int shield;

    //Size and place
    int xPosition, xSize;
    int yPosition, ySize;

    //Graphics
    TextureRegion shipTextureRegion;
    TextureRegion shieldTextureRegion;

    public Ship( int xCentre, int yCentre,int xSize, int ySize, int movementSpeed, int shield,  TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;
        this.xPosition = xCentre - xSize/2;
        this.xSize = xSize;
        this.yPosition = yCentre - ySize/2;
        this.ySize = ySize;
        this.shipTextureRegion = shipTextureRegion;
        this.shieldTextureRegion = shieldTextureRegion;
    }

public void draw(Batch batch) {
        batch.draw(shipTextureRegion, xPosition, yPosition, xSize, ySize);
        if(shield >= 0) {
            batch.draw(shieldTextureRegion, xPosition, yPosition + ySize / 4, xSize * 5/4, ySize );
        }
}
}

package com.tlg.view;

import com.tlg.view.GuiBuild;

import java.awt.image.BufferedImage;

public class Tileset {
    private BufferedImage[] tiles;
    private int tileWidth;
    private int tileHeight;

    public Tileset(BufferedImage[] tiles, int tileWidth, int tileHeight) {
        this.tiles = tiles;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public BufferedImage getTile(int index) {
        if (index < 0 || index >= tiles.length) {
            throw new IllegalArgumentException("Invalid tile index: " + index);
        }
        return tiles[index];
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public BufferedImage getTilesetImage() {
        int numTiles = tiles.length;
        int numTilesX = (int) Math.ceil(Math.sqrt(numTiles));
        int numTilesY = (int) Math.ceil((double) numTiles / numTilesX);

        int tilesetWidth = numTilesX * tileWidth;
        int tilesetHeight = numTilesY * tileHeight;

        BufferedImage tilesetImage = new BufferedImage(tilesetWidth, tilesetHeight, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < numTiles; i++) {
            int tileX = i % numTilesX;
            int tileY = i / numTilesX;

            int offsetX = tileX * tileWidth;
            int offsetY = tileY * tileHeight;

            BufferedImage tileImage = tiles[i];
            tilesetImage.getGraphics().drawImage(tileImage, offsetX, offsetY, null);
        }

        return tilesetImage;
    }
}

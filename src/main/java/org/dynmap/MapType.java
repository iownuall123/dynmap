package org.dynmap;

import java.util.List;

import org.bukkit.Location;
import org.json.simple.JSONObject;

public abstract class MapType {
    public enum ImageFormat {
        FORMAT_PNG("png"),
        FORMAT_JPG("jpg");
        
        String ext;
        ImageFormat(String ext) {
            this.ext = ext;
        }
        public String getFileExt() { return ext; }
    };

    public abstract MapTile[] getTiles(Location l);

    public abstract MapTile[] getTiles(Location l0, Location l1);

    public abstract MapTile[] getAdjecentTiles(MapTile tile);

    public abstract List<DynmapChunk> getRequiredChunks(MapTile tile);
    
    public void buildClientConfiguration(JSONObject worldObject, DynmapWorld w) {
    }
    
    public abstract String getName();

    /* Get maps rendered concurrently with this map in this world */
    public abstract List<MapType> getMapsSharingRender(DynmapWorld w);
    /* Get names of maps rendered concurrently with this map type in this world */
    public abstract List<String> getMapNamesSharingRender(DynmapWorld w);
    
    public enum MapStep {
        X_PLUS_Y_PLUS,
        X_PLUS_Y_MINUS,
        X_MINUS_Y_PLUS,
        X_MINUS_Y_MINUS
    }
    public abstract MapStep zoomFileMapStep();
    public abstract List<String> baseZoomFilePrefixes();
    public abstract int baseZoomFileStepSize();
    /* How many bits of coordinate are shifted off to make big world directory name */
    public abstract int getBigWorldShift();
    /* Returns true if big world file structure is in effect for this map */
    public abstract boolean isBigWorldMap(DynmapWorld w);
    /* Return number of zoom levels needed by this map (before extra levels from extrazoomout) */
    public int getMapZoomOutLevels() { return 0; }
    
    public ImageFormat getImageFormat() { return ImageFormat.FORMAT_PNG; }
    
    /**
     * Step sequence for creating zoomed file: first index is top-left, second top-right, third bottom-left, forth bottom-right
     * Values correspond to tile X,Y (0), X+step,Y (1), X,Y+step (2), X+step,Y+step (3) 
     */
    public abstract int[] zoomFileStepSequence();
}

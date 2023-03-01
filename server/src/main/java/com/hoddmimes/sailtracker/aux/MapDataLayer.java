package com.hoddmimes.sailtracker.aux;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hoddmimes.sailtracker.generated.dbobjects.Position;

import java.util.List;

public class MapDataLayer {
    JsonArray jGeoPositions;

    public MapDataLayer(List<Position> pPositions) {
        jGeoPositions = new JsonArray();
        for (Position pos : pPositions) {
            addPosition(pos);
        }
    }

    public MapDataLayer(Position pPosition) {
        jGeoPositions = new JsonArray();
        addPosition(pPosition);

    }


    private void addPoint(JsonArray pJsonPoint, int pIndex) {
        JsonObject jPos = new JsonObject();
        jPos.addProperty("type", "Feature");

        JsonObject jProp = new JsonObject();
        jProp.addProperty("message", " [ " + pJsonPoint.get(0) + ":" + pJsonPoint.get(1) + " ]  (" + pIndex + ")");
        JsonArray jSizeArray = new JsonArray();
        jSizeArray.add(32); // Icon size 32 pixels
        jSizeArray.add(32); // Icon size 32 pixels
        jProp.add("iconSize", jSizeArray);
        jPos.add("properties", jProp);

        JsonObject jGeo = new JsonObject();
        jGeo.addProperty("type", "Point");
        JsonArray jLatLong = new JsonArray();
        jLatLong.add(pJsonPoint.get(0));
        jLatLong.add(pJsonPoint.get(1));
        jGeo.add("coordinates", jLatLong);
        jPos.add("geometry", jGeo);

        jGeoPositions.add(jPos);
    }

    private void addPosition(Position pPosition) {
        JsonObject jPos = new JsonObject();
        jPos.addProperty("type", "Feature");

        JsonObject jProp = new JsonObject();
        jProp.addProperty("message", "date: " + pPosition.getTime().get());
        JsonArray jSizeArray = new JsonArray();
        jSizeArray.add(32); // Icon size 32 pixels
        jSizeArray.add(32); // Icon size 32 pixels
        jProp.add("iconSize", jSizeArray);
        jPos.add("properties", jProp);

        JsonObject jGeo = new JsonObject();
        jGeo.addProperty("type", "Point");
        JsonArray jLatLong = new JsonArray();
        jLatLong.add(pPosition.getLong().get());
        jLatLong.add(pPosition.getLat().get());
        jGeo.add("coordinates", jLatLong);
        jPos.add("geometry", jGeo);

        jGeoPositions.add(jPos);
    }

    public JsonElement mapLayerToJson() {
        return jGeoPositions;
    }
}

class LongLat {
    double lng;
    double lat;

    LongLat(double pLng, double pLat) {
        lng = pLng;
        lat = pLat;
    }
}
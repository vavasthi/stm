package com.avasthi.android.apps.roadbuddy.backend.fence;

import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.gson.Gson;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.index.strtree.STRtree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

/**
 * Created by vavasthi on 9/12/15.
 */
public class FenceUtils {
    public static Fence createFence(long placeId, String name, String group, boolean buildIndex, Double latitude, Double longitude, Double radius) {

        Fence fence = new Fence(placeId, name, group, latitude, longitude, radius);

        //Get the last fences' id.
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key fenceKey = KeyFactory.createKey("Geofence", group);

        long nextId;
        long lastId;
        Query query = new Query("Fence", fenceKey).addSort("id", Query.SortDirection.DESCENDING);
        List<Entity> lastFence = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(1));

        if (lastFence.isEmpty()) {
            lastId = -1;
        } else {
            lastId = (long) lastFence.get(0).getProperty("id");
        }
        nextId = lastId + 1;

        //Create new Entity with nextId.
        Entity fenceEntity = new Entity("Fence", fenceKey);
        fenceEntity.setProperty("id", nextId);
        fenceEntity.setProperty("placeId", placeId);
        fenceEntity.setProperty("name", fence.getName());
        fenceEntity.setProperty("latitude", fence.getLatitude());
        fenceEntity.setProperty("longitude", fence.getLongitude());
        fenceEntity.setProperty("radius", fence.getRadius());

        datastore.put(fenceEntity);

        fence.setId(nextId);

        //Rebuild the Index.
        if (buildIndex) {
            try {
                GeometryIndex.buildIndex(group);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fence;
    }

    public static ArrayList<Fence> queryPoint(String group, double longitude, double latitude) {

        ArrayList<Fence> fences = new ArrayList<Fence>();

        //Get the Index from Memcache.
        MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
        GeometryFactory gf = new GeometryFactory();
        STRtree index = (STRtree) syncCache.get(group); // read from cache
        if (index == null) {

            try {
                GeometryIndex.buildIndex(group);
                index = (STRtree) syncCache.get(group); // read from cache
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (index != null) {
            Coordinate coord = new Coordinate(longitude, latitude);
            Point point = gf.createPoint(coord);
            List<FencingCircle> items = index.query(point.getEnvelopeInternal());
            if (!items.isEmpty()) {
                for (FencingCircle fc : items) {
                    if (fc.getGeometry().contains(point)) {
                        long id = fc.getId();
                        Fence newFence = new Fence();
                        newFence.setId(id);
                        fences.add(newFence);
                    }
                }
            }
        }
        return fences;
    }

}

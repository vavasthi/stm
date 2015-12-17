package com.avasthi.android.apps.roadbuddy.backend.fence;

import com.avasthi.android.apps.roadbuddy.backend.OfyService;
import com.avasthi.android.apps.roadbuddy.backend.bean.Fence;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.index.strtree.STRtree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 9/12/15.
 */
public class FenceUtils {

    private static final Logger logger = Logger.getLogger(FenceUtils.class.getName());

    public static Fence createFence(long placeId, String name, String group, boolean buildIndex, Double latitude, Double longitude, Double radius) {

        Fence fence = new Fence(placeId, name, group, latitude, longitude, radius);

        OfyService.ofy().save().entity(fence).now();

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
                if (index != null) {

                    logger.severe("Index is " + index.size() + " index " + index.toString() + " group is " + group);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (index != null) {
            Coordinate coord = new Coordinate(longitude, latitude);
            Point point = gf.createPoint(coord);
            List<FencingPolygon> items = index.query(point.getEnvelopeInternal());
            if (!items.isEmpty()) {
                for (FencingPolygon fc : items) {
                    if (fc.contains(point)) {
                        long id = fc.getId();
                        Fence loadedFence = OfyService.ofy().load().type(Fence.class).id(id).now();
                        logger.severe("Item is " + items.size() + " index " + items.toString() + " group is " + group + " area " + fc.getArea());
                        fences.add(loadedFence);
                    }
                }
            }
        }
        return fences;
    }

}

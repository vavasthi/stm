package com.avasthi.android.apps.roadbuddy.backend.fence;

/**
 * Created by vavasthi on 9/12/15.
 */

import com.avasthi.android.apps.roadbuddy.backend.OfyService;
import com.avasthi.android.apps.roadbuddy.backend.bean.Fence;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.gson.Gson;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.index.strtree.STRtree;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by vavasthi on 12/10/15.
 */
public class GeometryIndex {
    private static final Logger logger = Logger.getLogger(GeometryIndex.class.getName());
    //Create the index and store it in Memcache.
    public static int buildIndex(String group) throws IOException {
        //Get all fences of group from DataStore.

        List<Fence> fenceList = OfyService.ofy().load().type(Fence.class).filter("group", group).list();
        if (!fenceList.isEmpty()) {
            //Create STRTree-Index.
            STRtree index = new STRtree();
            //Loop through the fences from DataStore.
            for (Fence fence : fenceList) {
                //Create polygon from the coordinates.
                GeometryFactory fact = new GeometryFactory();
                /*Geometry g = fact.createPoint(new Coordinate(fence.getLatitude(), fence.getLongitude()));
                g.buffer(fence.getRadius(), 20);
                FencingCircle fc = new FencingCircle(fence.getId(), fence.getPlaceId(), g);
                Envelope env = fc.getGeometry().getEnvelopeInternal();
                env.expandBy(fence.getRadius());
                logger.log(Level.INFO, "The area is " + env.getArea());
                index.insert(env, fc);*/
                FencingPolygon polygon = new FencingPolygon(fence.getId(), fence.getPlaceId(), fence.getLatitude(), fence.getLongitude(), fence.getRadius());
                index.insert(polygon.getEnvelopeInternal(), polygon);
            }
            //Build the index.
            index.build();
            //Write the index to Memcache.
            MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
            //Last param is expiration date. Set to null to keep it in Memcache forever.
            logger.info("Index size is " + index.size() + "|" + index.toString());
            syncCache.delete(group);
            syncCache.put(group, index, null);
        }
        return fenceList.size();
    }
}

package com.avasthi.android.apps.roadbuddy.backend.fence;

/**
 * Created by vavasthi on 9/12/15.
 */

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
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.index.strtree.STRtree;

import java.io.IOException;
import java.util.List;



/**
 * Created by vavasthi on 12/10/15.
 */
public class GeometryIndex {
    //Create the index and store it in Memcache.
    public static int buildIndex(String group) throws IOException {
        //Get all fences of group from DataStore.
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key fenceKey = KeyFactory.createKey("Geofence", group);

        Query query = new Query("Fence", fenceKey).addSort("id", Query.SortDirection.DESCENDING);
        List<Entity> fencesFromStore = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());

        if (!fencesFromStore.isEmpty()) {
            //Create STRTree-Index.
            STRtree index = new STRtree();
            //Loop through the fences from DataStore.
            for (Entity fenceFromStore : fencesFromStore) {
                long id = (long) fenceFromStore.getProperty("id");
                long placeId = (long) fenceFromStore.getProperty("placeId");
                Double latitude = (Double) fenceFromStore.getProperty("latitude");
                Double longitude = (Double) fenceFromStore.getProperty("longitude");
                Double radius = (Double) fenceFromStore.getProperty("radius");
                //Create polygon from the coordinates.
                GeometryFactory fact = new GeometryFactory();
                Geometry g = fact.createPoint(new Coordinate(latitude, longitude));
                g.buffer(radius, 20);
                FencingCircle fc = new FencingCircle(id, placeId, g);
                index.insert(fc.getGeometry().getEnvelopeInternal(), fc);
            }
            //Build the index.
            index.build();
            //Write the index to Memcache.
            MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
            //Last param is expiration date. Set to null to keep it in Memcache forever.
            syncCache.put(group, index, null);
        }
        return fencesFromStore.size();
    }
}

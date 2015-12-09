package com.avasthi.android.apps.roadbuddy.backend.endpoints;

/**
 * Created by vavasthi on 9/12/15.
 */
/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

import com.avasthi.android.apps.roadbuddy.backend.fence.Fence;
import com.avasthi.android.apps.roadbuddy.backend.fence.FenceUtils;
import com.avasthi.android.apps.roadbuddy.backend.fence.FencingCircle;
import com.avasthi.android.apps.roadbuddy.backend.fence.GeometryIndex;
import com.avasthi.roadbuddy.common.RBConstants;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
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
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.index.strtree.STRtree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.index.strtree.STRtree;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.LineString;

import javax.inject.Named;

import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "geofencingApi",
        version = "v1",
        scopes = {RBConstants.EMAIL_SCOPE},
        audiences = {RBConstants.ANDROID_AUDIENCE},
        clientIds = {RBConstants.WEB_CLIENT_ID, RBConstants.ANDROID_CLIENT_ID},
        namespace = @ApiNamespace(
                ownerDomain = RBConstants.OWNER_DOMAIN,
                ownerName = RBConstants.OWNER_DOMAIN,
                packagePath = ""
        )
)
public class GeofencingEndpoint {

    private final static Logger logger = Logger.getLogger(GeofencingEndpoint.class.getName());


    /**
     * Endpoint for listing all fences from the DataStore collection.
     */
    @ApiMethod(name = "list", httpMethod = "get", path = "list")
    public ArrayList<Fence> listFences(@Named("group") String group) {
        ArrayList<Fence> fences = new ArrayList<Fence>();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key fenceKey = KeyFactory.createKey("Geofence", group);

        Query query = new Query("Fence", fenceKey).addSort("id", Query.SortDirection.DESCENDING);
        List<Entity> fencesFromStore = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());

        if (fencesFromStore.isEmpty()) {
            return fences;
        } else {
            for (Entity fenceFromStore : fencesFromStore) {
                long id = (long) fenceFromStore.getProperty("id");
                String name = (String) fenceFromStore.getProperty("name");
                Double latitude = (Double) fenceFromStore.getProperty("latitude");
                Double longitude = (Double) fenceFromStore.getProperty("longitude");
                Double radius = (Double) fenceFromStore.getProperty("radius");

                Fence tempFence = new Fence(id, name, group, latitude, longitude, radius);
                fences.add(tempFence);
            }
            return fences;
        }
    }

    /**
     * Endpoint for finding the fences a certain point is in.
     */
    @ApiMethod(name = "point", httpMethod = "get", path = "point")
    public ArrayList<Fence> queryPoint(@Named("group") String group, @Named("longitude") double longitude, @Named("latitude") double latitude) {
        ArrayList<Fence> fences = new ArrayList<Fence>();

        //Get the Index from Memcache.
        MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
        GeometryFactory gf = new GeometryFactory();
        STRtree index = (STRtree) syncCache.get(group); // read from cache
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
        } else {
            try {
                GeometryIndex.buildIndex(group);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fences;
    }

    /**
     * Endpoint for getting a fence's metadata by its id.
     */
    @ApiMethod(name = "getById", httpMethod = "get", path = "getById")
    public ArrayList<Fence> getFenceById(@Named("group") String group, @Named("id") long id) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key fenceKey = KeyFactory.createKey("Geofence", group);
        Query.Filter propertyFilter = new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, id);
        Query query = new Query("Fence", fenceKey).setFilter(propertyFilter);
        Entity fenceFromStore = datastore.prepare(query).asSingleEntity();

        ArrayList<Fence> fences = new ArrayList<Fence>();
        if (fenceFromStore != null) {
            String name = (String) fenceFromStore.getProperty("name");
            Double latitude = (Double)fenceFromStore.getProperty("latitude");
            Double longitude = (Double)fenceFromStore.getProperty("longitude");
            Double radius = (Double)fenceFromStore.getProperty("radius");

            Fence tempFence = new Fence(id, name, group, latitude, longitude, radius);
            fences.add(tempFence);
        }
        return fences;
    }

    /**
     * Endpoint for building and storing the index for a certain group to Memcache.
     */
    @ApiMethod(name = "buildIndex", httpMethod = "get", path = "buildIndex")
    public Fence buildIndex(@Named("group") String group) {
        Fence fence = new Fence();

        long id;
        try {
            id = (long) GeometryIndex.buildIndex(group);
            fence.setId(id);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fence;
    }

    //TODO delete group
}
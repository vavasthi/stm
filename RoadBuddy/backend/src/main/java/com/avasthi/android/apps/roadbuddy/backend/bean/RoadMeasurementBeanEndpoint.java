package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.avasthi.android.apps.roadbuddy.backend.PMF;
import com.avasthi.android.apps.roadbuddy.backend.RoadBuddyConstants;
import com.google.api.server.spi.Constant;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.ConflictException;

import static com.avasthi.android.apps.roadbuddy.backend.OfyService.ofy;

import java.util.logging.Logger;

import javax.inject.Named;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "roadMeasurementBeanApi",
        version = "v1",
        resource = "roadMeasurementBean",
        namespace = @ApiNamespace(
                ownerDomain = "bean.backend.roadbuddy.apps.android.avasthi.com",
                ownerName = "bean.backend.roadbuddy.apps.android.avasthi.com",
                packagePath = ""
        )
)
public class RoadMeasurementBeanEndpoint {

    private static final Logger logger = Logger.getLogger(RoadMeasurementBeanEndpoint.class.getName());

    /**
     * This method gets the <code>RoadMeasurementBean</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>RoadMeasurementBean</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getRoadMeasurementBean", scopes = {RoadBuddyConstants.EMAIL_SCOPE},
    clientIds = {RoadBuddyConstants.ANDROID_CLIENT_ID,
    Constant.API_EXPLORER_CLIENT_ID},
    audiences = {RoadBuddyConstants.ANDROID_AUDIENCE})
    public RoadMeasurementBean getRoadMeasurementBean(@Named("id") Long id) {
        // TODO: Implement this function
        PersistenceManager pm = PMF.get().getPersistenceManager();
        RoadMeasurementBean bean = null;
        try {
            bean = pm.getObjectById(RoadMeasurementBean.class, id);
        }
        finally {
            pm.close();
        }
        logger.info("Calling getRoadMeasurementBean method");
        return bean;
    }

    /**
     * This inserts a new <code>RoadMeasurementBean</code> object.
     *
     * @param newMeasurement The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertRoadMeasurementBean", scopes = {RoadBuddyConstants.EMAIL_SCOPE},
            clientIds = {RoadBuddyConstants.ANDROID_CLIENT_ID,
                    Constant.API_EXPLORER_CLIENT_ID},
            audiences = {RoadBuddyConstants.ANDROID_AUDIENCE})
    public RoadMeasurementBean insertRoadMeasurementBean(RoadMeasurementBean newMeasurement) throws ConflictException {
        if (newMeasurement.getId() != null) {
            if (ofy().load().type(RoadMeasurementBean.class).id(newMeasurement.getId()) != null) {
                throw new ConflictException("Object already exists");
            }
        }
        ofy().save().entity(newMeasurement).now();
        logger.info("Calling insertRoadMeasurementBean method");
        return newMeasurement;
    }
}
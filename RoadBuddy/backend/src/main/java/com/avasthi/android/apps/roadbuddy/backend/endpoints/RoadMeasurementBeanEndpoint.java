package com.avasthi.android.apps.roadbuddy.backend.endpoints;

import com.avasthi.android.apps.roadbuddy.backend.OfyService;
import com.avasthi.android.apps.roadbuddy.backend.bean.Amenity;
import com.avasthi.android.apps.roadbuddy.backend.bean.AmenityStop;
import com.avasthi.android.apps.roadbuddy.backend.bean.Checkpost;
import com.avasthi.android.apps.roadbuddy.backend.bean.CheckpostStop;
import com.avasthi.android.apps.roadbuddy.backend.bean.City;
import com.avasthi.android.apps.roadbuddy.backend.bean.Drive;
import com.avasthi.android.apps.roadbuddy.backend.bean.DriveParameters;
import com.avasthi.android.apps.roadbuddy.backend.bean.Family;
import com.avasthi.android.apps.roadbuddy.backend.bean.FamilyMemberAndPointsOfInterest;
import com.avasthi.android.apps.roadbuddy.backend.bean.Group;
import com.avasthi.android.apps.roadbuddy.backend.bean.GroupMembership;
import com.avasthi.android.apps.roadbuddy.backend.bean.Member;
import com.avasthi.android.apps.roadbuddy.backend.bean.MemberAndVehicles;
import com.avasthi.android.apps.roadbuddy.backend.bean.PointsOfInterest;
import com.avasthi.android.apps.roadbuddy.backend.bean.RegistrationRecord;
import com.avasthi.android.apps.roadbuddy.backend.bean.SensorData;
import com.avasthi.android.apps.roadbuddy.backend.bean.Toll;
import com.avasthi.android.apps.roadbuddy.backend.bean.TollStop;
import com.avasthi.android.apps.roadbuddy.backend.bean.UserGroup;
import com.avasthi.android.apps.roadbuddy.backend.bean.Vehicle;
import com.avasthi.android.apps.roadbuddy.backend.exceptions.AlreadyOngoingDrive;
import com.avasthi.android.apps.roadbuddy.backend.exceptions.GroupRetrievalFailed;
import com.avasthi.android.apps.roadbuddy.backend.exceptions.InvalidMemberException;
import com.avasthi.android.apps.roadbuddy.backend.exceptions.NoOngoingDrive;
import com.avasthi.android.apps.roadbuddy.backend.bean.Fence;
import com.avasthi.android.apps.roadbuddy.backend.fence.FenceUtils;
import com.avasthi.roadbuddy.common.RBConstants;
import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;

import static com.avasthi.android.apps.roadbuddy.backend.OfyService.ofy;


/**
 * An endpoint class we are exposing
 */
@Api(
        name = "roadMeasurementApi",
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
public class RoadMeasurementBeanEndpoint {

    private static final Logger logger = Logger.getLogger(RoadMeasurementBeanEndpoint.class.getName());

    private static final String API_KEY = "AIzaSyDI61G93r9c3jfKUfkaRVZrP8AoU1l4tgk";
    private static final String SENDER_ID = "772008650875";

    @ApiMethod(name = "isRegisteredUser")
    public MemberAndVehicles isRegisteredMember(User user) throws InvalidMemberException, ForbiddenException, OAuthRequestException {


        return authorizeApi(user);
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "register")
    public MemberAndVehicles register(@Named("name") String name,
                                      @Named("mobile") String mobile,
                                      @Named("city") String city,
                                      @Named("state") String state,
                                      @Named("detectedCity") String detectedCity,
                                      @Named("detectedState") String detectedState,
                                      @Named("latitude") Double latitude,
                                      @Named("logitude") Double longitude,
                                      @Named("vehicleBrand") String vehicleBrand,
                                      @Named("vehicleRegistration") String vehicleRegistration,
                                      User user) throws ForbiddenException, OAuthRequestException {
        if (user == null) {
            throw new ForbiddenException("user is null.");
        }
        try {

            MemberAndVehicles memberAndVehicles = authorizeApi(user);
            throw new ConflictException(user.getEmail() + " already exists.");
        } catch (Exception ex) {

        }
        City c = getCity(city, state);
        City dc = getCity(detectedCity, detectedState);
        Vehicle v = new Vehicle(0L, vehicleBrand, vehicleRegistration);
        OfyService.ofy().save().entity(v).now();
        Member member = new Member(name, user.getEmail().toLowerCase(), mobile, c.getId(), dc.getId(), latitude, longitude, v.getId());
        OfyService.ofy().save().entity(member).now();
        v.setOwnerId(member.getId());
        OfyService.ofy().save().entity(v).now();
        Vehicle[] vehicles = new Vehicle[1];
        vehicles[0] = v;
        return new MemberAndVehicles(member, vehicles, null);
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "createGroup")
    public List<UserGroup> createGroup(@Named("name") String name,
                                       @Named("description") String description,
                                       User user) throws ForbiddenException, OAuthRequestException, InvalidMemberException {
        if (user == null) {
            throw new ForbiddenException("user is null.");
        }
        MemberAndVehicles memberAndVehicles = authorizeApi(user);
        Member member = memberAndVehicles.getMember();
        Group group = new Group(name, description, member.getId());
        OfyService.ofy().save().entity(group).now();
        GroupMembership gm = new GroupMembership(member.getId(), group.getId());
        OfyService.ofy().save().entity(gm).now();
        return getGroupsForUser(member);
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "listGroups")
    public List<UserGroup> listGroups(User user) throws ForbiddenException, OAuthRequestException, InvalidMemberException {
        if (user == null) {
            throw new ForbiddenException("user is null.");
        }
        MemberAndVehicles memberAndVehicles = authorizeApi(user);
        return getGroupsForUser(memberAndVehicles.getMember());
    }
    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "addSensorData")
    public SensorData addSensorData(@Named("timestamp") Date timestamp,
                                    @Named("driveId") Long driveId,
                                    @Named("verticalAccelerometerMean") Float verticalAccelerometerMean,
                                    @Named("verticalAccelerometerSD") Float verticalAccelerometerSD,
                                    @Named("accuracy") Float accuracy,
                                    @Named("bearing") Float bearing,
                                    @Named("latitude") Double latitude,
                                    @Named("longitude") Double longitude,
                                    @Named("speed") Float speed,
                                    User user) throws ForbiddenException, OAuthRequestException, InvalidMemberException {

        MemberAndVehicles memberAndVehicles = authorizeApi(user);
        Member member = memberAndVehicles.getMember();
        SensorData sd = new SensorData(member.getId(), driveId, timestamp, verticalAccelerometerMean, verticalAccelerometerSD, latitude, longitude, accuracy, bearing, speed);
        OfyService.ofy().save().entity(sd).now();
        return sd;
    }
    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "addAmenity")
    public PointsOfInterest addAmenity(@Named("timestamp") Date timestamp,
                                       @Named("name") String name,
                                       @Named("latitude") Double latitude,
                                       @Named("longitude") Double longitude,
                                       @Named("hasRestaurant") Boolean hasRestaurant,
                                       @Named("restaurantCreditCardAccepted") Boolean restaurantCreditCardAccepted,
                                       @Named("foodAmount") Float foodAmount,
                                       @Named("restaurantRating") Integer restaurantRating,
                                       @Named("hasRestrooms") Boolean hasRestrooms,
                                       @Named("restroomRating") Integer restroomRating,
                                       @Named("hasPetroStation") Boolean hasPetrolStation,
                                       @Named("fuelCreditCardAccepted") Boolean fuelCreditCardAccepted,
                                       @Named("petrolStationRating") Integer petrolStationRating,
                                       @Named("fuelAmount") Float fuelAmount,
                                       @Named("fuelQuantity") Float fuelQuantity,
                                       @Named("city") String city,
                                       @Named("sate") String state,
                                       @Named("country") String country,
                                       User user) throws ForbiddenException, OAuthRequestException, InvalidMemberException {

        Member member = authorizeApi(user).getMember();
        Amenity amenity = new Amenity(member.getId(), timestamp, name, latitude, longitude, hasRestaurant, restaurantCreditCardAccepted, hasRestrooms, hasPetrolStation, fuelCreditCardAccepted, state, city, country);
        OfyService.ofy().save().entity(amenity).now();
        Drive drive = getOngoingDrive(member);
        AmenityStop amenityStop = new AmenityStop(member.getId(), drive.getId(), amenity.getId(), timestamp, restaurantRating, foodAmount, restroomRating, petrolStationRating, fuelAmount, fuelQuantity);
        OfyService.ofy().save().entity(amenityStop).now();
        FenceUtils.createFence(amenity.getId(), name, RBConstants.AMENITIES_GROUP, true, latitude, longitude, RBConstants.AMENITIES_RADIUS);
        return populatePointOfInterests(member, latitude, longitude);
    }
    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "addToll")
    public PointsOfInterest addToll(@Named("timestamp") Date timestamp,
                                    @Named("fasTagLane") Boolean fasTagLane,
                                    @Named("latitude") Double latitude,
                                    @Named("longitude") Double longitude,
                                    @Named("amount") Float amount,
                                    @Named("city") String city,
                                    @Named("sate") String state,
                                    @Named("country") String country,
                                    User user) throws ForbiddenException, OAuthRequestException, InvalidMemberException {
        Member member = authorizeApi(user).getMember();
        Toll toll = new Toll(member.getId(), timestamp, fasTagLane, latitude, longitude, amount, city, state, country);
        OfyService.ofy().save().entity(toll).now();
        Drive drive = getOngoingDrive(member);
        TollStop tollStop = new TollStop(member.getId(), drive.getId(), toll.getId(), timestamp, amount);
        OfyService.ofy().save().entity(tollStop).now();
        FenceUtils.createFence(toll.getId(), city + "-" + state, RBConstants.TOLLS_GROUP, true, latitude, longitude, RBConstants.TOLLS_RADIUS);
        return populatePointOfInterests(member, latitude, longitude);
    }
    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "addCheckpost")
    public PointsOfInterest addCheckpost(@Named("timestamp") Date timestamp,
                                         @Named("latitude") Double latitude,
                                         @Named("longitude") Double longitude,
                                         @Named("city") String city,
                                         @Named("sate") String state,
                                         @Named("country") String country,
                                         @Named("speedCameras") Boolean speedCameras,
                                         @Named("fineAmount") Float fineAmount,
                                         User user) throws ForbiddenException, OAuthRequestException, InvalidMemberException {
        Member member = authorizeApi(user).getMember();
        Checkpost checkpost = new Checkpost(member.getId(), timestamp, latitude, longitude, city, state, country);
        OfyService.ofy().save().entity(checkpost).now();
        Drive drive = getOngoingDrive(member);
        CheckpostStop checkpostStop = new CheckpostStop(member.getId(), drive.getId(), checkpost.getId(), timestamp, speedCameras, fineAmount);
        OfyService.ofy().save().entity(checkpostStop).now();
        FenceUtils.createFence(checkpost.getId(), city + "-" + state, RBConstants.CHECKPOSTS_GROUP, true, latitude, longitude, RBConstants.CHECKPOSTS_RADIUS);
        return populatePointOfInterests(member, latitude, longitude);
    }
    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "addAmenityStop")
    public PointsOfInterest addAmenityVisit(@Named("establishmentId") Long establishmentId,
                                            @Named("timestamp") Date timestamp,
                                            @Named("restaurantRating") Integer restaurantRating,
                                            @Named("foodAmount") Float foodAmount,
                                            @Named("restroomRating") Integer restroomRating,
                                            @Named("petrolStationRating") Integer petrolStationRating,
                                            @Named("fuelAmount") Float fuelAmount,
                                            @Named("fuelQuantity") Float fuelQuantity,
                                            User user) throws ForbiddenException, OAuthRequestException, InvalidMemberException {
        Member member = authorizeApi(user).getMember();
        Drive drive = getOngoingDrive(member);
        AmenityStop amenityStop = new AmenityStop(member.getId(), drive.getId(), establishmentId, timestamp, restaurantRating, foodAmount, restroomRating, petrolStationRating, fuelAmount, fuelQuantity);
        OfyService.ofy().save().entity(amenityStop).now();
        return populatePointOfInterests(member, drive.getLastLatitude(), drive.getLastLongitude());
    }
    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "addTollStop")
    public PointsOfInterest addTollStop(@Named("establishmentId") Long establishmentId,
                                        @Named("timestamp") Date timestamp,
                                        @Named("fasTagLane") Boolean fasTagLane,
                                        @Named("amount") Float amount,
                                        User user) throws ForbiddenException, OAuthRequestException, InvalidMemberException {
        Member member = authorizeApi(user).getMember();
        Toll toll = OfyService.ofy().load().type(Toll.class).id(establishmentId).now();
        if (fasTagLane != toll.getFasTagLane()) {
            toll.setFasTagLane(fasTagLane);
        }
        Drive drive = getOngoingDrive(member);
        TollStop tollStop = new TollStop(member.getId(), drive.getId(), toll.getId(), timestamp, amount);
        OfyService.ofy().save().entity(tollStop).now();
        return populatePointOfInterests(member, drive.getLastLatitude(), drive.getLastLongitude());
    }
    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "addCheckpostStop")
    public PointsOfInterest addCheckpostStop(@Named("establishmentId") Long establishmentId,
                                             @Named("timestamp") Date timestamp,
                                             @Named("speedCameras") Boolean speedCameras,
                                             @Named("fineAmount") Float fineAmount,
                                             User user) throws ForbiddenException, OAuthRequestException, InvalidMemberException {

        Member member = authorizeApi(user).getMember();
        Drive drive = getOngoingDrive(member);
        CheckpostStop checkpostStop = new CheckpostStop(member.getId(), drive.getId(), establishmentId, timestamp, speedCameras, fineAmount);
        OfyService.ofy().save().entity(checkpostStop).now();
        return populatePointOfInterests(member, drive.getLastLatitude(), drive.getLastLongitude());
    }
    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "updateSensorData")
    public PointsOfInterest updateSensorData(@Named("timestamp") Date timestamp,
                                             @Named("memberId") Long memberId,
                                             @Named("driveId") Long driveId,
                                             @Named("verticalAccelerometerMean") Float verticalAccelerometerMean,
                                             @Named("verticalAccelerometerSD") Float verticalAccelerometerSD,
                                             @Named("latitude") Double latitude,
                                             @Named("longitude") Double longitude,
                                             @Named("speed") Float speed,
                                             @Named("accuracy") Float accuracy,
                                             @Named("bearing") Float bearing,
                                             @Named("distance") Float distance,
                                             User user) throws ForbiddenException, OAuthRequestException, InvalidMemberException, NoOngoingDrive, IOException {
        Member member = authorizeApi(user).getMember();
        Drive drive = getOngoingDrive(member);
        drive.setLastLatitude(latitude);
        drive.setLastLongitude(longitude);
        drive.setDistanceCovered(drive.getDistanceCovered() + distance);
        if (drive == null) {
            throw new NoOngoingDrive(member.getEmail());
        }
        SensorData sensorData = new SensorData(member.getId(), drive.getId(), timestamp, verticalAccelerometerMean, verticalAccelerometerSD, latitude, longitude, speed, accuracy, bearing);
        OfyService.ofy().save().entity(sensorData).now();
        OfyService.ofy().save().entity(drive).now();
        sendMessageToFamilyMembers(RBConstants.UPDATE_FAMILY_LOCATION, member);
        return populatePointOfInterests(member, latitude, longitude);
    }
    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "startDrive")
    public PointsOfInterest startDrive(@Named("timestamp") Date timestamp,
                                       @Named("groupId") Long groupId,
                                       @Named("eventId") Long eventId,
                                       @Named("latitude") Double latitude,
                                       @Named("longitude") Double longitude,
                                       User user) throws ForbiddenException, OAuthRequestException, InvalidMemberException, AlreadyOngoingDrive, IOException {

        Member member = authorizeApi(user).getMember();
        if (getOngoingDrive(member) != null) {
            throw new AlreadyOngoingDrive(member.getEmail());
        }
        Drive drive = new Drive(eventId, groupId, member.getId(), latitude, longitude);
        OfyService.ofy().save().entity(drive).now();
        sendMessageToFamilyMembers(RBConstants.FAMILY_MEMBER_DRIVE_STARTED, member);
        return populatePointOfInterests(member, latitude, longitude);
    }
    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "finishDrive")
    public PointsOfInterest finishDrive(@Named("latitude") Double latitude,
                                        @Named("longitude") Double longitude,
                                        User user) throws ForbiddenException, OAuthRequestException, InvalidMemberException, NoOngoingDrive, IOException {

        Member member = authorizeApi(user).getMember();
        Drive drive = getOngoingDrive(member);
        if (drive == null) {
            throw new NoOngoingDrive(member.getEmail());
        }
        DriveParameters driveParameters = getCurrentDriveParameters(drive);
        drive.setDone(Boolean.TRUE);
        drive.setCompletedAt(new Date());;
        OfyService.ofy().save().entity(driveParameters).now();
        OfyService.ofy().save().entity(drive).now();
        sendMessageToFamilyMembers(RBConstants.FAMILY_MEMBER_DRIVE_STOPPED, member);
        return populatePointOfInterests(member, latitude, longitude, drive);
    }
    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "getFamilyDrives")
    public List<FamilyMemberAndPointsOfInterest> getFamilyDrives(User user) throws ForbiddenException, OAuthRequestException, InvalidMemberException, NoOngoingDrive {

        Member member = authorizeApi(user).getMember();
        return getFamilyDrives(member);
    }
    @ApiMethod(name = "registerDevice")
    public void registerDevice(@Named("regId") String regId,
                               User user) throws InvalidMemberException, OAuthRequestException {

        MemberAndVehicles memberAndVehicles = authorizeApi(user);
        if(findRecord(regId) != null) {
            logger.info("Device " + regId + " already registered, skipping register");
            return;
        }
        RegistrationRecord record = new RegistrationRecord(memberAndVehicles.getMember().getId(), regId);
        ofy().save().entity(record).now();
    }

    /**
     * Unregister a device from the backend
     *
     * @param regId The Google Cloud Messaging registration Id to remove
     */
    @ApiMethod(name = "unregisterDevice")
    public void unregisterDevice(@Named("regId") String regId,
                                 User user) {
        RegistrationRecord record = findRecord(regId);
        if(record == null) {
            logger.info("Device " + regId + " not registered, skipping unregister");
            return;
        }
        ofy().delete().entity(record).now();
    }

    /**
     * Return a collection of registered devices
     *
     * @param count The number of devices to list
     * @return a list of Google Cloud Messaging registration Ids
     */
    @ApiMethod(name = "listDevices")
    public CollectionResponse<RegistrationRecord> listDevices(@Named("count") int count) {
        List<RegistrationRecord> records = ofy().load().type(RegistrationRecord.class).limit(count).list();
        return CollectionResponse.<RegistrationRecord>builder().setItems(records).build();
    }

    private RegistrationRecord findRecord(String regId) {
        return ofy().load().type(RegistrationRecord.class).filter("regId", regId).first().now();
    }

    private void sendMessageToFamilyMembers(String message, Member member) throws IOException {
        List<Member> familyMembers = getFamily(member);
        for (Member familyMember : familyMembers) {

            sendMessage(familyMember, message);
        }
    }
    private PointsOfInterest populatePointOfInterests(Member member) {

        Drive drive = getOngoingDrive(member);
        if (drive != null) {

            return populatePointOfInterests(member, drive.getLastLatitude(), drive.getLastLongitude(), drive);
        }
        else {

            return populatePointOfInterests(member, member.getLatitude(), member.getLongitude(), drive);
        }
    }
    private PointsOfInterest populatePointOfInterests(Member member, Double latitude, Double longitude) {

        return populatePointOfInterests(member, latitude, longitude, getOngoingDrive(member));
    }
    private PointsOfInterest populatePointOfInterests(Member member, Double latitude, Double longitude, Drive drive) {

        try {

            List<Amenity> amenityList = new ArrayList<>();
            {

                List<Fence> fenceList = FenceUtils.queryPoint(RBConstants.AMENITIES_GROUP, longitude, latitude);
                if (fenceList != null) {

                    for (Fence f : fenceList) {
                        amenityList.add(OfyService.ofy().load().type(Amenity.class).id(f.getPlaceId()).now());
                    }
                }
            }
            List<Toll> tollList = new ArrayList<>();
            {

                List<Fence> fenceList = FenceUtils.queryPoint(RBConstants.TOLLS_GROUP, longitude, latitude);
                if (fenceList != null) {

                    for (Fence f : fenceList) {
                        tollList.add(OfyService.ofy().load().type(Toll.class).id(f.getPlaceId()).now());
                    }
                }
            }
            List<Checkpost> checkpostList = new ArrayList<>();
            {

                List<Fence> fenceList = FenceUtils.queryPoint(RBConstants.CHECKPOSTS_GROUP, longitude, latitude);
                if (fenceList != null) {

                    for (Fence f : fenceList) {
                        checkpostList.add(OfyService.ofy().load().type(Checkpost.class).id(f.getPlaceId()).now());
                    }
                }
            }
            return new PointsOfInterest(drive, amenityList, tollList, checkpostList);
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "Illegal argument exception.", ex);
            throw new RuntimeException(ex);
        }
    }
    private MemberAndVehicles authorizeApi(com.google.appengine.api.users.User user) throws InvalidMemberException, OAuthRequestException {
        if (user == null) {
            throw new OAuthRequestException("User is null");
        }
        Member member = OfyService.ofy().load().type(Member.class).filter("email", user.getEmail().toLowerCase()).first().now();
        if (member == null) {

            throw new InvalidMemberException(user.getEmail());
        }
        List<Vehicle> list = OfyService.ofy().load().type(Vehicle.class).filter("ownerId", member.getId()).list();
        Vehicle[] vehicles = list.toArray(new Vehicle[list.size()]);
        Drive drive = getOngoingDrive(member);
        return new MemberAndVehicles(member, vehicles, drive);
    }
    private City getCity(String city, String state) {

        city = city.toUpperCase();
        City c = OfyService.ofy().load().type(City.class).filter("city", city).filter("state", state).first().now();
        if (c == null) {
            c = new City(city, state);
            OfyService.ofy().save().entity(c).now();
        }
        return c;
    }
    private Drive getOngoingDrive(Member member) {

        return OfyService.ofy().load().type(Drive.class).filter("memberId", member.getId()).filter("done", Boolean.FALSE).first().now();
    }

    private DriveParameters getCurrentDriveParameters(Member member) {


        Drive drive = getOngoingDrive(member);
        return getCurrentDriveParameters(drive);
    }
    private DriveParameters getCurrentDriveParameters(Drive drive) {


        int fuelStopCount = 0;
        Float fuelAmount = 0.0F;
        Float fuelQuantity = 0.0F;
        int amenityStopCount = 0;
        Float foodAmount = 0.0F;
        int tollStopCount = 0;
        Float tollAmount = 0.0F;
        int numberOfCheckposts = 0;
        int speedCameras = 0;
        Float fineAmount = 0.0F;
        {
            List<AmenityStop> list = OfyService.ofy().load().type(AmenityStop.class).filter("driveId", drive.getId()).list();
            for (AmenityStop as : list) {
                if (as.getFoodAmount() > 0.0F) {
                    ++amenityStopCount;
                    amenityStopCount += as.getFoodAmount();
                }
                if (as.getFuelAmount() > 0.0F) {
                    ++fuelStopCount;
                    fuelAmount += as.getFuelAmount();
                    fuelQuantity += as.getFuelQuantity();
                }
            }
        }
        {
            List<TollStop> list = OfyService.ofy().load().type(TollStop.class).filter("driveId", drive.getId()).list();
            for (TollStop ts : list) {
                if (ts.getAmount() > 0.0F) {
                    ++amenityStopCount;
                    tollAmount += ts.getAmount();
                }
            }
        }
        {
            List<CheckpostStop> list = OfyService.ofy().load().type(CheckpostStop.class).filter("driveId", drive.getId()).list();
            for (CheckpostStop cs : list) {
                if (cs.getFineAmount() > 0.0F) {
                    fineAmount += cs.getFineAmount();
                }
                if (cs.getSpeedCameras()) {
                    ++speedCameras;
                }
            }
        }
        return  new DriveParameters(drive.getId(), foodAmount, fuelAmount, amenityStopCount, numberOfCheckposts, speedCameras, fineAmount, tollStopCount, tollAmount);
    }

    private List<UserGroup> getGroupsForUser(Member member) throws ForbiddenException, OAuthRequestException {
        try {

            List<UserGroup> listGroups = new ArrayList<>();
            List<GroupMembership> gmList = OfyService.ofy().load().type(GroupMembership.class).filter("memberId", member.getId()).list();
            System.out.println("Query returned :"  + gmList.size() + " for " + member.getName());
            logger.severe("Query returned :"  + gmList.size() + " for " + member.getName());
            for (GroupMembership gm : gmList) {

                Group group = OfyService.ofy().load().type(Group.class).id(gm.getGroupId()).now();
                if (group.getOwnerId().equals(member.getId())) {
                    listGroups.add(UserGroup.createAdministratorGroup(group.getId(), group.getName(), group.getDescription()));
                }
                else {

                    listGroups.add(UserGroup.createMemberGroup(group.getId(), group.getName(), group.getDescription()));
                }
            }
            logger.severe("Total number of groups :"  + listGroups.size());
            return listGroups;
        } catch (Exception ex) {
            throw new GroupRetrievalFailed(member.getId(), ex);
        }
    }
    private List<Member> getFamily(Member member) {
        List<Member> memberList = new ArrayList<>();
        memberList.add(member);
        {

            List<Family> familyList = OfyService.ofy().load().type(Family.class).filter("firstMemberId", member.getId()).list();
            for (Family f:familyList) {

                Member member2 = OfyService.ofy().load().type(Member.class).id(f.getSecondMemberId()).now();
                memberList.add(member2);
            }

        }
        {

            List<Family> familyList = OfyService.ofy().load().type(Family.class).filter("secondMemberId", member.getId()).list();
            for (Family f:familyList) {

                Member member2 = OfyService.ofy().load().type(Member.class).id(f.getFirstMemberId()).now();
                memberList.add(member2);
            }

        }
        return memberList;
    }
    private List<FamilyMemberAndPointsOfInterest> getFamilyDrives(Member member) {
        List<FamilyMemberAndPointsOfInterest> familyMemberAndPointsOfInterestList = new ArrayList<>();
        Drive drive = getOngoingDrive(member);
        if (drive != null) {

            PointsOfInterest poi = populatePointOfInterests(member, drive.getLastLatitude(), member.getLongitude(), drive);
            familyMemberAndPointsOfInterestList.add(new FamilyMemberAndPointsOfInterest(member, poi));
        }
        {

            List<Family> familyList = OfyService.ofy().load().type(Family.class).filter("firstMemberId", member.getId()).list();
            for (Family f:familyList) {

                Member member1 = OfyService.ofy().load().type(Member.class).id(f.getSecondMemberId()).now();
                Drive drive1 = getOngoingDrive(member1);
                if (drive1 != null) {

                    PointsOfInterest poi1 = populatePointOfInterests(member1, drive1.getLastLatitude(), member1.getLongitude(), drive1);
                    familyMemberAndPointsOfInterestList.add(new FamilyMemberAndPointsOfInterest(member1, poi1));
                }
            }

        }
        {

            List<Family> familyList = OfyService.ofy().load().type(Family.class).filter("secondMemberId", member.getId()).list();
            for (Family f:familyList) {

                Member member1 = OfyService.ofy().load().type(Member.class).id(f.getFirstMemberId()).now();
                Drive drive1 = getOngoingDrive(member1);
                if (drive1 != null) {

                    PointsOfInterest poi1 = populatePointOfInterests(member1, drive1.getLastLatitude(), member1.getLongitude(), drive1);
                    familyMemberAndPointsOfInterestList.add(new FamilyMemberAndPointsOfInterest(member1, poi1));
                }
            }

        }
        return familyMemberAndPointsOfInterestList;
    }
    private void sendMessage(Member member,
                             String message) throws IOException {

        if (message == null || message.trim().length() == 0) {
            logger.warning("Not sending message because it is empty");
            return;
        }
        // crop longer messages
        if (message.length() > 1000) {
            message = message.substring(0, 1000) + "[...]";
        }
        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder().addData("message", message).build();
        RegistrationRecord record = OfyService.ofy().load().type(RegistrationRecord.class).filter("memberId", member.getId()).first().now();
        Result result = sender.send(msg, record.getRegId(), 5);
        if (result.getMessageId() != null) {
            logger.info("Message sent to " + record.getRegId());
            String canonicalRegId = result.getCanonicalRegistrationId();
            if (canonicalRegId != null) {
                // if the regId changed, we have to update the datastore
                logger.info("Registration Id changed for " + record.getRegId() + " updating to " + canonicalRegId);
                record.setRegId(canonicalRegId);
                OfyService.ofy().save().entity(record).now();
            }
        } else {
            String error = result.getErrorCodeName();
            if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                logger.warning("Registration Id " + record.getRegId() + " no longer registered with GCM, removing from datastore");
                // if the device is no longer registered with Gcm, remove it from the datastore
                OfyService.ofy().delete().entity(record).now();
            } else {
                logger.warning("Error when sending message : " + error);
            }
        }
    }

    public void sendMessage(@Named("message") String message) throws IOException {
        List<RegistrationRecord> records = OfyService.ofy().load().type(RegistrationRecord.class).limit(10).list();
        for (RegistrationRecord record : records) {
            Member member = OfyService.ofy().load().type(Member.class).id(record.getMemberId()).now();
            sendMessage(member, message);
        }
    }

}

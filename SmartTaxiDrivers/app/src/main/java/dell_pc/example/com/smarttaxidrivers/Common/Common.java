package dell_pc.example.com.smarttaxidrivers.Common;


import android.location.Location;


import dell_pc.example.com.smarttaxidrivers.Model.User;
import dell_pc.example.com.smarttaxidrivers.Remote.FCMClient;
import dell_pc.example.com.smarttaxidrivers.Remote.IFCMService;
import dell_pc.example.com.smarttaxidrivers.Remote.IGoogleAPI;
import dell_pc.example.com.smarttaxidrivers.Remote.RetrofitClient;

public class Common {

    public static User currentUser; //To save current user

    public static String currentToken = "";

    public static Location mLastLocation = null;

    public static final String baseURL = "https://maps.googleapis.com";
    public static final String fcmURL = "https://fcm.googleapis.com/";

    public static final String driver_tbl = "Drivers";
    public static final String user_driver_tbl = "DriversInformation";
    public static final String user_rider_tbl = "RidersInformation";
    public static final String pickup_request_tbl = "PickupRequest";


    public static IGoogleAPI getGoogleAPI() {
        return RetrofitClient.getClient(baseURL).create(IGoogleAPI.class);
    }

    public static IFCMService getFCMService() {
        return FCMClient.getClient(fcmURL).create(IFCMService.class);
    }

}

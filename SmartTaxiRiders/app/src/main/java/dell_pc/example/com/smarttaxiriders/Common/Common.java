package dell_pc.example.com.smarttaxiriders.Common;

import dell_pc.example.com.smarttaxiriders.Remote.FCMClient;
import dell_pc.example.com.smarttaxiriders.Remote.IFCMService;

public class Common {

    public static final String fcmURL = "https://fcm.googleapis.com/";
    public static final String driver_tbl = "Drivers";
    public static final String user_driver_tbl = "DriversInformation";
    public static final String user_rider_tbl = "RidersInformation";
    public static final String pickup_request_tbl = "PickupRequest";
    public static final String token_tbl="Token";


    public static IFCMService getFCMService() {
        return FCMClient.getClient(fcmURL).create(IFCMService.class);
    }

}

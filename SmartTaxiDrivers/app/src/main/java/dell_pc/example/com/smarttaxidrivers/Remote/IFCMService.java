package dell_pc.example.com.smarttaxidrivers.Remote;

import dell_pc.example.com.smarttaxidrivers.Model.FCMResponse;
import dell_pc.example.com.smarttaxidrivers.Model.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMService {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAhmpklC0:APA91bH7E1wGGmgc2D0w4-94_U1spBaFT21I9sHwuzOwfnCq8ztteilvUWFew_Sa_4qVNqbN1GNc_Uvnbex2aDDr7Dew7AIP7ryF1Xnyg9nx2dZD4oIYGaFFITRHV31iEeurtC8nDoPr"
    })

    @POST("fcm/send")
    Call<FCMResponse> sendMessage(@Body Sender body);

}

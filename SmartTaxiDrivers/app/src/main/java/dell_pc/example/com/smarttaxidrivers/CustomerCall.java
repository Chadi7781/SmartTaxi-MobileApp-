package dell_pc.example.com.smarttaxidrivers;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dell_pc.example.com.smarttaxidrivers.Common.Common;
import dell_pc.example.com.smarttaxidrivers.Model.FCMResponse;
import dell_pc.example.com.smarttaxidrivers.Model.Notification;
import dell_pc.example.com.smarttaxidrivers.Model.Sender;
import dell_pc.example.com.smarttaxidrivers.Model.Token;
import dell_pc.example.com.smarttaxidrivers.Remote.IFCMService;
import dell_pc.example.com.smarttaxidrivers.Remote.IGoogleAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CustomerCall extends AppCompatActivity  {

    TextView txtTime, txtAddress, txtDistance;

    IGoogleAPI mService;

    IFCMService mIFCMService;

    MediaPlayer mediaPlayer;

    public String custommerId;
    Button btnAccept,btnDecline;

    double lat,lng;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/restaurantFont.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_customer_call);

        mService = Common.getGoogleAPI();
        mIFCMService = Common.getFCMService();

        txtTime = (TextView)findViewById(R.id.txtTime);
        txtAddress = (TextView)findViewById(R.id.txtAddress);
        txtDistance = (TextView)findViewById(R.id.txtDistance);
        btnAccept = (Button)findViewById(R.id.btnAccept);
        btnDecline = (Button)findViewById(R.id.btnDecline);

        //Decline= cancel (same word)
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(custommerId))
                    cancelBooking(custommerId);
            }
        });
        // Accept
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerCall.this, DriverTracking.class);
                //Send customer location to new activity
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                intent.putExtra("customerId",custommerId);
                startActivity(intent);
                finish();

            }
        });

        mediaPlayer = MediaPlayer.create(this, R.raw.iphon);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
 
        if(getIntent() != null) {
             lat = getIntent().getDoubleExtra("lat",-1.0);
             lng = getIntent().getDoubleExtra("lng",-1.0);
            custommerId = getIntent().getStringExtra("customer");



            getDirection(lat,lng);
        }
    }

    private void cancelBooking(String custommerId) {
        Token token = new Token(custommerId);

        Notification notification = new Notification("Notice!","Driver has cancelled your request");
        Sender sender = new Sender(token.getToken(),notification);

        mIFCMService.sendMessage(sender)
                .enqueue(new Callback<FCMResponse>() {
                    @Override
                    public void onResponse(Call<FCMResponse> call, Response<FCMResponse> response) {
                        if(response.body().success == 1){
                            Toast.makeText(CustomerCall.this, "Cancelled", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<FCMResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    protected void onStop() {
        mediaPlayer.release();
        super.onStop();
    }

    @Override
    protected void onPause() {
        mediaPlayer.release();
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();


    }

    private void getDirection(double lat, double lng) {

        String requestApi = null;
        try {
            https://maps.googleapis.com/maps/api/directions/json?origin="+o+"&destination="+d+"&key="+key+"&mode="+mode
            requestApi = "https://maps.googleapis.com/maps/api/directions/json?"+
                    "mode=driving&"+
                    "transit_routing_preference=less_driving&"+
                    "origin="+ Common.mLastLocation.getLatitude()+","+Common.mLastLocation.getLongitude()+"&"+
                    "destination="+lat+","+lng+"&"+
                    "key="+getResources().getString(R.string.google_direction_api);
            Log.d("getDirection :",requestApi);
            mService.getPath(requestApi)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());

                                JSONArray routes = jsonObject.getJSONArray("routes");

                                JSONObject object = routes.getJSONObject(0);

                                JSONArray legs = object.getJSONArray("legs");

                                JSONObject legsObject = legs.getJSONObject(0);

                                JSONObject distance = legsObject.getJSONObject("distance");
                                txtDistance.setText(distance.getString("text"));


                                JSONObject time = legsObject.getJSONObject("duration");
                                txtTime.setText(time.getString("text"));


                                String address = legsObject.getString("end_address");
                                txtAddress.setText(address);

                            } catch (JSONException e) {
                                 Toast.makeText(CustomerCall.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(CustomerCall.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}

package dell_pc.example.com.smarttaxiriders.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import dell_pc.example.com.smarttaxiriders.R;


public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

    View mView;

    public CustomInfoWindow(Context mContext) {
        mView = LayoutInflater.from(mContext)
                .inflate(R.layout.custom_rider_info,null);
    }
    @Override
    public View getInfoWindow(Marker marker) {
        TextView textViewP = ((TextView)mView.findViewById(R.id.txtPickUp));
        textViewP.setText(marker.getTitle());

        TextView textViewS = ((TextView)mView.findViewById(R.id.txtSnippet));
        textViewS.setText(marker.getSnippet());

        return mView;
    }

    @Override
    public View getInfoContents(Marker marker) {

        return null;
    }
}

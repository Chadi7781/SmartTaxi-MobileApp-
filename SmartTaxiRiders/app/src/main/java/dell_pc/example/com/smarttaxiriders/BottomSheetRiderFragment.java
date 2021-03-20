package dell_pc.example.com.smarttaxiriders;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BottomSheetRiderFragment extends BottomSheetDialogFragment {
    String mLocation,mDestination;


    public static BottomSheetRiderFragment newInstance(String location, String destination) {
        BottomSheetRiderFragment f = new BottomSheetRiderFragment();
        Bundle args = new Bundle();
        args.putString("Location",location);
        args.putString("Destination",destination);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocation = getArguments().getString("Location");
        mDestination = getArguments().getString("Destination");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.bottom_sheet_rider,container,false);
        TextView txtLocation = (TextView)root.findViewById(R.id.txtLocation);
        TextView txtDestination = (TextView)root.findViewById(R.id.txtDestination);
        TextView txtCalculate = (TextView)root.findViewById(R.id.txtCalculate);

        //Set Data
        txtLocation.setText(mLocation);
        txtLocation.setText(mDestination);

        return root;
    }
}

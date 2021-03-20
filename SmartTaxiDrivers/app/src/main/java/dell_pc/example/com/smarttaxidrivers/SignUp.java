package dell_pc.example.com.smarttaxidrivers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dell_pc.example.com.smarttaxidrivers.Common.Common;
import dell_pc.example.com.smarttaxidrivers.Model.User;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUp extends AppCompatActivity {

    EditText edtEmail, edtPassword, edtFirstName, edtLastName, edtAddress, edtPhone;

    Button signUp_signUpButton;
    FirebaseDatabase db;
    DatabaseReference users;
    RelativeLayout rootLayout;
    ProgressDialog progressDialog;

    FirebaseAuth auth;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/restaurantFont.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_sign_up);



        init();
        signUp_signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();


            }
        });


    }
    public void registerUser() {
        //Check validation chadi i troudi  don't g
        if(TextUtils.isEmpty(edtFirstName.getText().toString())){
            edtFirstName.setError("Please enter FirstName");
            return;
        }

        if(TextUtils.isEmpty(edtLastName.getText().toString())){
            edtLastName.setError("Please enter LastName");
            return;
        }
        if(TextUtils.isEmpty(edtPhone.getText().toString())){
            edtPhone.setError("Please enter Phone number");
            return;
        }
        if(TextUtils.isEmpty(edtAddress.getText().toString())){
            edtAddress.setError("Please enter Address");
            return;
        }
        if(TextUtils.isEmpty(edtEmail.getText().toString())){
            edtEmail.setError("Please enter Email");
            return;
        }

        if(TextUtils.isEmpty(edtPassword.getText().toString())){
            edtPassword.setError("Please enter Password");

        }

        if(edtPassword.getText().toString().length() < 6){
            edtFirstName.setError("Password too short ");

        }
        //Register new user
        auth.createUserWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //Save user to db


                        User user = new User();
                        user.setFirstName(edtFirstName.getText().toString());
                        user.setLastName(edtLastName.getText().toString());
                        user.setEmail(edtEmail.getText().toString());
                        user.setPassword(edtPassword.getText().toString());
                        user.setAddress(edtAddress.getText().toString());
                        user.setPhone(edtPhone.getText().toString());

                        //Use email to key
                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(),"Successfully",Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Snackbar.make(rootLayout,"Failed "+e.getMessage(),Snackbar.LENGTH_LONG)
                                                .show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(rootLayout,"error connect to database "+e.getMessage(),Snackbar.LENGTH_LONG)
                                .show();
                    }
                });

    }

    public void init() {

        //Init Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference(Common.user_driver_tbl);



        //Init view
        rootLayout = (RelativeLayout)findViewById(R.id.rootLayout);
        edtFirstName = (EditText)findViewById(R.id.edtFirstName);
        edtLastName = (EditText)findViewById(R.id.edtLastName);
        edtPhone = (EditText)findViewById(R.id.edtPhone);
        edtAddress = (EditText)findViewById(R.id.edtAddress);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        signUp_signUpButton = (Button)findViewById(R.id.signUp_signUpButton);
    }



}

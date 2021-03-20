package dell_pc.example.com.smarttaxiriders;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import dell_pc.example.com.smarttaxiriders.Common.Common;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignIn extends AppCompatActivity {

    EditText edtEmail,edtPassword,edtFirstName,edtLastName,edtAddress,edtPhone;
    Button btnSignIn;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    RelativeLayout rootLayout;
    ProgressDialog progressDialog;
    DatabaseReference table_user;
    FirebaseDatabase database;

    Button btn_sign;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/taxiFont.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_sign_in);

        init();

        database= FirebaseDatabase.getInstance();
        table_user=database.getReference(Common.user_rider_tbl);

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, SignUp.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                controlUser();

                final ProgressDialog progressDialog = new ProgressDialog(SignIn.this);
                progressDialog.setMessage("Please waiting...");
                progressDialog.show();

                //Login
                auth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                progressDialog.dismiss();
                                startActivity(new Intent(SignIn.this, Home.class));

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignIn.this, "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }

        });
    }
    private void controlUser() {
        if(TextUtils.isEmpty(edtEmail.getText().toString())){
            edtEmail.setError("Please enter Email");
        }
        if(TextUtils.indexOf(edtEmail.getText().toString(),'@')==- 1)
            edtEmail.setError("Please enter valid @ ");

        if(TextUtils.isEmpty(edtPassword.getText().toString())){
            edtPassword.setError("Please enter Password");
        }

    }

    public void init() {

        //Init view
        rootLayout = (RelativeLayout)findViewById(R.id.rootLayout);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);
        btn_sign = (Button)findViewById(R.id.btn_sign);
        auth = FirebaseAuth.getInstance();
    }
}

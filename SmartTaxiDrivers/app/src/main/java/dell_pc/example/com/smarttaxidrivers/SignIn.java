package dell_pc.example.com.smarttaxidrivers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dell_pc.example.com.smarttaxidrivers.Common.Common;
import dell_pc.example.com.smarttaxidrivers.Model.User;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignIn extends AppCompatActivity {

    EditText edtEmail,edtPassword,edtFirstName,edtLastName,edtAddress,edtPhone;

    Button btnSignIn;
    FirebaseDatabase db;
    DatabaseReference users;
    RelativeLayout rootLayout;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    Button btnAccount;

    DatabaseReference table_user;
    private String userId;

    FirebaseDatabase database;



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
        setContentView(R.layout.activity_sign_in);




        init();

        database=FirebaseDatabase.getInstance();
        table_user=database.getReference(Common.user_driver_tbl);

        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, SignUp.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
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
                                if(edtEmail.getText().toString().equals("") || edtPassword.getText().toString().equals("")) {
                                    progressDialog.dismiss();
                                    Snackbar.make(rootLayout,"Email and Password should be mentioned",Snackbar.LENGTH_LONG)
                                            .show();
                                    return;
                                }
                                progressDialog.dismiss();

                                FirebaseDatabase.getInstance().getReference(Common.user_driver_tbl)
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                Common.currentUser = dataSnapshot.getValue(User.class);

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

                                startActivity(new Intent(SignIn.this, Welcome.class));

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











//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//
//
//            @Override
//            public void onClick(View v) {
//
//                final ProgressDialog progressDialog = new ProgressDialog(SignIn.this);
//                progressDialog.setMessage("Please waiting...");
//                progressDialog.show();
//                FirebaseDatabase.getInstance().getReference().child("chauffeurs")
//                        .addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//
//                            // go to database and add Chauffeur document for test signIn and signUp
//                            // another time to make changes..........
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                    User user = snapshot.getValue(User.class);
//                                    if(user.getMobile().equals(edtPhone.getText().toString()) &&
//                                            user.getEmail().equals(edtEmail.getText().toString())){
//                                        progressDialog.dismiss();
//                                        Intent intent = new Intent(SignIn.this, Welcome.class);
//                                        intent.putExtra("currentUser", user.getUid());
//                                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME,
//                                                MODE_PRIVATE).edit();
//                                        editor.putString("currentUserIdService",user.getUid());
//                                        editor.apply();
//                                        startActivity(intent);
//                                    }
//                                    else {
//                                        progressDialog.dismiss();
//                                        showDialog();
//                                        return;
//
//                                    }
//                                }
//                            }
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                            }
//                        });



    private void controlUser() {
        if(TextUtils.isEmpty(edtEmail.getText().toString())){
            edtEmail.setError("Please enter your email");
            return;
        }

        if(TextUtils.isEmpty(edtPassword.getText().toString())){
            edtPassword.setError("Please enter Password");

        }


    }
    public void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(SignIn.this).create();
        alertDialog.setTitle("Error Authentification");
        alertDialog.setMessage("Wrong Password or email");
        // Alert dialog button
        alertDialog.setButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Alert dialog action goes here
                        // onClick button code here
                        dialog.dismiss();// use dismiss to cancel alert dialog
                    }
                });
        alertDialog.show();
    }




    public void init() {

        //Init view
        auth = FirebaseAuth.getInstance();

        rootLayout = (RelativeLayout)findViewById(R.id.rootLayout);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);
        btnAccount = (Button)findViewById(R.id.btn_sign);
    }


}


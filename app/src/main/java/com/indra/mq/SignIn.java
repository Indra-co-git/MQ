package com.indra.mq;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



public class SignIn extends AppCompatActivity {

    private static final String TAG = SignIn.class.getSimpleName();

    int flag;

    private static final int RC_SIGN_IN = 1;
    //    private static final String TAG = sim;
    FirebaseAuth fireauth;
    DatabaseReference dataref;

    private EditText name,email,pass,compass;
    String user_pass,user_compass,user_email,user_name;
    private Button register;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;


    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        fireauth= FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
//        if(firebaseUser!=null)
//        {
//            Intent i=new Intent(SignIn.this,MainActivity.class);
//            startActivity(i);
//            finish();
//        }

        mAuth = FirebaseAuth.getInstance();
        fireauth=FirebaseAuth.getInstance();
        google_Sign_Request();
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    private void google_Sign_Request() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                Log.d(TAG, "onActivityResult:inside1");
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "onActivityResult:inside2");
                firebaseAuthWithGoogle(account.getIdToken());
                Log.d(TAG, "onActivityResult:Success");
            } catch (ApiException e) {
                Log.d(TAG, "onActivityResult:Faild");
            }
        }
    }


    private void firebaseAuthWithGoogle(String idToken) {
        Log.d(TAG, "onActivityResult:firebaseAuth");
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(final FirebaseUser useres) {

        final FirebaseUser user = mAuth.getCurrentUser();

        if (user != null)
        {                // sign in

//            mUsername=user.getDisplayName();
//            mUserid=user.getUid();
            final String useremail=user.getEmail();

            flag=0;

            //to fetch all the users of firebase Auth app
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

            final DatabaseReference mMessageDatabaseRefrence = rootRef.child("users");

            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String email = ds.child("email").getValue(String.class);
                        if(useremail.equals(email))
                        {
                            flag=1;
                        }

                    }
                    if(flag==0)
                    {

                        String user_email=user.getEmail();
                        String useremail_id = user_email.replace(".",","); // replaces all dots

                        PersonItem person = new PersonItem(user.getDisplayName(),user.getEmail());
                        mMessageDatabaseRefrence.child(useremail_id).child("profile").setValue(person);
//                        DatabaseReference mMessageDatabaseRefrence_temp=FirebaseDatabase.getInstance().getReference().child("globle").child("user_detail_by_id");
//
//                        mMessageDatabaseRefrence_temp.child(useremail_id).setValue(person);

                    }
//                            if(!dataSnapshot.exists()) {
//                            }


                }



                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            mMessageDatabaseRefrence.addListenerForSingleValueEvent(eventListener);

            Toast.makeText(SignIn.this,   "Welcome " + user.getDisplayName() , Toast.LENGTH_SHORT).show();
        }

        Intent i=new Intent(SignIn.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }
}
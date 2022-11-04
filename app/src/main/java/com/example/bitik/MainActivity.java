         package com.example.bitik;

     import androidx.annotation.NonNull;
     import androidx.appcompat.app.AppCompatActivity;

     import android.Manifest;
     import android.content.Intent;
     import android.os.Bundle;
     import android.widget.Toast;

     import com.example.bitik.Common.Common;
     import com.example.bitik.userModel.userModel;
     import com.google.firebase.auth.FirebaseAuth;
     import com.karumi.dexter.Dexter;
     import com.karumi.dexter.PermissionToken;
     import com.karumi.dexter.listener.PermissionRequest;
     import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;

     import java.lang.reflect.Array;
     import java.util.List;

     import kotlin.OverloadResolutionByLambdaReturnType;

         public class MainActivity extends AppCompatActivity {
         private final static int LOGIN_REQUEST_CODE = 1717;
         private list< AuthUI.IdpConfig> providers;
         private FirebaseAuth firebaseAuth;
         private FirebaseAuth.AuthStateListener listener;

         FirebaseDatabase database;
         DatabaseReference useRef;

             @Override
             protected void onStart() {
                 super.onStart();
                 firebaseAuth.addAuthStateListener(listener);
             }

             @Override
             protected void onStop() {
                 if(firebaseAuth != null && listener != null);
                 super.onStop();
             }

             @Override
         protected void onCreate(Bundle savedInstanceState) {
             super.onCreate(savedInstanceState);
             setContentView(R.layout.activity_main);
             init();
         }

         private void init() {
                 providers = Array.asList(
                         new AuthUI.IdpConfig.PhoneBuilder().build()
                 );
                 firebaseAuth = FirebaseAuth.getInstance();

                 database = FirebaseDatabase.getInstance();
             Object userRef = database.getReference(Common.User_REFERENCES);

                 listener = myFirebaseAuth -> {
                     Dexter.withContext(this)
                     .withPermissions(arrays.asList(
                             Manifest.permission.CAMERA,
                             Manifest.permission.READ_EXTERNAL_STORAGE,
                             Manifest.permission.WRITE_EXTERNAL_STORAGE,
                             Manifest.permission.ACCESS_FINE_LOCATION,
                             Manifest.permission.ACCESS_COARSE_LOCATION
                     )).withListener(new BaseMultiplePermissionsListener())
                             @Override
                             public void onPermissionsChecked(sendBroadcastWithMultiplePermissionsReport) {
                     if(multiplePermissionsReport.areAllPermissionsGranted())
                     {
                          firebaseUser user = myFirebaseAuth.getCurrentUser();
                          if(user!= null)
                          {
                              checkUserFromFirebase();
                          }
                          else
                              showLoginLayout();
                     }
                     else
                         Toast.makeText(MainActivity.this, "please enable all permissions", Toast.LENGTH_SHORT).show();

                 }

                 @Override
                  public void onPermissionRationaleShouldBeShown(List< PermissionRequest> list, PermissionToken PermissionToken) {

                     }
         }).check();

             }

             private void showLoginLayout() {
                 startActivityForResult(AuthUI.getInstance().crateSignInIntentBuilder()
                         .setIsSmartLockEnabled(false)
                         .setTheme(R.style.LoginTheme)
                         .setAvailableProviders(providers).build(),LOGIN_REQUEST_CODE);
             }
             }

             private void checkUserFromFirebase() {
               userRef.child(firebaseAuth.getInstance().getCurrentUser().getUid())
                       .addListenerForSingleValueEvent(new ValueEventListener) {
                     @Override
                             public void onDataChange(@NonNull DataSnapshot snapshot){
                         if(snapshot.exists())
                         {
                             userModel userModel = snapshot.getValue(userModel.class);
                             userModel.setUid(snapshot.getKey());
                             goToHomeActivity(userModel);
                         }
                         else
                             showRegisterLayout();
                     }
                 }
                 @Override
                         public void onCancelled(@NonNull DatabaseError error) {
             }


     }

             private void goToHomeActivity(userModel userModel) {
             Common.currentUser = userModel;
             startActivity(new Intent(MainActivity.this,HomeActivity.class));
             finish();
         }


             private void showRegisterLayout() {
             }


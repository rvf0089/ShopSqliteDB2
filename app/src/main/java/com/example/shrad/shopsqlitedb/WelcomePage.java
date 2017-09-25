package com.example.shrad.shopsqlitedb;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class WelcomePage extends AppCompatActivity {

    static DatabaseHelper myDb;
    EditText editfirstName, editLastName, editEmailId, editMobileNo, editId;
    Button btnaddData;
    Button btnviewAll;
    Button btnUpdateData;
    Button btnDeleteData;

    private Button button;
    private TextView textView;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Geocoder geocoder;
    private List<Address> addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        button = (Button) findViewById(R.id.button);

        textView = (TextView) findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewActivity();
            }
        });
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        geocoder = new Geocoder(this, Locale.getDefault());

        /**
         * locationListener would update the textview with the current mobile location address whenever called.
         */
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                try {
                    addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    String address = addressList.get(0).getAddressLine(0);
                    String area = addressList.get(0).getLocality();
                    String full_address =address + ", "+area;
                    textView.append("\n "+full_address+"\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                },10);
            }
            return;
        }else {
            configureButton();
        }
    }

//        editfirstName = (EditText) findViewById(R.id.firstNameTxt);
//        editfirstName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//            @Override
//            public void afterTextChanged(Editable editable) {
//                Validation.hasText(editfirstName);
//            }
//        });
//        editLastName = (EditText) findViewById(R.id.lastNameTxt);
//        editLastName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                Validation.hasText(editLastName);
//            }
//        });
//        editEmailId = (EditText) findViewById(R.id.emailIdTxt);
//        editEmailId.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                Validation.isEmailAddress(editEmailId,true);
//            }
//        });
//        editMobileNo = (EditText) findViewById(R.id.mobileNumberTxt);
//        editMobileNo.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                Validation.isPhoneNumber(editMobileNo,true);
//            }
//        });
//        editId = (EditText) findViewById(R.id.idTxt);
//
//        btnaddData = (Button) findViewById(R.id.btnAddData);
//        btnviewAll =(Button) findViewById(R.id.btnViewAllData);
//        btnUpdateData= (Button) findViewById(R.id.btnUpdateData);
//        btnDeleteData = (Button) findViewById(R.id.btnDeleteData);
//
//        addData();
//        viewAll();
//        deleteData();
//        updateData();
//
//    }
//
//
//    private void updateData() {
//        btnUpdateData.setOnClickListener(
//                new View.OnClickListener(){
//                    @Override
//                    public void onClick(View view) {
//                        boolean isUpdate = myDb.updataData(editId.getText().toString(),
//                                 editfirstName.getText().toString(),editLastName.getText().toString(),
//                                 editMobileNo.getText().toString(),editEmailId.getText().toString());
//                         if(isUpdate==true){
//                             Toast.makeText(ShopOwnerForm.this, "Data Updated", Toast.LENGTH_LONG).show();
//                         } else {
//                             Toast.makeText(ShopOwnerForm.this, "Data Not Updated", Toast.LENGTH_LONG).show();
//                         }
//
//                     }
//                 }
//         );
//     }
//
//     private void deleteData() {
//         btnDeleteData.setOnClickListener(
//                 new View.OnClickListener(){
//                     @Override
//                     public void onClick(View view) {
//                         Integer deletedRows = myDb.deleteData(editId.getText().toString());
//                         if(deletedRows>0)
//                             Toast.makeText(ShopOwnerForm.this,"Data Deleted",Toast.LENGTH_LONG).show();
//                         else
//                             Toast.makeText(ShopOwnerForm.this,"Data Not Deleted",Toast.LENGTH_LONG).show();
//                     }
//                 }
//         );
//     }
//
//     public void addData(){
//         btnaddData.setOnClickListener(
//                 new View.OnClickListener() {
//                     @Override
//                     public void onClick(View view) {
//                         if (checkValidation()) {
//                             boolean isInserted = myDb.insertData(editfirstName.getText().toString(),
//                                     editLastName.getText().toString(), editMobileNo.getText().toString(), editEmailId.getText().toString());
//                             if (isInserted == true) {
//                                 Toast.makeText(ShopOwnerForm.this, "Data inserted", Toast.LENGTH_LONG).show();
//                             } else {
//                                 Toast.makeText(ShopOwnerForm.this, "Data Not inserted", Toast.LENGTH_LONG).show();
//                             }
//                             openNewActivity();
//                         }
//                     }
//                 }
//         );
//
//     }
//

    public void openNewActivity() {
        Intent intent = new Intent(this, ShopOwnerSignUpForm.class);
        startActivity(intent);
    }
//
//     public void viewAll() {
//         btnviewAll.setOnClickListener(
//                 new View.OnClickListener() {
//                     @Override
//                     public void onClick(View v) {
//                         Cursor res = myDb.getAllData();
//                         if (!(myDb.foundData())){
//                             Toast.makeText(ShopOwnerForm.this,"No Data found",Toast.LENGTH_LONG).show();
//                         }
//                         else{
//                             if(res.getCount() == 0) {
//                             showMessage("Error","Nothing found");
//                             return;
//                         }
//
//                         StringBuffer buffer = new StringBuffer();
//                         while (res.moveToNext()) {
//                             buffer.append("Id :"+ res.getString(0)+"\n");
//                             buffer.append("First Name :"+ res.getString(1)+"\n");
//                             buffer.append("Last Name:"+ res.getString(2)+"\n");
//                             buffer.append("Mobile No :"+ res.getString(3)+"\n");
//                             buffer.append("Email ID:"+ res.getString(4)+"\n\n");
//                         }
//
//                         showMessage("Data",buffer.toString());
//                     }
//                         openNewActivity();
//                     }
//                 }
//         );
//     }
//     public void showMessage(String title, String message){
//         AlertDialog.Builder builder = new AlertDialog.Builder(this);
//         builder.setCancelable(true);
//         builder.setTitle(title);
//         builder.setMessage(message);
//         builder.show();
//
//     }
//
//    private boolean checkValidation() {
//        boolean ret = true;
//
//        if (!Validation.hasText(editfirstName)) ret = false;
//        if (!Validation.isEmailAddress(editEmailId, true)) ret = false;
//        if (!Validation.isPhoneNumber(editMobileNo, false)) ret = false;
//
//        return ret;
//    }

    /**
     * This method is used to check if the app has the permission enabled or not, if it is
     * then the permission result is  the configureButton would be called.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 10:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }

    /**
     * The configureButton() is used basically to add the listener to the button so that when its pressed,
     * it would ask for the location updates with the use of LocationManager object created in the onCreated method.
     * The parameters set for requestLocationUpdates are set to the given below because the provider(first paramter)
     * the app would use is the gps, the second parameter is the time the location would be updated so have set to
     * 5 sec(5000millsec) to show location address if same for every 5 second
     * , the third parameter is the location distance would be updated after every 5 meters distance, the last
     * is the listener which we have already created in the onCreate method
     *
     */
    public void configureButton(){
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                locationManager.requestLocationUpdates("gps", 5000, 5, locationListener);
            }
        });

    }

    /**
     *  This method opens the map activity when pressed to get directions to the selected place.
     */
    public void openMapAcitivity(){
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }


}

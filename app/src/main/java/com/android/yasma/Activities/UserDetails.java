package com.android.yasma.Activities;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.yasma.Model.UsersDetails;
import com.android.yasma.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UserDetails extends AppCompatActivity {
    public static final String USER_DETAILS = "user_details";

    private double CellWidth;

    UsersDetails usersDetails;
    ImageView profile_pic;
    TextView name, username, email, phone, website, company_name, company_catchphrase, company_bs, addr_street, addr_suite, addr_city, addr_zipcode;
    SupportMapFragment mapFragment;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Bundle extras = getIntent().getExtras();
        if (extras == null)
            return;
        usersDetails = (UsersDetails) extras.get(USER_DETAILS);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setResources();
        setData();
    }

    private void setResources() {
        profile_pic = (ImageView) findViewById(R.id.ud_img);
        name = (TextView) findViewById(R.id.ud_name);
        username = (TextView) findViewById(R.id.ud_username);
        email = (TextView) findViewById(R.id.ud_email);
        phone = (TextView) findViewById(R.id.ud_phone);
        website = (TextView) findViewById(R.id.ud_website);
        company_name = (TextView) findViewById(R.id.ud_company_name);
        company_catchphrase = (TextView) findViewById(R.id.ud_company_catchphrase);
        company_bs = (TextView) findViewById(R.id.ud_company_bs);
        addr_street = (TextView) findViewById(R.id.ud_addr_street);
        addr_suite = (TextView) findViewById(R.id.ud_addr_suite);
        addr_city = (TextView) findViewById(R.id.ud_addr_city);
        addr_zipcode = (TextView) findViewById(R.id.ud_addr_zipcode);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.ud_map);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        CellWidth = (displayMetrics.widthPixels * 0.20F);
    }

    private void setData() {
        name.setText(usersDetails.getName());
        username.setText(usersDetails.getUsername());
        email.setText(usersDetails.getEmail());
        phone.setText(usersDetails.getPhone());
        website.setText(usersDetails.getWebsite());
        company_name.setText(usersDetails.getCompany().getName());
        company_catchphrase.setText(usersDetails.getCompany().getCatchPhrase());
        company_bs.setText(usersDetails.getCompany().getBs());
        addr_street.setText(usersDetails.getAddress().getStreet());
        addr_suite.setText(usersDetails.getAddress().getSuite());
        addr_city.setText(usersDetails.getAddress().getCity());
        addr_zipcode.setText(usersDetails.getAddress().getZipcode());

        Glide.with(getApplicationContext())
                .load(R.drawable.default_user)
                .asBitmap()
                .placeholder(R.drawable.default_user)
                .error(R.drawable.default_user)
                .into(new BitmapImageViewTarget(profile_pic) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(),
                                Bitmap.createScaledBitmap(resource, (int) CellWidth, (int) CellWidth, false));
                        drawable.setCircular(true);
                        profile_pic.setImageDrawable(drawable);
                    }
                });


        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(usersDetails.getAddress().getGeo().getLat()),Double.parseDouble(usersDetails.getAddress().getGeo().getLng()))).title(usersDetails.getAddress().getStreet());
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
                mMap.addMarker(marker);

                LatLng latlng = new LatLng(Double.parseDouble(usersDetails.getAddress().getGeo().getLat()),Double.parseDouble(usersDetails.getAddress().getGeo().getLng()));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(latlng).zoom(2).build();
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}

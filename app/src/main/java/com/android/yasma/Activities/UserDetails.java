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

public class UserDetails extends AppCompatActivity {
    public static final String USER_DETAILS = "user_details";

    private double CellWidth;

    UsersDetails usersDetails;
    ImageView profile_pic;
    TextView name, username, email, phone, website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Bundle extras = getIntent().getExtras();
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

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        CellWidth = (displayMetrics.widthPixels * 0.20F);
    }

    private void setData() {
        name.setText(usersDetails.getName());
        username.setText(usersDetails.getUsername());
        email.setText(usersDetails.getEmail());
        phone.setText(usersDetails.getPhone());
        website.setText(usersDetails.getWebsite());
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

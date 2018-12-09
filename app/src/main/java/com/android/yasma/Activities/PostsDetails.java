package com.android.yasma.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.android.yasma.Model.Comments;
import com.android.yasma.Model.PostsModel;
import com.android.yasma.Model.UsersDetails;
import com.android.yasma.R;
import com.android.yasma.Utils.RestClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsDetails extends AppCompatActivity {
    public static final String POSTS_DETAILS = "posts_details";
    public static final String USER_DETAILS = "user_details";
    PostsModel postsDetails;
    UsersDetails usersDetails;

    TextView username, datetime, postmsg, comment, poststitle;
    TextSwitcher likes;
    LinearLayout likeview, commentview;
    ImageView profilepic, like;
    ProgressDialog commentsDialog;
    List<Comments> commentsList;
    int dpWidth;
    LinearLayout commentlistlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();

        if (extras == null)
            return;

        postsDetails = (PostsModel) extras.get(POSTS_DETAILS);
        usersDetails = (UsersDetails) extras.get(USER_DETAILS);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        dpWidth = displayMetrics.widthPixels;


        setResources();

        getComments();

    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("");
    }

    private void getComments() {
        commentsDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        commentsDialog.setMessage("Loading Comments...");
        commentsDialog.show();

        Call<List<Comments>> get_users_list_call = RestClient.getInstance().getRetrofitInterface().getComments(postsDetails.getId());
        get_users_list_call.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                commentsList = response.body();


                if (commentsDialog.isShowing())
                    commentsDialog.dismiss();

                setData();

            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                System.out.println("Posts Details Error : " + t.getMessage());
                if (commentsDialog.isShowing())
                    commentsDialog.dismiss();

            }


        });

    }


    private void setData() {

        final float pwidth;


        pwidth = dpWidth * 0.12F;
        ConstraintLayout.LayoutParams imgParams = (ConstraintLayout.LayoutParams) profilepic.getLayoutParams();
        imgParams.width = (int) pwidth;
        imgParams.height = (int) pwidth;
        profilepic.setLayoutParams(imgParams);


        username.setText(usersDetails.getName());
        poststitle.setText(postsDetails.getTitle());
        postmsg.setText(postsDetails.getBody());
        comment.setText(commentsList.size() + " Comments");
        Glide.with(getApplicationContext())
                .load(R.drawable.default_user)
                .asBitmap()
                .placeholder(R.drawable.default_user)
                .error(R.drawable.default_user)
                .into(new BitmapImageViewTarget(profilepic) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(),
                                Bitmap.createScaledBitmap(resource, (int) pwidth, (int) pwidth, false));
                        drawable.setCircular(true);
                        profilepic.setImageDrawable(drawable);
                    }
                });
        addcommentlist(commentsList, dpWidth);

    }

    private void setResources() {
        username = (TextView) findViewById(R.id.pd_username);
        datetime = (TextView) findViewById(R.id.pd_datetime);
        postmsg = (TextView) findViewById(R.id.pd_postmsg);
        poststitle = (TextView) findViewById(R.id.pd_posttitle);
        likes = (TextSwitcher) findViewById(R.id.pd_likescount);
        comment = (TextView) findViewById(R.id.pd_commentscount);
        profilepic = (ImageView) findViewById(R.id.pd_profilepic);
        like = (ImageView) findViewById(R.id.pd_likes);
        likeview = (LinearLayout) findViewById(R.id.pd_likeview);
        commentlistlayout = (LinearLayout) findViewById(R.id.commentlistlayout);

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ud_intent = new Intent(PostsDetails.this, UserDetails.class);
                ud_intent.putExtra(UserDetails.USER_DETAILS, usersDetails);
                startActivity(ud_intent);
            }
        });

    }

    private void addcommentlist(List<Comments> com_list, float dpWidth) {
        commentlistlayout.removeAllViews();
        LayoutInflater lv = (LayoutInflater) getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < com_list.size(); i++) {
            final Comments comment = com_list.get(i);
            View child = lv.inflate(R.layout.comments_row, null);
            TextView username = (TextView) child.findViewById(R.id.cm_username);
            TextView postmsg = (TextView) child.findViewById(R.id.cm_postmsg);
            final ImageView profilepic = (ImageView) child.findViewById(R.id.cm_profilepic);

            // setting image width

            final float pwidth;


            pwidth = dpWidth * 0.12F;
            ConstraintLayout.LayoutParams imgParams = (ConstraintLayout.LayoutParams) profilepic.getLayoutParams();
            imgParams.width = (int) pwidth;
            imgParams.height = (int) pwidth;
            profilepic.setLayoutParams(imgParams);


            username.setText(comment.getName());
            postmsg.setText(comment.getBody());


            // Loading the Profile_Pic


            Glide.with(getApplicationContext())
                    .load(R.drawable.default_user)
                    .asBitmap()
                    .placeholder(R.drawable.default_user)
                    .error(R.drawable.default_user)
                    .into(new BitmapImageViewTarget(profilepic) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(),
                                    Bitmap.createScaledBitmap(resource, (int) pwidth, (int) pwidth, false));
                            drawable.setCircular(true);
                            profilepic.setImageDrawable(drawable);
                        }
                    });


            commentlistlayout.addView(child);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

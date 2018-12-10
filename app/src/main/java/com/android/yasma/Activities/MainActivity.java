package com.android.yasma.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.android.yasma.Adapter.PostsAdapter;
import com.android.yasma.Model.PostsModel;
import com.android.yasma.Model.UsersDetails;
import com.android.yasma.R;
import com.android.yasma.Utils.RestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ProgressDialog postsDialog, usersDialog;
    List<PostsModel> postsModelArrayList;
    List<UsersDetails> usersDetailsList;
    PostsAdapter postsAdapter;
    RecyclerView posts_lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setResources();

        getUsers();
        getPosts();
       /* postsAdapter = new PostsAdapter(this, postsModelArrayList);
        posts_lists.setAdapter(postsAdapter);*/
        posts_lists.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getUsers() {
        usersDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        usersDialog.setMessage("Loading Users...");
        usersDialog.show();

        Call<List<UsersDetails>> get_users_list_call = RestClient.getInstance().getRetrofitInterface().getUsers();
        get_users_list_call.enqueue(new Callback<List<UsersDetails>>() {
            @Override
            public void onResponse(Call<List<UsersDetails>> call, Response<List<UsersDetails>> response) {
                usersDetailsList = response.body();


                if (usersDialog.isShowing())
                    usersDialog.dismiss();


            }

            @Override
            public void onFailure(Call<List<UsersDetails>> call, Throwable t) {
                System.out.println("Posts Details Error : " + t.getMessage());
                if (usersDialog.isShowing())
                    usersDialog.dismiss();

            }


        });

    }

    private void setResources() {
        posts_lists = (RecyclerView) findViewById(R.id.posts_recycler);


    }

    private void getPosts() {
        postsDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        postsDialog.setMessage("Loading Feeds...");
        postsDialog.show();
        Call<List<PostsModel>> get_posts_list_call = RestClient.getInstance().getRetrofitInterface().getPosts();
        get_posts_list_call.enqueue(new Callback<List<PostsModel>>() {
            @Override
            public void onResponse(Call<List<PostsModel>> call, Response<List<PostsModel>> response) {
                postsModelArrayList = response.body();
                postsAdapter = new PostsAdapter(MainActivity.this, postsModelArrayList, usersDetailsList);
                posts_lists.setAdapter(postsAdapter);
                postsAdapter.notifyDataSetChanged();


                if (postsDialog.isShowing())
                    postsDialog.dismiss();


            }

            @Override
            public void onFailure(Call<List<PostsModel>> call, Throwable t) {
                System.out.println("Posts Details Error : " + t.getMessage());
                if (postsDialog.isShowing())
                    postsDialog.dismiss();

            }


        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("");
    }
}

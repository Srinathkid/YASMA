package com.android.yasma;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.yasma.Model.PostsModel;
import com.android.yasma.Utils.RestClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    PostsModel postsModel;
    List<PostsModel> postsModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPosts();
    }

    private void getPosts() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading List...");

        Call<List<PostsModel>> get_songs_list_call = RestClient.getInstance().getRetrofitInterface().getPosts();
        get_songs_list_call.enqueue(new Callback<List<PostsModel>>() {
            @Override
            public void onResponse(Call<List<PostsModel>> call, Response<List<PostsModel>> response) {
                postsModelArrayList = response.body();


                if (progressDialog.isShowing())
                    progressDialog.dismiss();
              

            }

            @Override
            public void onFailure(Call<List<PostsModel>> call, Throwable t) {
                System.out.println("Posts Details Error : " + t.getMessage());

            }

           /* @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("API Response Error : " + t.getMessage());
            }*/
        });


    }
}

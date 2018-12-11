package com.android.yasma.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.yasma.Adapter.AlbumListsAdapter;
import com.android.yasma.Adapter.PostsAdapter;
import com.android.yasma.Model.AlbumListsModel;
import com.android.yasma.Model.PostsModel;
import com.android.yasma.R;
import com.android.yasma.Utils.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumLists extends AppCompatActivity {

    RecyclerView album_lists;
    List<AlbumListsModel> albumLists;
    ProgressDialog albumDialog;
    AlbumListsAdapter albumListsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_lists);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setResources();
        getAlbums();
    }

    private void setResources() {
        album_lists = (RecyclerView) findViewById(R.id.albums_recycler);

    }

    private void getAlbums() {
        albumDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        albumDialog.setMessage("Loading Albums...");
        albumDialog.show();
        Call<List<AlbumListsModel>> get_posts_list_call = RestClient.getInstance().getRetrofitInterface().getAlbums();
        get_posts_list_call.enqueue(new Callback<List<AlbumListsModel>>() {
            @Override
            public void onResponse(Call<List<AlbumListsModel>> call, Response<List<AlbumListsModel>> response) {
                albumLists = response.body();

                System.out.println("Album Lists : " + albumLists.size());
               /* albumListsAdapter = new PostsAdapter(AlbumLists.this, albumLists);
                album_lists.setAdapter(albumListsAdapter);
                albumListsAdapter.notifyDataSetChanged();*/


                if (albumDialog.isShowing())
                    albumDialog.dismiss();


            }

            @Override
            public void onFailure(Call<List<AlbumListsModel>> call, Throwable t) {
                System.out.println("Posts Details Error : " + t.getMessage());
                if (albumDialog.isShowing())
                    albumDialog.dismiss();

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

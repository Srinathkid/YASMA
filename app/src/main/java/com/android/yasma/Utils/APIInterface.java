package com.android.yasma.Utils;

import com.android.yasma.Model.PostsModel;
import com.android.yasma.Model.UsersDetails;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIInterface {
    @GET("posts")
    Call<List<PostsModel>> getPosts();

    @GET("posts/{post_id}")
    Call<JsonObject> getPostsDetails();

    @GET("posts/{post_id}/comments")
    Call<JsonObject> getComments();

    @GET("users")
    Call<List<UsersDetails>> getUsers();
}

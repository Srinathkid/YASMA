package com.android.yasma.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.android.yasma.Model.PostsModel;
import com.android.yasma.R;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    List<PostsModel> postsLists;
    Context context;
    float dpWidth, Iwidth, pwidth;

    public PostsAdapter(Context context, List<PostsModel> postsLists) {
        this.postsLists = postsLists;
        this.context = context;
    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feeds_item, viewGroup, false);
        ImageView profilepic = (ImageView) itemView.findViewById(R.id.profilepic);

        // setting image width
        pwidth = dpWidth * 0.12F;
        ConstraintLayout.LayoutParams imgParams = (ConstraintLayout.LayoutParams) profilepic.getLayoutParams();
        imgParams.width = (int) pwidth;
        imgParams.height = (int) pwidth;
        profilepic.setLayoutParams(imgParams);

        return new PostsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder viewHolder, int i) {
        PostsModel postsModel = postsLists.get(i);

        viewHolder.username.setText(postsModel.getTitle());
        viewHolder.postmsg.setText(postsModel.getBody());
    }

    @Override
    public int getItemCount() {
        return postsLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView username, datetime, postmsg, comment;
        TextSwitcher likes;
        LinearLayout likeview, commentview;
        ImageView profilepic, like;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = (TextView) itemView.findViewById(R.id.username);
            datetime = (TextView) itemView.findViewById(R.id.datetime);
            postmsg = (TextView) itemView.findViewById(R.id.postmsg);
            likes = (TextSwitcher) itemView.findViewById(R.id.likescount);
            comment = (TextView) itemView.findViewById(R.id.commentscount);
            profilepic = (ImageView) itemView.findViewById(R.id.profilepic);
            like = (ImageView) itemView.findViewById(R.id.likes);
            likeview = (LinearLayout) itemView.findViewById(R.id.likeview);
            commentview = (LinearLayout) itemView.findViewById(R.id.commentview);
        }
    }
}

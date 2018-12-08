package com.android.yasma.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.android.yasma.Activities.PostsDetails;
import com.android.yasma.Model.PostsModel;
import com.android.yasma.Model.UsersDetails;
import com.android.yasma.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    List<PostsModel> postsLists;
    List<UsersDetails> usersDetailsList;
    Context context;
    float dpWidth, Iwidth, pwidth;

    public PostsAdapter(Context context, List<PostsModel> postsLists, List<UsersDetails> usersDetailsList) {
        this.postsLists = postsLists;
        this.context = context;
        this.usersDetailsList = usersDetailsList;
    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feeds_item, viewGroup, false);


        return new PostsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostsAdapter.ViewHolder viewHolder, int i) {
        final PostsModel postsModel = postsLists.get(i);
        UsersDetails usersDetails = null;
        for (UsersDetails ud : usersDetailsList) {
            if (ud.getId() == postsModel.getUserId()) {
                usersDetails = ud;
                break;
            }
        }
        String email = "";
       /* if (usersDetails.getEmail() == null || usersDetails.getEmail().equalsIgnoreCase(""))
            email = "example@sample.com";
        else
            email = usersDetails.getEmail();*/

        viewHolder.username.setText(usersDetails.getName());
        viewHolder.postmsg.setText(postsModel.getBody());
        viewHolder.poststitle.setText(postsModel.getTitle());


        final UsersDetails finalUsersDetails = usersDetails;
        viewHolder.card_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pd_inten = new Intent(context, PostsDetails.class);
                pd_inten.putExtra(PostsDetails.POSTS_DETAILS, postsModel);
                pd_inten.putExtra(PostsDetails.USER_DETAILS, finalUsersDetails);
                context.startActivity(pd_inten);
            }
        });


    }

    @Override
    public int getItemCount() {
        return postsLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView username, datetime, postmsg, comment, poststitle;
        TextSwitcher likes;
        LinearLayout likeview, commentview;
        ImageView profilepic, like;
        CardView card_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = (TextView) itemView.findViewById(R.id.username);
            datetime = (TextView) itemView.findViewById(R.id.datetime);
            postmsg = (TextView) itemView.findViewById(R.id.postmsg);
            poststitle = (TextView) itemView.findViewById(R.id.posttitle);
            likes = (TextSwitcher) itemView.findViewById(R.id.likescount);
            comment = (TextView) itemView.findViewById(R.id.commentscount);
            profilepic = (ImageView) itemView.findViewById(R.id.profilepic);
            like = (ImageView) itemView.findViewById(R.id.likes);
            likeview = (LinearLayout) itemView.findViewById(R.id.likeview);
            commentview = (LinearLayout) itemView.findViewById(R.id.commentview);
            card_layout = (CardView) itemView.findViewById(R.id.posts_card_layout);
        }
    }
}

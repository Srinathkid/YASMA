package com.android.yasma.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.yasma.Model.AlbumListsModel;
import com.android.yasma.R;

import java.util.List;

public class AlbumListsAdapter extends RecyclerView.Adapter<AlbumListsAdapter.ViewHolder> {
    Context context;
    List<AlbumListsModel> albumList;

    public AlbumListsAdapter(Context context, List<AlbumListsModel> albumList) {
        this.context = context;
        this.albumList = albumList;

    }

    @NonNull
    @Override
    public AlbumListsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.album_list_row, viewGroup, false);

        return new AlbumListsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumListsAdapter.ViewHolder viewHolder, int i) {
        AlbumListsModel albumListsModel = albumList.get(i);
//        viewHolder.user_name.setText(albumListsModel.getUserId().toString());
        viewHolder.album_name.setText(albumListsModel.getTitle());
        System.out.println("Album Lists Recycler : " + albumListsModel.getTitle());
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView user_name, album_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = (TextView) itemView.findViewById(R.id.al_user_name);
            album_name = (TextView) itemView.findViewById(R.id.al_album_name);

        }
    }
}

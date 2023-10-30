package com.sl1degod.kursovaya.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sl1degod.kursovaya.Models.Users;
import com.sl1degod.kursovaya.R;
import com.sl1degod.kursovaya.Viewmodels.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.AdapterViewHolder> {

    List<Users> usersList = new ArrayList<>();
    Context context;


    private UserViewModel viewModel;

    public UserAdapter(Context context) {
        this.context = context;
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_users_model, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bind(usersList.get(position));

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                context = view.getContext();
//                Intent intent = new Intent(context, PlayerActivity.class);
//                intent.putExtra("track_id", trackList.get(position).getId());
//                intent.putExtra("track_name", trackList.get(position).getTrack());
//                intent.putExtra("track_duration", trackList.get(position).getDuration());
//                intent.putExtra("artist_name", trackList.get(position).getArtist());
//                intent.putExtra("track_from", trackList.get(position).getAlbum());
//                intent.putExtra("track_image", trackList.get(position).getImage());
//                intent.putExtra("track_counts_playing", trackList.get(position).getCount_of_playing());
//                intent.putExtra("position", position);
//
//                context.startActivity(intent);
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        TextView id, first_name, second_name, last_name, post, login, password;
        ImageView image;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.setUserId);
            first_name = itemView.findViewById(R.id.setFirstName);
            second_name = itemView.findViewById(R.id.setSecondName);
            last_name = itemView.findViewById(R.id.setLastName);
            post = itemView.findViewById(R.id.setPost);
            login = itemView.findViewById(R.id.setLogin);
            password = itemView.findViewById(R.id.setPassword);
        }

        public void bind(Users users) {
//            id.setText(users.getId());
//            first_name.setText(users.getFirstName());
//            second_name.setText(users.getSecondName());
//            last_name.setText(users.getLastName());
//            post.setText(users.getPost());
//            login.setText(users.getLogin());
//            password.setText(users.getPassword());


//            Glide.with(context)
//                    .load(track.getImage())
//                    .centerCrop()
//                    .into(image);
        }
    }


}

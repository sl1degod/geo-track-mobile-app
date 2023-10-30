package com.sl1degod.kursovaya.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sl1degod.kursovaya.App;
import com.sl1degod.kursovaya.Models.Users;
import com.sl1degod.kursovaya.R;
import com.sl1degod.kursovaya.Viewmodels.ReportsViewModel;
import com.sl1degod.kursovaya.Viewmodels.UserViewModel;
import com.sl1degod.kursovaya.databinding.FragmentProfileBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private UserViewModel viewModel;

    FragmentProfileBinding binding;

    Context context;

    List<Users> usersList = new ArrayList<>();

    Users users;

    String id = App.getInstance().getUser_id();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        context = getContext();
        getUserData(id, rootView);
        return rootView;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void getUserData(String id, View view) {
        viewModel.getUsersData().observe(getViewLifecycleOwner(), user -> {
            if (user == null) {
                Toast.makeText(context, "Unluko", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, user.toString(), Toast.LENGTH_SHORT).show();

                setData(user, view);
            }
        });
        viewModel.getUser(id);
    }

    private void setData(Users user, View view) {
        TextView surname = view.findViewById(R.id.setSurname);
        TextView name = view.findViewById(R.id.setName);
        TextView post = view.findViewById(R.id.setPost);
        TextView login = view.findViewById(R.id.setLogin);
        TextView password = view.findViewById(R.id.setPassword);
        ImageView imageView = view.findViewById(R.id.setPhoto);
        surname.setText(user.getFirstname());
        name.setText(user.getSecondname());
        post.setText(user.getPost());
        login.setText(user.getLogin());
        password.setText(user.getPassword());

        Glide.with(context)
                    .load(user.getImage())
                    .centerCrop()
                    .into(imageView);
    }

}
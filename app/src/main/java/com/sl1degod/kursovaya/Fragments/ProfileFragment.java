package com.sl1degod.kursovaya.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sl1degod.kursovaya.Activity.LoginActivity;
import com.sl1degod.kursovaya.Adapters.ReportsAdapter;
import com.sl1degod.kursovaya.App;
import com.sl1degod.kursovaya.Models.Users;
import com.sl1degod.kursovaya.Network.RetrofitInstance;
import com.sl1degod.kursovaya.R;
import com.sl1degod.kursovaya.Viewmodels.ReportsViewModel;
import com.sl1degod.kursovaya.Viewmodels.UserViewModel;
import com.sl1degod.kursovaya.databinding.FragmentProfileBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileFragment extends Fragment {

    private UserViewModel viewModel;

    FragmentProfileBinding binding;

    Context context;

    List<Users> usersList = new ArrayList<>();

    Users users;

    String pathToImage = RetrofitInstance.getRetrofitInstance().baseUrl() + "static/users/";

    String id = App.getInstance().getUser_id();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        context = getContext();
        getUserData(id, rootView);
//        getUserData(String.valueOf(1), rootView);
        return rootView;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void getUserData(String id, View view) {
        viewModel.getUsersData().observe(getViewLifecycleOwner(), user -> {
            if (user == null) {
                Toast.makeText(context, "Unluko", Toast.LENGTH_SHORT).show();
            } else {
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

//        System.out.println(pathToImage + id);

        Glide.with(context)
                    .load(pathToImage + id + ".jpg")
                    .centerCrop()
                    .into(imageView);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_toolbar, menu);
        menu.setGroupVisible(R.id.homeGroup, false);
        menu.setGroupVisible(R.id.profile, true);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.exitProfile:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
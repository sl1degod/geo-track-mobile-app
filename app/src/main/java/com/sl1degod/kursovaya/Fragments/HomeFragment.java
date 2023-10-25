package com.sl1degod.kursovaya.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sl1degod.kursovaya.Adapters.UserAdapter;
import com.sl1degod.kursovaya.App;
import com.sl1degod.kursovaya.Models.Users;
import com.sl1degod.kursovaya.R;
import com.sl1degod.kursovaya.Viewmodels.UserViewModel;
import com.sl1degod.kursovaya.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private UserViewModel viewModel;
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    App app;

    List<Users> trackList = new ArrayList<>();

    Context context;
    FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        context = getContext();
        setHasOptionsMenu(true);
        binding.rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserAdapter(getContext());
        binding.rvUsers.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        getAllUsers();

        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getAllUsers() {
        viewModel.getUsersData().observe(getViewLifecycleOwner(), users -> {
            if (users == null) {
                Toast.makeText(context, "Unluko", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "LETS GOOO", Toast.LENGTH_SHORT).show();
                adapter.setUsersList(users);
                adapter.notifyDataSetChanged();
            }
        });
        viewModel.getAllUsers();
    }
}
package com.example.todotask.ui.home;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.todotask.R;
import com.example.todotask.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    TextView drawerUsernameView;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        drawerUsernameView=root.findViewById(R.id.drawerUsername);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginSharedPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "defaultValue");
        String pass = sharedPreferences.getString("password", "defaultValue");
        Log.d(TAG, "email: "+email.toString());
        Log.d(TAG, "pass: "+pass.toString());
   
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.example.bmlsalon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bmlsalon.databinding.MensHaircutBinding;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Mens_Haircut extends AppCompatActivity {
        private MensHaircutBinding binding;

        Button backtomain;

    private ArrayList<String> availableSlots;
    private GridView gridView;
    private TextView totalPriceTextView;
    private Button checkoutButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = MensHaircutBinding.inflate(getLayoutInflater());
            View view = binding.getRoot();
            setContentView(view);


            backtomain = binding.back; // Assuming binding is an instance of the appropriate binding class for the layout

            backtomain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.back:
                            // Create a new instance of the HomeFragment
                            HomeFragment homeFragment = new HomeFragment();

                            // Get the FragmentManager and start a FragmentTransaction
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            // Replace the current fragment (if any) with the HomeFragment
                            fragmentTransaction.replace(R.id.back, homeFragment); // R.id.container is the ID of the container in which the fragment will be displayed
                            fragmentTransaction.addToBackStack(null); // Add the transaction to the back stack, so the user can navigate back
                            fragmentTransaction.commit(); // Commit the transaction
                            break;
                }
            }
        });


    }
}

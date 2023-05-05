package com.example.bmlsalon;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.bmlsalon.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
//    SearchView searchView;
//    ListView myListView;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    TextView name;
    String greetingUser;

    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

//        searchView = binding.searchView;
//        myListView = binding.listView;

//        myListView.setVisibility(View.GONE);

//        arrayList = new ArrayList<>();
//        arrayList.add("Men's- Haircut");
//        arrayList.add("Men's- Beard Trims");
//        arrayList.add("Men's- Hair Wash");
//        arrayList.add("Men's- Hair Color");
//        arrayList.add("Men's- Hair Massage");
//        arrayList.add("Men's- Facial");
//        arrayList.add("Women's- Haircut");
//        arrayList.add("Women's- Hair Color");
//        arrayList.add("Women's- Hair Washing");
//        arrayList.add("Women's- Eyebrow");
//        arrayList.add("Women's- Waxing");
//        arrayList.add("Women's- Pedicures");
//        arrayList.add("Women's- Manicures");
//        arrayList.add("Women's- Facials");
//        arrayList.add("Women's- Hair Styling");

//        adapter =new ArrayAdapter<>(getContext() ,android.R.layout.simple_list_item_1,arrayList);
//        myListView.setAdapter(adapter);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                myListView.setVisibility(View.VISIBLE);
//                adapter.getFilter().filter(s);
//                return false;
//            }
//        });





        name = binding.retrivingusername;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String emailId = currentUser.getEmail();

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference userRef = rootRef.child("users");

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String email = dataSnapshot.child("email").getValue(String.class);
                        if (email != null && email.equals(emailId)) {
                            String username = dataSnapshot.child("username").getValue(String.class);
                            Log.d("firebase", "onDataChange: "+username);
                            binding.retrivingusername.setText("Hey "+username+"!");
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("firebase", "Error getting data: " + databaseError.getMessage());
                }
            });
        }


        CircleImageView myButton = binding.menhaircut;
                myButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.menhaircut:

                                Intent intent1 = new Intent(getActivity(), Mens_Haircut.class);
                                startActivity(intent1);
                               getActivity().overridePendingTransition(R .anim.slide_in, R.anim.slide_out);
                                break;

                            default:
                                break;
                        }
                    }
                });


                ImageSlider imageSlider = binding.imageSlider;
                ArrayList<SlideModel> slideModels = new ArrayList<>();

                slideModels.add(new SlideModel(R.drawable.slide3, ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.slide1, ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.slide4, ScaleTypes.FIT));

                imageSlider.setImageList(slideModels, ScaleTypes.FIT);



                return view;
            }



        }



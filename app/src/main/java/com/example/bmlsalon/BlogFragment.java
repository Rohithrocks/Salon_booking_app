package com.example.bmlsalon;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bmlsalon.Adapter.myAdapter;
import com.example.bmlsalon.Model.datamodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link BlogFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class BlogFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<datamodel> dataholder;
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public BlogFragment() {
//        // Required empty public constructor
//    }
//
//    // TODO: Rename and change types and number of parameters
//    public static BlogFragment newInstance(String param1, String param2) {
//        BlogFragment fragment = new BlogFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_blog, container, false);
        recyclerView = view.findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataholder = new ArrayList<>();

        datamodel ob1 = new datamodel(R.drawable.img,"Praneeth","Excellent Service Liked it!");
        dataholder.add(ob1);
        datamodel ob2 = new datamodel(R.drawable.img,"Praneeth","Excellent Service Liked it!");
        dataholder.add(ob2);
        datamodel ob3 = new datamodel(R.drawable.img,"Praneeth","Excellent Service Liked it!");
        dataholder.add(ob3);
        datamodel ob4 = new datamodel(R.drawable.img,"Praneeth","Excellent Service Liked it!");
        dataholder.add(ob4);
        datamodel ob5 = new datamodel(R.drawable.img,"Praneeth","Excellent Service Liked it!");
        dataholder.add(ob5);
        datamodel ob6 = new datamodel(R.drawable.img,"Praneeth","Excellent Service Liked it!");
        dataholder.add(ob6);

        recyclerView.setAdapter(new myAdapter(dataholder));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(), reviewEditPage.class);
                startActivity(intent1);
//                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });




                return view;

    }

    
}

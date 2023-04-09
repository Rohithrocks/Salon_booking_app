package com.example.bmlsalon;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.bmlsalon.databinding.ActivityMainBinding;
import com.example.bmlsalon.databinding.FragmentHomeBinding;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();






        CircleImageView myButton = binding.menhaircut;
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.menhaircut:

                        Intent intent1 = new Intent(getActivity(), Mens_Haircut.class);
                        startActivity(intent1);
                        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
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

        // Do any view initialization here
        return view;
    }
}

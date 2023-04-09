package com.example.bmlsalon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bmlsalon.databinding.MensHaircutBinding;

import de.hdodenhof.circleimageview.CircleImageView;

public class Mens_Haircut extends AppCompatActivity {
        private MensHaircutBinding binding;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Initialize the binding object
            binding = MensHaircutBinding.inflate(getLayoutInflater());
            View view = binding.getRoot();
            setContentView(view);

      ImageButton myButton = binding.back;

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.back:

                        Intent intent1 = new Intent(Mens_Haircut.this, HomeFragment.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                        break;

                    default:
                        break;
                }
            }
        });


    }
}

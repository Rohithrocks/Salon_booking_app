package com.example.bmlsalon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bmlsalon.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;


import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private @NonNull FragmentProfileBinding binding;

    TextView Username, email, logout;
    FirebaseAuth mAuth;

    private static final int REQUEST_PICK_IMAGE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;


    private Uri imageUri;
    private StorageReference storageReference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();





        mAuth= FirebaseAuth.getInstance();

        email = binding.emailAddress;
        Username = binding.username;
        logout=binding.logout;









        Username.setText(MainActivity.userName, TextView.BufferType.EDITABLE);
        email.setText(MainActivity.emailId, TextView.BufferType.EDITABLE);
        Log.d("TEST", " onCreateView: "+MainActivity.userName);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Are you sure you want to logout?")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        mAuth.signOut(); // Sign out the current user from Firebase Authentication
                                        startActivity(new Intent(getActivity(), Onboarding.class));
                                        getActivity().finish(); // Finish the current activity to prevent going back
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User cancelled the dialog, do nothing
                                    }
                                });
                        // Create the AlertDialog object and show it
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        break;
                }
            }
        });



        return view;
}



}

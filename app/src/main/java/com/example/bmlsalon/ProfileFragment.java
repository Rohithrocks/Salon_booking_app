package com.example.bmlsalon;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.bmlsalon.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private @NonNull
    FragmentProfileBinding binding;

    TextView Username, email, logout, callprofile;
    FirebaseAuth mAuth;
    private CircleImageView circleImageView;

    private static final int REQUEST_PICK_IMAGE = 1002;
    private static final int REQUEST_CAMERA = 1001;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    ProgressDialog pd;
    FirebaseAuth firebaseAuth;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        callprofile = view.findViewById(R.id.call_profile);

        mAuth = FirebaseAuth.getInstance();

        email = binding.emailAddress;
        Username = binding.username;
        logout = binding.logout;
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        // Set click listener for profile image
        binding.profileimg.setOnClickListener(v -> showImagePickerDialog());

// Load the image from the storage database
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/image.jpg");
        Glide.with(this /* Context */)
                .load(storageReference)
                .into(new CustomTarget<Drawable>() {

                          public void onResourceReady(@NonNull Drawable resource, @Nullable Transition transition) {
                              // Create a circular bitmap from the loaded image
                              BitmapDrawable bitmapDrawable = (BitmapDrawable) resource;
                              Bitmap bitmap = bitmapDrawable.getBitmap();
                              Bitmap circularBitmap = ImageUtils.getCircularBitmap(bitmap);

                              // Set the circular bitmap to the circular image view
                              binding.profileimg.setImageBitmap(circularBitmap);
                          }

                          @Override
                          public void onLoadFailed(@Nullable Drawable errorDrawable) {
                              // Handle error loading image
                          }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {

                    }

                    @Override
                          public void onLoadCleared(@Nullable Drawable placeholder) {
                              // Handle cleared image
                          }

                      });





        Username.setText(MainActivity.userName, TextView.BufferType.EDITABLE);
        email.setText(MainActivity.emailId, TextView.BufferType.EDITABLE);
        Log.d("TEST", " onCreateView: "+MainActivity.userName);

        callprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:7288918840"));
                startActivity(intent);
            }
        });

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

    private void updatePassword(String oldPassword, String newPassword) {
        pd.show();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(),oldPassword);
        user.reauthenticate(authCredential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        user.updatePassword(newPassword)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        pd.dismiss();
                                        Toast.makeText(getActivity(),"Password Updated...",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        pd.dismiss();
                                        Toast.makeText(getActivity(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getActivity(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }

    // Method to show image picker dialog or camera
    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose Image Source")
                .setItems(new CharSequence[]{"Gallery", "Camera"}, (dialog, which) -> {
                    if (which == 0) {
                        Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickImageIntent, REQUEST_PICK_IMAGE);
                    } else {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, REQUEST_CAMERA);
                    }
                });
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PICK_IMAGE && data != null) {
                Uri imageUri = data.getData();
                saveImageToStorage(imageUri);
            } else if (requestCode == REQUEST_CAMERA && data != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                Uri imageUri = getImageUri(getContext(),imageBitmap);
                saveImageToStorage(imageUri);
            }
        }
    }

    private void saveImageToStorage(Uri imageUri) {
        // Generate unique filename for the image
        String imageName = UUID.randomUUID().toString() + ".jpg";
        StorageReference imageRef = storageReference.child("images/" + imageName);

        // Compress the image to reduce file size
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imageUri);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        } catch (IOException e) {
            Log.e("ProfileFragment", "Failed to compress image: " + e.getMessage());
            return;
        }

        byte[] imageData = baos.toByteArray();
        UploadTask uploadTask = imageRef.putBytes(imageData);
        uploadTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Get the download URL of the uploaded image
                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    // Update the image URL in your app's preferences or database
                    String imageUrl = uri.toString();
                    // Update the image path in your app's preferences or database
                    // Example:
                    // String imagePath = imageFile.getAbsolutePath();
                    // saveImagePathToPreferences(imagePath);
                    Log.d("ProfileFragment", "Image URL: " + imageUrl);
                    // TODO: Update the image URL in your app's preferences or database
                });
            } else {
                Log.e("ProfileFragment", "Failed to upload image: " + task.getException().getMessage());
            }
        });
    }

        private Uri getImageUri(Context context,Bitmap imageBitmap) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), imageBitmap, "ProfileImage", null);
            return Uri.parse(path);
        }

}

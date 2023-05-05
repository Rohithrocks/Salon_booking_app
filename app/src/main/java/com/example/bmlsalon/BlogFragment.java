package com.example.bmlsalon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bmlsalon.Adapter.myAdapter;
import com.example.bmlsalon.Model.datamodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link BlogFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class BlogFragment extends Fragment {

    private static final String TAG = "ServiceReviewListFragment";

    private RecyclerView recyclerView;
    private ServiceReviewAdapter adapter;
    private List<ServiceReview> serviceReviews;
    private String userEmail;

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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        userEmail = sharedPreferences.getString("user_email", "");

        recyclerView = view.findViewById(R.id.service_review_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        serviceReviews = new ArrayList<>();
        adapter = new ServiceReviewAdapter(serviceReviews);
        recyclerView.setAdapter(adapter);
      Spinner spinner = new Spinner(getContext());



        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog();
            }
        });
        return view;

    }

    private void showConfirmationDialog() {
        // Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Your Review");


        // Add the service options dropdown
        Spinner serviceSpinner = new Spinner(getContext());



        // Add the comment and review fields
        EditText commentEditText = new EditText(getContext());
        commentEditText.setHint("Comment");
        EditText reviewEditText = new EditText(getContext());
        reviewEditText.setHint("Review");
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 32, 32, 32);
        layout.addView(serviceSpinner);

        layout.addView(commentEditText);
        layout.addView(reviewEditText);
        builder.setView(layout);





        // Add the OK button
        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Create a new ServiceReview object with the selected service, comment, and review
                String selectedService = (String) serviceSpinner.getSelectedItem();
                String comment = commentEditText.getText().toString();
                String review = reviewEditText.getText().toString();

                ServiceReview serviceReview = new ServiceReview(userEmail, selectedService, comment, review);

                // Add the new ServiceReview to the list and notify the adapter
                serviceReviews.add(serviceReview);
                adapter.notifyDataSetChanged();
            }
        });

        // Add the Cancel button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing
            }
        });

        // Show the dialog
        builder.show();
    }
    private class ServiceReviewAdapter extends RecyclerView.Adapter<ServiceReviewViewHolder> {
        private final List<ServiceReview> serviceReviews;

        public ServiceReviewAdapter(List<ServiceReview> serviceReviews) {
            this.serviceReviews = serviceReviews;
        }

        @NonNull
        @Override
        public ServiceReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_itemservicereview, parent, false);
            return new ServiceReviewViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ServiceReviewViewHolder holder, int position) {
            ServiceReview serviceReview = serviceReviews.get(position);


            holder.selectedServiceTextView.setText(serviceReview.getSelectedService());
            holder.userEmailTextView.setText(serviceReview.getUserEmail());
            holder.commentTextView.setText(serviceReview.getComment());

            holder.reviewTextView.setText(serviceReview.getReview());
        }


        @Override
        public int getItemCount() {
            return serviceReviews.size();
        }
    }

    private static class ServiceReviewViewHolder extends RecyclerView.ViewHolder {
        private final TextView userEmailTextView;
        private final TextView selectedServiceTextView;
        private final TextView commentTextView;
        private final TextView reviewTextView;

        public ServiceReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            userEmailTextView = itemView.findViewById(R.id.user_email_text_view);
            selectedServiceTextView = itemView.findViewById(R.id.selected_service_text_view);
            commentTextView = itemView.findViewById(R.id.comment_text_view);
            reviewTextView = itemView.findViewById(R.id.review_text_view);
        }
    }

    private static class ServiceReview {
        private final String userEmail;
        private final String selectedService;
        private final String comment;
        private final String review;

        public ServiceReview(String userEmail, String selectedService, String comment, String review) {
            this.userEmail = userEmail;
            this.selectedService = selectedService;
            this.comment = comment;
            this.review = review;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public String getSelectedService() {
            return selectedService;
        }

        public String getComment() {
            return comment;
        }

        public String getReview() {
            return review;
        }
    }


}

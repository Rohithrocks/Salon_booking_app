package com.example.bmlsalon;

import android.os.Bundle;
import androidx.annotation.Nullable;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bmlsalon.databinding.MensHaircutBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class Mens_Haircut extends AppCompatActivity {
    private MensHaircutBinding binding;

    Button backtomain;
    String selectedTimeSlot = ""; // Store the selected time slot
    Button bookAppointmentButton;

    String DataSelected;

    private TextView totalPriceTextView;
    private int totalPrice = 120;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MensHaircutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        bookAppointmentButton = findViewById(R.id.bookbutton); // Replace with the ID of your "Book Appointment" button
        bookAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if a date, time slot, and total price are selected
                if (!DataSelected.isEmpty() && !selectedTimeSlot.isEmpty() && totalPrice > 0) {
                    // Send the collected data to the database
                    // You can implement the logic to send data to the database here

                    // Show a Toast to indicate that the appointment is booked
                    Toast.makeText(Mens_Haircut.this, "Appointment booked! Date: " + DataSelected + ", Time: " + selectedTimeSlot + ", Total Price: " + totalPrice, Toast.LENGTH_SHORT).show();
                    sendDataToDatabase(DataSelected, selectedTimeSlot, totalPrice);
                } else {
                    // Show a Toast to indicate that some data is missing
                    Toast.makeText(Mens_Haircut.this, "Please select a date, time slot, and services", Toast.LENGTH_SHORT).show();

                }
            }
        });


        totalPriceTextView = findViewById(R.id.totalPriceTextView);

        Button beardTrimButton = findViewById(R.id.beardTrimButton);
        Button headMassageButton = findViewById(R.id.headMassageButton);
        Button hairWashButton = findViewById(R.id.hairWashButton);


        beardTrimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update total price
                totalPrice += 60;
                totalPriceTextView.setText("Rs. " + totalPrice);
                beardTrimButton.setEnabled(false); // Disable button after selection
            }
        });

        headMassageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update total price
                totalPrice += 120;
                totalPriceTextView.setText("Rs. " + totalPrice);
                headMassageButton.setEnabled(false); // Disable button after selection
            }
        });

        hairWashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update total price
                totalPrice += 30;
                totalPriceTextView.setText("Rs. " + totalPrice);
                hairWashButton.setEnabled(false); // Disable button after selection
            }
        });


        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                //do something
            }
        });


        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                DataSelected = DateFormat.format("yyyy-MM-dd", date).toString();


                // Show a Toast with the selected date
                Toast.makeText(getApplicationContext(), "Selected Date: " + DataSelected, Toast.LENGTH_SHORT).show();

                // Additional actions you want to perform with the selected date
                // ...
            }


            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView,
                                         int dx, int dy) {

            }


            @Override
            public boolean onDateLongClicked(Calendar date, int position) {
                return true;
            }
        });

        // Define an array of time slots
        String[] timeSlots = {
                "9:00 AM", "9:45 AM", "10:30 AM", "11:15 AM",
                "12:00 PM", "12:45 PM", "1:30 PM", "2:15 PM",
                "3:00 PM", "3:45 PM", "4:30 PM", "5:15 PM"
        };


        // Set up the GridView
        GridView gridView = findViewById(R.id.gridView); // Replace with the ID of your GridView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, timeSlots);
        gridView.setAdapter(adapter);

        // Set item click listener for the GridView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected time slot
                String clickedTimeSlot = timeSlots[position];

                // If a time slot is already selected, unselect it
                if (!selectedTimeSlot.isEmpty()) {
                    // If the clicked time slot is same as the selected time slot, unselect it
                    if (selectedTimeSlot.equals(clickedTimeSlot)) {
                        selectedTimeSlot = "";
                        view.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        return;
                    }
                }

                // Set the clicked time slot as selected
                selectedTimeSlot = clickedTimeSlot;

                // Perform the desired action with the selected time slot
                // For example, show a toast with the selected time slot
                Toast.makeText(Mens_Haircut.this, "Selected time: " + selectedTimeSlot, Toast.LENGTH_SHORT).show();

                // Update UI to visually highlight the selected time slot
                view.setBackgroundColor(getResources().getColor(R.color.black2));
                adapter.notifyDataSetChanged();
            }
        });


    }
    private void sendDataToDatabase(String selectedDate, String selectedTimeSlot, int totalPrice) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Appointments"); // Replace "Appointments" with the name of your Firebase database node

        // Create a new Appointment object with the selected date, selected time slot, and total price
        Appointment appointment = new Appointment(selectedDate, selectedTimeSlot, totalPrice);

        // Generate a new unique key for the appointment in Firebase database
        String appointmentId = databaseReference.push().getKey();

        // Set the appointment data to the generated key
        databaseReference.child(appointmentId).setValue(appointment);

        // Show a Toast to indicate that the data is sent to Firebase database
        Toast.makeText(getApplicationContext(), "Data sent to Firebase database", Toast.LENGTH_SHORT).show();
    }

}

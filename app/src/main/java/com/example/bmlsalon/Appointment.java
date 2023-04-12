package com.example.bmlsalon;

public class Appointment {
    private String selectedDate;
    private String selectedTimeSlot;
    private int totalPrice;

    // Empty constructor for Firebase
    public Appointment() {
    }

    // Constructor with parameters
    public Appointment(String selectedDate, String selectedTimeSlot, int totalPrice) {
        this.selectedDate = selectedDate;
        this.selectedTimeSlot = selectedTimeSlot;
        this.totalPrice = totalPrice;
    }

    // Getters and setters
    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getSelectedTimeSlot() {
        return selectedTimeSlot;
    }

    public void setSelectedTimeSlot(String selectedTimeSlot) {
        this.selectedTimeSlot = selectedTimeSlot;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}


package com.example.lab_02_submission;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter, suggestionsAdapter;
    ArrayList<String> dataList;
    Button addButton, deleteButton, confirmButton;
    AutoCompleteTextView editCityName;
    ArrayList<String> displayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        cityList = findViewById(R.id.city_list);
        editCityName = findViewById(R.id.editCityName);
        addButton = findViewById(R.id.Add_Button);
        deleteButton = findViewById(R.id.Delete_Button);
        confirmButton = findViewById(R.id.Confirm_Button);

        // Hide the EditText and Confirm button initially
        editCityName.setVisibility(View.GONE);
        confirmButton.setVisibility(View.GONE);

        // Initial data for the ListView
        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>(Arrays.asList(cities));

        displayList = new ArrayList<>();

        // Set up the ArrayAdapter with a simple layout
        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, displayList);
        cityList.setAdapter(cityAdapter);
        cityList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        suggestionsAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dataList);
        editCityName.setAdapter(suggestionsAdapter);

        // Add city to the display list
        addButton.setOnClickListener(view -> {
            editCityName.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.VISIBLE);
            confirmButton.setOnClickListener(confirmView -> {
                String cityName = editCityName.getText().toString().trim();
                if (!cityName.isEmpty()) {
                    if (!dataList.contains(cityName)) {
                        dataList.add(cityName);
                        displayList.add(cityName);
                        suggestionsAdapter.notifyDataSetChanged();
                    } else if (!displayList.contains(cityName)) {
                        displayList.add(cityName);
                    }
                    cityAdapter.notifyDataSetChanged();
                    editCityName.setText("");
                }
                editCityName.setVisibility(View.GONE);
                confirmButton.setVisibility(View.GONE);
            });
        });

        // Delete selected cities from the display list
        deleteButton.setOnClickListener(view -> {
            SparseBooleanArray selectedItems = cityList.getCheckedItemPositions();
            ArrayList<String> citiesToDelete = new ArrayList<>();

            for (int i = 0; i < selectedItems.size(); i++) {
                int position = selectedItems.keyAt(i);
                if (selectedItems.valueAt(i)) {
                    citiesToDelete.add(displayList.get(position));
                }
            }

            // Remove the selected cities from displayList
            displayList.removeAll(citiesToDelete);
            cityAdapter.notifyDataSetChanged();
            cityList.clearChoices();
        });
    }
}
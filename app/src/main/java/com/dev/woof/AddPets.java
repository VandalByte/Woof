package com.dev.woof;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddPets extends AppCompatActivity {
    EditText name_input, age_input, color_input, breed_input;
    Spinner gender_spinner, breed_spinner;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_pets);

        name_input = findViewById(R.id.name_input);
        age_input = findViewById(R.id.age_input);
        color_input = findViewById(R.id.color_input);
        breed_input = findViewById(R.id.breed_input);
        gender_spinner = findViewById(R.id.gender_spinner);
        breed_spinner = findViewById(R.id.breed_spinner);
        add_button = findViewById(R.id.add_button);

        // Set up the gender Spinner
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender_array, R.layout.spinner_item_gender);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_spinner.setAdapter(genderAdapter);
        gender_spinner.setSelection(0);

        // Set up the breed Spinner
        ArrayAdapter<CharSequence> breedAdapter = ArrayAdapter.createFromResource(this, R.array.breed_array, R.layout.spinner_item_breeds);
        breedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breed_spinner.setAdapter(breedAdapter);
        breed_spinner.setSelection(0);

        // Show/hide custom breed input based on spinner selection
        breed_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other")) {
                    breed_input.setVisibility(View.VISIBLE);
                } else {
                    breed_input.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                breed_input.setVisibility(View.GONE);
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = name_input.getText().toString().trim();
                String age = age_input.getText().toString().trim();
                String color = color_input.getText().toString().trim();
                String gender = gender_spinner.getSelectedItem().toString().trim();
                String breed;

                if (breed_spinner.getSelectedItem().toString().equals("Other")) {
                    breed = breed_input.getText().toString().trim();
                } else {
                    breed = breed_spinner.getSelectedItem().toString().trim();
                }

                if (name.isEmpty() || age.isEmpty() || color.isEmpty() || gender.equals("Gender") || breed.equals("Breed")) {
                    Toast.makeText(AddPets.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                PetsDatabase myDB = new PetsDatabase(AddPets.this);
                myDB.addPet(name, gender, Integer.parseInt(age), breed, color);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}

package com.dev.woof;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PetUpdateActivity extends AppCompatActivity {
    EditText name_input, breed_input, age_input, color_input, gender_input;
    Button update_button;

    String id, name, breed, age, color, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pet_update);

        name_input = findViewById(R.id.name_input2);
        breed_input = findViewById(R.id.breed_input2);
        age_input = findViewById(R.id.age_input2);
        color_input = findViewById(R.id.color_input2);
        gender_input = findViewById(R.id.gender_input2);
        update_button = findViewById(R.id.update_button);

        getAndSetIntentData();

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PetsDatabase myDB = new PetsDatabase(PetUpdateActivity.this);

                // Get the updated data from the input fields
                name = name_input.getText().toString();
                breed = breed_input.getText().toString();
                age = age_input.getText().toString();
                color = color_input.getText().toString();
                gender = gender_input.getText().toString();

                // Update the database
                myDB.updateData(id, name, gender, age, breed, color);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name")
                && getIntent().hasExtra("breed") && getIntent().hasExtra("age")
                && getIntent().hasExtra("color") && getIntent().hasExtra("gender")) {

            // Getting data from intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            breed = getIntent().getStringExtra("breed");
            age = getIntent().getStringExtra("age");
            color = getIntent().getStringExtra("color");
            gender = getIntent().getStringExtra("gender");

            // Strip "yrs" from age if present
            if (age.endsWith(" yrs")) {
                age = age.replace(" yrs", "");
            }

            // Setting Intent Data
            name_input.setText(name);
            breed_input.setText(breed);
            age_input.setText(age);  // Set numeric value only
            color_input.setText(color);
            gender_input.setText(gender);

        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
}

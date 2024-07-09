package com.dev.woof;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddPets extends AppCompatActivity {

    EditText name_input, gender_input, age_input, breed_input, weight_input, height_input, color_input;
    Button add_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_pets);


        name_input = findViewById(R.id.name_input);
        gender_input = findViewById(R.id.gender_input);
        age_input = findViewById(R.id.age_input);
        breed_input = findViewById(R.id.breed_input);
        weight_input = findViewById(R.id.weight_input);
        height_input = findViewById(R.id.height_input);
        color_input = findViewById(R.id.color_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PetsDatabase myDB = new PetsDatabase(AddPets.this);
                myDB.addPet(name_input.getText().toString().trim(),
                        gender_input.getText().toString().trim(),
                       Integer.valueOf( age_input.getText().toString().trim()),
                        breed_input.getText().toString().trim(),
                        Integer.valueOf(weight_input.getText().toString().trim()),
                        Integer.valueOf(height_input.getText().toString().trim()),
                        color_input.getText().toString().trim());
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
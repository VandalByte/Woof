package com.dev.woof;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.MyViewHolder> {
    private Context context;
    private Activity activity;
    private ArrayList<String> pet_id, pet_name, pet_gender, pet_age, pet_breed, pet_color;

    PetAdapter(Activity activity, Context context,
               ArrayList<String> pet_id,
               ArrayList<String> pet_name,
               ArrayList<String> pet_gender,
               ArrayList<String> pet_age,
               ArrayList<String> pet_breed,
               ArrayList<String> pet_color) {
        this.activity = activity;
        this.context = context;
        this.pet_id = pet_id;
        this.pet_name = pet_name;
        this.pet_gender = pet_gender;
        this.pet_age = pet_age;
        this.pet_breed = pet_breed;
        this.pet_color = pet_color;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_pets, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.pet_id_txt.setText(pet_id.get(position));
        holder.pet_name_txt.setText(pet_name.get(position));
        holder.pet_breed_txt.setText(pet_breed.get(position));
        holder.pet_age_txt.setText(pet_age.get(position));
        holder.pet_color_txt.setText(pet_color.get(position));
        holder.pet_gender_txt.setText(pet_gender.get(position));

        holder.mainLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, PetUpdateActivity.class);
            intent.putExtra("id", pet_id.get(position));
            intent.putExtra("name", pet_name.get(position));
            intent.putExtra("breed", pet_breed.get(position));
            intent.putExtra("age", pet_age.get(position));
            intent.putExtra("color", pet_color.get(position));
            intent.putExtra("gender", pet_gender.get(position));
            activity.startActivityForResult(intent, 2);
        });
    }

    @Override
    public int getItemCount() {
        return pet_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pet_id_txt, pet_name_txt, pet_breed_txt, pet_age_txt, pet_color_txt, pet_gender_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pet_id_txt = itemView.findViewById(R.id.pet_id_txt);
            pet_name_txt = itemView.findViewById(R.id.pet_name_txt);
            pet_breed_txt = itemView.findViewById(R.id.pet_breed_txt);
            pet_age_txt = itemView.findViewById(R.id.pet_age_txt);
            pet_color_txt = itemView.findViewById(R.id.pet_color_txt);
            pet_gender_txt = itemView.findViewById(R.id.pet_gender_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}

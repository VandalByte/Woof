package com.dev.woof

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PetsFragment : Fragment(R.layout.fragment_pets) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton

    private lateinit var myDB: PetsDatabase
    private lateinit var pet_id: ArrayList<String>
    private lateinit var pet_name: ArrayList<String>
    private lateinit var pet_gender: ArrayList<String>
    private lateinit var pet_age: ArrayList<String>
    private lateinit var pet_weight: ArrayList<String>
    private lateinit var pet_height: ArrayList<String>
    private lateinit var pet_breed: ArrayList<String>
    private lateinit var pet_color: ArrayList<String>

    private lateinit var petAdapter: PetAdapter

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        addButton = view.findViewById(R.id.add_button)

        addButton.setOnClickListener {
            val intent = Intent(activity, AddPets::class.java)
            startActivity(intent)
        }

        myDB = PetsDatabase(requireActivity())
        pet_id = ArrayList()
        pet_name = ArrayList()
        pet_gender = ArrayList()
        pet_age = ArrayList()
        pet_weight = ArrayList()
        pet_height = ArrayList()
        pet_breed = ArrayList()
        pet_color = ArrayList()

        storeDataInArrays()

        petAdapter = PetAdapter(activity, requireContext(), pet_id, pet_name, pet_gender, pet_age, pet_breed, pet_color)
        recyclerView.adapter = petAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Set the list of pet names in the SharedViewModel
        sharedViewModel.setPetNames(pet_name)
    }

    private fun storeDataInArrays() {
        val cursor = myDB.readAllData()
        if (cursor.count == 0) {
            Toast.makeText(requireContext(), "No data.", Toast.LENGTH_SHORT).show()
        } else {
            while (cursor.moveToNext()) {
                pet_id.add(cursor.getString(0))
                pet_name.add(cursor.getString(1))
                pet_gender.add(cursor.getString(2))
                pet_age.add(cursor.getString(3) + " yrs")
                pet_breed.add(cursor.getString(4))
                pet_color.add(cursor.getString(5))
            }
        }
    }
}

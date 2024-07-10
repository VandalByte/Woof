package com.dev.woof

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class HomeFragment: Fragment(R.layout.fragment_home) {

    private lateinit var myDB: PetsDatabase
    private lateinit var petsCountTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        petsCountTextView = view.findViewById(R.id.pets_count_text_view)

        myDB = PetsDatabase(requireActivity())
        val petsCount = myDB.getPetsCount()
        petsCountTextView.text = "Total Pets: $petsCount"
    }
}

package com.dev.woof

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var myDB: PetsDatabase
    private lateinit var petsCountTextView: TextView
    private lateinit var petNamesTextView: TextView
    private lateinit var timerInputEditText: EditText
    private lateinit var timerTextView: TextView
    private lateinit var startButton: Button
    private lateinit var pauseButton: Button
    private lateinit var stopButton: Button

    private var timer: CountDownTimer? = null
    private var timerRunning = false
    private var timeLeftInMillis: Long = 0

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        petsCountTextView = view.findViewById(R.id.pets_count_text_view)
        petNamesTextView = view.findViewById(R.id.pet_names_text_view)
        timerInputEditText = view.findViewById(R.id.timer_input_edit_text)
        timerTextView = view.findViewById(R.id.timer_text_view)
        startButton = view.findViewById(R.id.start_button)
        pauseButton = view.findViewById(R.id.pause_button)
        stopButton = view.findViewById(R.id.stop_button)

        myDB = PetsDatabase(requireActivity())
        val petsCount = myDB.getPetsCount()
        petsCountTextView.text = "Total Pets: $petsCount"

        sharedViewModel.petNames.observe(viewLifecycleOwner, { names ->
            val allNames = names.joinToString("\n\t ")
            petNamesTextView.text = "Pet Names:\n $allNames"
        })

        startButton.setOnClickListener {
            startTimer()
        }

        pauseButton.setOnClickListener {
            pauseTimer()
        }

        stopButton.setOnClickListener {
            stopTimer()
        }
    }

    private fun startTimer() {
        val input = timerInputEditText.text.toString().trim()
        if (input.isEmpty()) return

        val millisInput = input.toLong() * 60000 // Convert minutes to milliseconds
        setTime(millisInput)
        startCountDown()
    }

    private fun pauseTimer() {
        timer?.cancel()
        timerRunning = false
    }

    private fun stopTimer() {
        timer?.cancel()
        timerRunning = false
        setTime(0)
    }

    private fun setTime(milliseconds: Long) {
        timeLeftInMillis = milliseconds
        updateCountDownText()
    }

    private fun startCountDown() {
        timer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                timerRunning = false
                updateCountDownText()
            }
        }.start()

        timerRunning = true
    }

    private fun updateCountDownText() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60

        val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)
        timerTextView.text = "Time Left: $timeLeftFormatted"
    }
}

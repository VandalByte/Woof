package com.dev.woof

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _petNames = MutableLiveData<List<String>>()
    val petNames: LiveData<List<String>> get() = _petNames

    fun setPetNames(names: List<String>) {
        _petNames.value = names
    }
}

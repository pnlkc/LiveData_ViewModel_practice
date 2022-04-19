package com.example.livedata_viewmodel_tutorial_two

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class ActionType {
    PLUS, MINUS
}

class MyNumberViewModel : ViewModel() {

    private val _currentValue = MutableLiveData<Int>(0)
    val currentValue: LiveData<Int> = _currentValue

    fun updateValue(actionType: ActionType, input: Int) {
        when (actionType) {
            ActionType.PLUS -> {
                _currentValue.value = _currentValue.value?.plus(input)
            }
            ActionType.MINUS -> {
                _currentValue.value = _currentValue.value?.minus(input)
            }
        }
    }
}
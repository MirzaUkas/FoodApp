package com.mirz.foodapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetreward.ui.common.UiState
import com.mirz.foodapp.data.FoodRepository
import com.mirz.foodapp.model.Food
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailFoodViewModel(private val repository: FoodRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Food>>(UiState.Loading)
    val uiState: StateFlow<UiState<Food>>
        get() = _uiState

    fun getFood(foodId: Int) {
        viewModelScope.launch {
            repository.getFood(foodId)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { food ->
                    _uiState.value = UiState.Success(food)
                }
        }
    }
}
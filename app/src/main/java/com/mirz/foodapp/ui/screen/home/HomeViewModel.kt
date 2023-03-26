package com.mirz.foodapp.ui.screen.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetreward.ui.common.UiState
import com.mirz.foodapp.data.FoodRepository
import com.mirz.foodapp.model.Food
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val foodRepository: FoodRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Food>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Food>>>
        get() = _uiState
    private val _query = mutableStateOf("")
    val query: String get() = _query.value

    fun getFoods() {
        viewModelScope.launch {
            foodRepository.getFoods()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { foods ->
                    _uiState.value = UiState.Success(foods)
                }
        }
    }

    fun searchFoods(query: String) {
        _query.value = query
        viewModelScope.launch {
            foodRepository.searchFoods(query)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { foods ->
                    _uiState.value = UiState.Success(foods)
                }
        }
    }
}
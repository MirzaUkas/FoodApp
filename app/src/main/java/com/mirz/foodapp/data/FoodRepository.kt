package com.mirz.foodapp.data

import com.mirz.foodapp.model.Food
import com.mirz.foodapp.model.FoodData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FoodRepository {
    private val foods = mutableListOf<Food>()

    init {
        if (foods.isEmpty()) {
            foods.addAll(FoodData.foods)
        }
    }

    fun getFoods(): Flow<List<Food>> {
        return flowOf(foods)
    }

    fun getFood(id: Int): Flow<Food> {
        return flowOf(foods.first { it.id == id })
    }

    fun searchFoods(query: String): Flow<List<Food>> {
        return flowOf(foods.filter { it.name.contains(query, true) })
    }

    companion object {
        @Volatile
        private var instance: FoodRepository? = null

        fun getInstance(): FoodRepository =
            instance ?: synchronized(this) {
                FoodRepository().apply {
                    instance = this
                }
            }
    }
}
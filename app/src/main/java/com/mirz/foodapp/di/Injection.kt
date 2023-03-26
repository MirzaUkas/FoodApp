package com.mirz.foodapp.di

import com.mirz.foodapp.data.FoodRepository


object Injection {
    fun provideRepository(): FoodRepository {
        return FoodRepository.getInstance()
    }
}
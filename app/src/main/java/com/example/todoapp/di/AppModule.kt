package com.example.todoapp.di


import com.example.todoapp.presentation.viewmodel.SharedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SharedViewModel(get(), get()) }
    viewModel { SharedViewModel(get(), get()) }
}
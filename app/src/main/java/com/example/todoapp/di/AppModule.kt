package com.example.todoapp.di


import com.example.todoapp.presentation.viewmodel.ListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ListViewModel(get(), get()) }
    viewModel { SharedViewModel(get(), get()) }
}
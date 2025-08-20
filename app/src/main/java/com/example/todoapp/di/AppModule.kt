package com.example.todoapp.di

import com.example.todoapp.presentation.screens.list.ListViewModel
import com.example.todoapp.presentation.viewmodel.TaskViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ListViewModel(get(), get()) }
    viewModel { TaskViewModel(get()) }
}
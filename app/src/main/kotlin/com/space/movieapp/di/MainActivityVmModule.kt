package com.space.movieapp.di

import com.space.movieapp.ui.vm.MainActivityVm
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val mainActivityVmModule = module {
    viewModel<MainActivityVm> {
        MainActivityVm(networkObserver = get())
    }
}
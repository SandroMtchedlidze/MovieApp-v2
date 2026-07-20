package com.space.database.di

import com.space.domain.usecase.GetAllFavouritesIdsUseCase
import com.space.domain.usecase.GetAllFavouritesUseCase
import com.space.domain.usecase.IsFavouriteUseCase
import com.space.domain.usecase.ToggleFavouriteUseCase
import org.koin.dsl.module

val favouritesUseCaseModule = module {
    single { ToggleFavouriteUseCase(repository = get()) }
    single { GetAllFavouritesUseCase(repository = get()) }
    single { IsFavouriteUseCase(repository = get()) }
    single { GetAllFavouritesIdsUseCase(repository = get()) }
}
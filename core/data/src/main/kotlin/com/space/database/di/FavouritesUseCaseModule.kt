package com.space.database.di

import com.space.domain.usecase.GetAllFavouritesIdsUseCase
import com.space.domain.usecase.GetAllFavouritesUseCase
import com.space.domain.usecase.IsFavouriteUseCase
import com.space.domain.usecase.ToggleFavouriteUseCase
import org.koin.dsl.module

val favouritesUseCaseModule = module {
    factory { ToggleFavouriteUseCase(repository = get()) }
    factory { GetAllFavouritesUseCase(repository = get()) }
    factory { IsFavouriteUseCase(repository = get()) }
    factory { GetAllFavouritesIdsUseCase(repository = get()) }
}
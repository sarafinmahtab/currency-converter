package com.sarafinmahtab.currencyconverter.di

import com.sarafinmahtab.currencyconverter.data.repository.CurrencyRepository
import com.sarafinmahtab.currencyconverter.data.repository.CurrencyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

/**
 * @author Shamsul Arafin Mahtab
 * @since 29/06/21
 */

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class CurrencyModule {
    @Binds
    abstract fun bindsCurrencyRepository(currencyRepositoryImpl: CurrencyRepositoryImpl): CurrencyRepository
}

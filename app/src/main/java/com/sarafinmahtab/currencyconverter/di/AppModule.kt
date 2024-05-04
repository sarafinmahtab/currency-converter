package com.sarafinmahtab.currencyconverter.di

import com.sarafinmahtab.currencyconverter.data.ServerConstant
import com.sarafinmahtab.currencyconverter.data.source.remote.CurrencyApiService
import com.sarafinmahtab.currencyconverter.data.source.remote.RemoteDataSource
import com.sarafinmahtab.currencyconverter.data.source.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Shamsul Arafin Mahtab
 * @since 29/06/21
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindsRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    companion object {

        @Provides
        fun provideCurrencyApiService(
            okHttpClientBuilder: OkHttpClient.Builder,
            gsonConverterFactory: GsonConverterFactory
        ): CurrencyApiService {
            return Retrofit.Builder()
                .baseUrl(ServerConstant.BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClientBuilder.build())
                .build()
                .create(CurrencyApiService::class.java)
        }
    }
}

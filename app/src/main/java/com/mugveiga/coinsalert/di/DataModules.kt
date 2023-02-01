package com.mugveiga.coinsalert.di

import com.mugveiga.coinsalert.data.api.CoinGeckoClient
import com.mugveiga.coinsalert.data.api.CoinGeckoInterface
import com.mugveiga.coinsalert.modules.coinlist.CoinListNetworkDataSource
import com.mugveiga.coinsalert.modules.coinlist.CoinListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

  @Provides
  fun provideApiService(): CoinGeckoInterface = CoinGeckoClient.getClient()

  @Provides
  fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

}

package com.mugveiga.coinsalert.modules.coinlist

import androidx.lifecycle.LiveData
import com.mugveiga.coinsalert.data.api.CoinGeckoInterface
import com.mugveiga.coinsalert.data.model.Coin
import com.mugveiga.coinsalert.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class CoinListRepository(private val apiService: CoinGeckoInterface) {

  lateinit var coinListNetworkDataSource: CoinListNetworkDataSource

  fun getCoinList(compositeDisposable: CompositeDisposable): LiveData<List<Coin>> {
    coinListNetworkDataSource = CoinListNetworkDataSource(apiService, compositeDisposable)
    coinListNetworkDataSource.fetchCoinList()
    return coinListNetworkDataSource.coinListResponse
  }

  fun getCoinListNetworkState(): LiveData<NetworkState> {
    return coinListNetworkDataSource.networkState
  }
}

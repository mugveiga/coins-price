package com.mugveiga.coinsalert.modules.coinlist

import androidx.lifecycle.LiveData
import com.mugveiga.coinsalert.data.api.NetworkState
import com.mugveiga.coinsalert.data.model.Coin

class CoinListRepository(
  private val coinListNetworkDataSource: CoinListNetworkDataSource
) {

  fun getCoinList(): LiveData<List<Coin>> {
    coinListNetworkDataSource.fetchCoinList()
    return coinListNetworkDataSource.coinListResponse
  }

  fun getCoinListNetworkState(): LiveData<NetworkState> {
    return coinListNetworkDataSource.networkState
  }
}

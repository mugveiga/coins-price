package com.mugveiga.coinsalert.modules.coinlist

import androidx.lifecycle.LiveData
import com.mugveiga.coinsalert.data.api.NetworkState
import com.mugveiga.coinsalert.data.model.Coin
import javax.inject.Inject

class CoinListRepository @Inject constructor(
  private val coinListNetworkDataSource: CoinListNetworkDataSource
) {

  fun fetchCoinList(filter: String? = null) {
    coinListNetworkDataSource.fetchCoinList(filter)
  }

  fun getCoinList(): LiveData<List<Coin>> {
    return coinListNetworkDataSource.coinListResponse
  }

  fun getCoinListNetworkState(): LiveData<NetworkState> {
    return coinListNetworkDataSource.networkState
  }
}

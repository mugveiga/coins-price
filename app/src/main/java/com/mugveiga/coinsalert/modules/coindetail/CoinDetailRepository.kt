package com.mugveiga.coinsalert.modules.coindetail

import androidx.lifecycle.LiveData
import com.mugveiga.coinsalert.data.api.NetworkState
import com.mugveiga.coinsalert.data.model.CoinPrice
import javax.inject.Inject

class CoinDetailRepository @Inject constructor(
  private val coinDetailNetworkDataSource: CoinDetailNetworkDataSource
) {

  fun fetchCoinPrice(id: String) {
    coinDetailNetworkDataSource.fetchCoinPrice(id)
  }

  fun getCoinPrice(id: String): LiveData<CoinPrice> {
    coinDetailNetworkDataSource.fetchCoinPrice(id)
    return coinDetailNetworkDataSource.coinPriceResponse
  }

  fun getCoinDetailNetworkState(): LiveData<NetworkState> {
    return coinDetailNetworkDataSource.networkState
  }
}

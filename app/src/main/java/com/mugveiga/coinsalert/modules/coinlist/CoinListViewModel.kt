package com.mugveiga.coinsalert.modules.coinlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mugveiga.coinsalert.data.api.NetworkState
import com.mugveiga.coinsalert.data.model.Coin
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
  private val coinListRepository: CoinListRepository,
) : ViewModel() {

  fun refresh() {
    coinListRepository.fetchCoinList()
  }

  val coinList: LiveData<List<Coin>> by lazy {
    coinListRepository.getCoinList()
  }

  val networkState: LiveData<NetworkState> by lazy {
    coinListRepository.getCoinListNetworkState()
  }

}

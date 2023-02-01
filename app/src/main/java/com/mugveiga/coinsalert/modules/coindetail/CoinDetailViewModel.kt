package com.mugveiga.coinsalert.modules.coindetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mugveiga.coinsalert.data.api.NetworkState
import com.mugveiga.coinsalert.data.model.CoinPrice
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
  private val coinDetailRepository: CoinDetailRepository,
) : ViewModel() {

  lateinit var id: String

  val coinPrice: LiveData<CoinPrice> by lazy {
    coinDetailRepository.getCoinPrice(id)
  }

  val networkState: LiveData<NetworkState> by lazy {
    coinDetailRepository.getCoinDetailNetworkState()
  }

}

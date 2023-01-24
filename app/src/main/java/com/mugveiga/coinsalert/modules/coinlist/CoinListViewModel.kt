package com.mugveiga.coinsalert.modules.coinlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mugveiga.coinsalert.data.model.Coin
import com.mugveiga.coinsalert.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class CoinListViewModel(private val coinListRepository: CoinListRepository) : ViewModel() {

  private val compositeDisposable = CompositeDisposable()

  val coinList: LiveData<List<Coin>> by lazy {
    coinListRepository.getCoinList(compositeDisposable)
  }

  val networkState: LiveData<NetworkState> by lazy {
    coinListRepository.getCoinListNetworkState()
  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.dispose()
  }
}

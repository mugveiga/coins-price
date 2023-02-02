package com.mugveiga.coinsalert.modules.coinlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mugveiga.coinsalert.data.api.NetworkState
import com.mugveiga.coinsalert.data.model.Coin
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
  private val coinListRepository: CoinListRepository,
) : ViewModel() {

  init {
    refresh()
  }

  fun refresh() {
    coinListRepository.fetchCoinList()
  }

  fun filterCoins(query: Observable<String>) {
    query
      .debounce(1500, TimeUnit.MILLISECONDS)
      .filter { text -> text.isNotEmpty() && text.length >= 3 }
      .map { text -> text.lowercase().trim() }
      .distinctUntilChanged()
      .switchMap { s -> Observable.just(s) }
      .subscribe {
        coinListRepository.fetchCoinList(it)
      }
    query
      .filter { text -> text.isEmpty() }
      .subscribe {
        coinListRepository.fetchCoinList()
      }
  }

  val coinList: LiveData<List<Coin>> = coinListRepository.getCoinList()

  val networkState: LiveData<NetworkState> by lazy {
    coinListRepository.getCoinListNetworkState()
  }

}

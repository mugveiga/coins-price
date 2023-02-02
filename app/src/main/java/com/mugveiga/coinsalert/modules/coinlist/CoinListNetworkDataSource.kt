package com.mugveiga.coinsalert.modules.coinlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mugveiga.coinsalert.data.api.CoinGeckoInterface
import com.mugveiga.coinsalert.data.api.NetworkState
import com.mugveiga.coinsalert.data.model.Coin
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CoinListNetworkDataSource @Inject constructor(
  private val apiService: CoinGeckoInterface,
  private val compositeDisposable: CompositeDisposable
) {

  private val _networkState = MutableLiveData<NetworkState>()
  val networkState: LiveData<NetworkState>
    get() = _networkState

  private val _coinListResponseFull = MutableLiveData<List<Coin>>()
  private val _coinListResponse = MutableLiveData<List<Coin>>()
  val coinListResponse: LiveData<List<Coin>>
    get() = _coinListResponse

  fun fetchCoinList(filter: String? = null) {
    _coinListResponseFull.value?.apply {
      _coinListResponse.postValue(if (filter == null) this else this.filter { coin ->
        coin.name.lowercase().contains(filter)
      })
    }

    if (_coinListResponseFull.value == null) {
      _networkState.postValue(NetworkState.LOADING)
      try {
        compositeDisposable.add(
          apiService.getCoinsList()
            .subscribeOn(Schedulers.io())
            .subscribe(
              {
                _coinListResponseFull.postValue(it)
                _coinListResponse.postValue(it)
                _networkState.postValue(NetworkState.LOADED)
              },
              {
                _networkState.postValue(NetworkState.customError(it.message))
              }
            )
        )
      } catch (e: Exception) {
        _networkState.postValue(NetworkState.ERROR)
        NetworkState.customError(e.message)
      }
    }
  }
}

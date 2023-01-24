package com.mugveiga.coinsalert.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mugveiga.coinsalert.data.api.CoinGeckoInterface
import com.mugveiga.coinsalert.data.model.Coin
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CoinListNetworkDataSource(
  private val apiService: CoinGeckoInterface,
  private val compositeDisposable: CompositeDisposable
) {

  private val _networkState = MutableLiveData<NetworkState>()
  val networkState: LiveData<NetworkState>
    get() = _networkState

  private val _coinListResponse = MutableLiveData<List<Coin>>()
  val coinListResponse: LiveData<List<Coin>>
    get() = _coinListResponse

  fun fetchCoinList() {
    _networkState.postValue(NetworkState.LOADING)
    try {
      compositeDisposable.add(
        apiService.getCoinsList()
          .subscribeOn(Schedulers.io())
          .subscribe(
            {
              _coinListResponse.postValue(it)
              _networkState.postValue(NetworkState.LOADED)
            },
            {
              _networkState.postValue(NetworkState.ERROR)
              NetworkState.customError(it.message)
            }
          )
      )
    } catch (e: Exception) {
      _networkState.postValue(NetworkState.ERROR)
      NetworkState.customError(e.message)
    }
  }
}

package com.mugveiga.coinsalert.modules.coindetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mugveiga.coinsalert.data.api.CoinGeckoInterface
import com.mugveiga.coinsalert.data.model.Coin
import com.mugveiga.coinsalert.data.api.NetworkState
import com.mugveiga.coinsalert.data.model.CoinPrice
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CoinDetailNetworkDataSource @Inject constructor(
  private val apiService: CoinGeckoInterface,
  private val compositeDisposable: CompositeDisposable
) {

  private val _networkState = MutableLiveData<NetworkState>()
  val networkState: LiveData<NetworkState>
    get() = _networkState

  private val _coinPriceResponse = MutableLiveData<CoinPrice>()
  val coinPriceResponse: LiveData<CoinPrice>
    get() = _coinPriceResponse

  fun fetchCoinPrice(id: String) {
    _networkState.postValue(NetworkState.LOADING)
    try {
      compositeDisposable.add(
        apiService.getCoinPrice(id)
          .subscribeOn(Schedulers.io())
          .subscribe(
            {
              _coinPriceResponse.postValue(it)
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

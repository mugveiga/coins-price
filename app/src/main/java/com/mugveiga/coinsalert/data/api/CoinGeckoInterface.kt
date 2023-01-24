package com.mugveiga.coinsalert.data.api

import com.mugveiga.coinsalert.data.model.Coin
import com.mugveiga.coinsalert.data.model.CoinPrice
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoInterface {

    @GET("api/v3/coins/list?include_platform=false")
    fun getCoinsList(): Single<List<Coin>>

    @GET("api/v3/simple/price?vs_currencies=usd&include_market_cap=true&include_24hr_vol=true&include_24hr_change=true&include_last_updated_at=true&precision=2")
    fun getCoinPrice(@Query("ids") id: String): Single<CoinPrice>

}

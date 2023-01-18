package com.mugveiga.coinsalert.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class CoinPrice(

    val id: String,
    val usd: Double,

    @SerializedName("usd_market_cap")
    val usdMarketCap: Double,

    @SerializedName("usd_24h_vol")
    val usd24hVol: Double,

    @SerializedName("usd_24h_change")
    val usd24hChange: Double,

    val lastUpdatedAt: LocalDate

//    {
//        "bitcoin": {
//        "usd": 21214.4,
//        "usd_market_cap": 408709187499.89185,
//        "usd_24h_vol": 32615276036.079323,
//        "usd_24h_change": 0.09002933426232895,
//        "last_updated_at": 1674035404
//         }
//    }
)

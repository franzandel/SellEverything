package com.franzandel.selleverything.features.home.data

import com.franzandel.selleverything.data.entity.Product
import com.franzandel.selleverything.external.Result
import com.franzandel.selleverything.external.suspendTryCatch
import com.squareup.moshi.Moshi

class HomeRepositoryImpl(private val homeNetworkService: HomeNetworkService) : HomeRepository {

    override suspend fun getProducts(): Result<List<Product>> =
        suspendTryCatch {
            val getProductsResponse = homeNetworkService.getProducts()

            with(getProductsResponse) {
                val responseBody = body()
                if (isSuccessful && responseBody != null) {
                    val moshi = Moshi.Builder().build()

//                    val type = Types.newParameterizedType(BaseBean::class.java, InfoData::class.java)
//                    val jsonAdapter: JsonAdapter<BaseBean<InfoData>> = moshi.adapter(type)
//                    val baseBean = jsonAdapter.fromJson(jsonStr)!!

                    Result.Success(responseBody.result)
                } else {
                    val errorBody = errorBody() ?: throw Exception("UNKNOWN_ERROR")
                    val moshi = Moshi.Builder().build()
                    val jsonAdapter = moshi.adapter(Any::class.java)
                    val parsedError = jsonAdapter.fromJson(errorBody.source())
                    val exception = Exception(parsedError.toString())

                    Result.Error(
                        error = exception,
                        errorCode = code()
                    )
                }
            }
        }
}
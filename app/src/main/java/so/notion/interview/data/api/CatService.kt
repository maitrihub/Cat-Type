package so.notion.interview.data.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import so.notion.interview.data.model.CatResponse

interface CatService {
    @GET("breeds/search")
    fun getBreeds(@Query("q") search: String): Single<List<CatResponse>>
}
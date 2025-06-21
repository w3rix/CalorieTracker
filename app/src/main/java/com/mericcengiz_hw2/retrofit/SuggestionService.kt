package com.mericcengiz_hw2.retrofit

import com.mericcengiz_hw2.model.Suggestion
import retrofit2.http.GET

interface SuggestionService {
    @GET("75cf0b63-5b1e-4a30-98ec-e3e72f453f27")
    suspend fun getSuggestions(): List<Suggestion>
}


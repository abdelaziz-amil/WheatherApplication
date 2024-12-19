package com.android.ubo.androidweatherubo.data.repository

import com.android.ubo.androidweatherubo.data.vo.FavCity
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepositoryInterface {
    val favsCities : Flow<List<FavCity>>

    suspend fun addOrRemoveFavCity(favCity : FavCity)

    suspend fun isInFavs(favCity : FavCity): Flow<Boolean>
}
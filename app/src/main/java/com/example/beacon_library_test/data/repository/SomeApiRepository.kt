package com.example.beacon_library_test.data.repository

import com.example.beacon_library_test.data.api.SomeIntercomApi
import com.example.beacon_library_test.data.models.Resource
import com.example.beacon_library_test.data.models.TokenWithAccessEnd
import com.example.beacon_library_test.data.utils.DateTimeFormatter
import com.example.beacon_library_test.utils.UserTokenStoragePreferencesUtils
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SomeApiRepository @Inject constructor(
    private val SomeApi: SomeIntercomApi,
    private val sharedPreferencesUtils: UserTokenStoragePreferencesUtils,
    private val dateTimeFormatter: DateTimeFormatter
) {

    suspend fun auth(username: String, password: String) =
        flow {
            SomeApi.auth(username, password)
                .also {
                    emit(Resource.success(it))
                    val token = TokenWithAccessEnd(
                        it.token,
                        it.endTime
                    )
                    sharedPreferencesUtils.writeNewToken(
                        token
                    )
                }
        }.onStart {
            emit(Resource.loading())
        }.catch { e ->
            emit(Resource.error(null, e.localizedMessage ?: "error occured"))
        }

    suspend fun getIntercoms() =
        flow {
            SomeApi.getRelays(pageSize = 10, buildingIds = "")
                .also {
                    emit(Resource.success(it))
                }
        }.onStart {
            emit(Resource.loading())
        }.catch { e ->
            emit(Resource.error(null, e.localizedMessage ?: "error occured"))
        }


    fun isTokenExpiredCheck(): Boolean {
        val token = sharedPreferencesUtils.getToken() ?: return true
        return dateTimeFormatter.stringToDateTime(token.accessEnd) < dateTimeFormatter.getMoscowTime()
    }

    fun openIntercom(intercomId: String) =
        flow {
            SomeApi.openIntercom(intercomId)
                .also {
                    emit(Resource.success(it))
                }
        }.onStart {
            emit(Resource.loading())
        }.catch { e ->
            emit(Resource.error(null, e.localizedMessage ?: "error occured"))
        }

}
package com.example.beacon_library_test.presenter.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beacon_library_test.data.models.IntercomRelays
import com.example.beacon_library_test.data.models.Resource
import com.example.beacon_library_test.domain.useCases.interfaces.GetIntercomRelaysUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiViewModel @Inject constructor(
    private val intercomRelaysUseCase: GetIntercomRelaysUseCase
) : ViewModel() {

    val intercomLiveData = MutableLiveData<Resource<List<IntercomRelays>>>(null)

    fun getIntercoms() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                intercomRelaysUseCase().collect {
                    intercomLiveData.postValue(it)
                }
            }
        }
    }
}
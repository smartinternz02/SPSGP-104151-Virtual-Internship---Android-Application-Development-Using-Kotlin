
package com.example.amphibians.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amphibians.network.Amphibian
import com.example.amphibians.network.AmphibianApi
import kotlinx.coroutines.launch
import java.lang.Exception

enum class AmphibianApiStatus {LOADING, ERROR, DONE}

class AmphibianViewModel : ViewModel() {


    private val _status = MutableLiveData<AmphibianApiStatus>()



    private val _amphibians = MutableLiveData<List<Amphibian>>()
    private val _description = MutableLiveData<List<Amphibian>>()


    val status: LiveData<AmphibianApiStatus> = _status
    val amphibians: LiveData<List<Amphibian>> = _amphibians
    val description: LiveData<List<Amphibian>> = _description



    private val _amphibian = MutableLiveData<Amphibian>()
    val amphibian:LiveData<Amphibian> = _amphibian



     fun getAmphibianList(){
        _status.value = AmphibianApiStatus.LOADING
        try{
            viewModelScope.launch {
                _amphibians.value = AmphibianApi.retrofitService.getAmphibians()
            }
        }catch(e:Exception){
            _status.value = AmphibianApiStatus.ERROR
            _amphibians.value = emptyList()
        }
    }

    fun onAmphibianClicked(amphibian: Amphibian) {
            _amphibian.value = amphibian //single amphibian clicked on
    }
}

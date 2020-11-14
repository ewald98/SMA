package ro.upt.ac.chiuitter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ro.upt.ac.chiuitter.data.ChiuitRepository
import ro.upt.ac.chiuitter.domain.Chiuit

class HomeViewModel(private val chiuitRepository: ChiuitRepository) : ViewModel() {

    val chiuitsLiveData = MutableLiveData<List<Chiuit>>()

    fun fetchChiuits() {
        GlobalScope.launch {
            val chiuits = chiuitRepository.getAll()
            chiuitsLiveData.postValue(chiuits)
        }
    }

    fun addChiuit(description: String) {
        GlobalScope.launch {
            chiuitRepository.addChiuit(Chiuit(System.currentTimeMillis(), description))
            fetchChiuits()
        }
    }

    fun removeChiuit(chiuit: Chiuit) {
        GlobalScope.launch {
            chiuitRepository.removeChiuit(chiuit)
            fetchChiuits()
        }
    }

}
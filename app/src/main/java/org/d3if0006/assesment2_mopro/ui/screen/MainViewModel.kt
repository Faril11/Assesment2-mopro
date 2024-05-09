package org.d3if0006.assesment2_mopro.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.d3if0006.assesment2_mopro.database.OutfitsDao
import org.d3if0006.assesment2_mopro.model.Outfits

class MainViewModel(val dao: OutfitsDao) : ViewModel() {

    val data: StateFlow<List<Outfits>> = dao.getOutfits().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    suspend fun getOutfitsById(id: Long): Outfits? {
        return dao.getOutfitsById(id)
    }

    fun insert(nama: String, tinggi: String, berat: String) {
        val outfits = Outfits(
            nama = nama,
            tinggi = tinggi,
            berat = berat
        )
        viewModelScope.launch {
            dao.insert(outfits)
        }

    }

    fun update(nama : String, tinggi: String, berat: String, id : Long){
        val outfits = Outfits(
            id = id,
            nama = nama,
            tinggi = tinggi,
            berat = berat
        )
        viewModelScope.launch {
            dao.update(outfits)
        }
    }

    fun deleteOutfitsDaoById(id: Long){
        viewModelScope.launch {
            dao.deleteOutfitsById(id)
        }
    }

}
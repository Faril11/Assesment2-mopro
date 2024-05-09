package org.d3if0006.assesment2_mopro.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if0006.assesment2_mopro.database.OutfitsDao
import org.d3if0006.assesment2_mopro.ui.screen.MainViewModel


class ViewModelFactory(
    private val dao: OutfitsDao
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
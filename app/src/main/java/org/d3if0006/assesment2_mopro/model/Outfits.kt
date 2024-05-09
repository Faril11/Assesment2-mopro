package org.d3if0006.assesment2_mopro.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Outfits")
data class Outfits(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val tinggi: String,
    val berat: String
)
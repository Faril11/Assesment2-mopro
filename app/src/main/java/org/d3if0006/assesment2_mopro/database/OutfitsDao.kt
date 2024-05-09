package org.d3if0006.assesment2_mopro.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if0006.assesment2_mopro.model.Outfits

@Dao
interface OutfitsDao {

    @Insert
    suspend fun insert(outfits: Outfits)

    @Update
    suspend fun update(outfits: Outfits)

    // di urutkan berdasarkan alfabet nama
    @Query("SELECT * FROM Outfits ORDER BY nama")
    fun getOutfits(): Flow<List<Outfits>>

    @Query("SELECT * FROM Outfits WHERE id = :id")
    suspend fun getOutfitsById(id: Long): Outfits?

    @Query("DELETE FROM Outfits WHERE id = :id")
    suspend fun deleteOutfitsById(id: Long)
}

package com.example.forage.data

import androidx.room.*
import com.example.forage.model.Forageable
import kotlinx.coroutines.flow.Flow


@Dao
interface ForageableDao {

    // All rows in the database.
    @Query("SELECT * FROM forageable_database")
    fun getForagable() : Flow<List<Forageable>>

    // Review what a flow is.
    @Query("SELECT * FROM forageable_database WHERE id = :thisId")
    fun getForageable(thisId: Long) : Flow<Forageable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forageable: Forageable)

    @Update
    fun update(forageable: Forageable)

    @Delete
    fun delete(forageable: Forageable)

}

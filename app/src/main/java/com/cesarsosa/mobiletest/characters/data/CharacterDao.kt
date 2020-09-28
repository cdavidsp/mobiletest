package com.cesarsosa.mobiletest.characters.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters ORDER BY id DESC")
    fun getCharacters(): LiveData<List<Character>>

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacter(id: String): LiveData<Character>


    @Query("SELECT * FROM characters ORDER BY id DESC")
    fun getPagedCharacter(): DataSource.Factory<Int, Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Character>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: Character)
}
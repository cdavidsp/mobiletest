package com.cesarsosa.mobiletest.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cesarsosa.mobiletest.characters.data.CharacterDao
import com.cesarsosa.mobiletest.util.getValue
import com.cesarsosa.mobiletest.util.testCharacterA
import com.cesarsosa.mobiletest.util.testCharacterB
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterDaoTest : DbTest() {
    private lateinit var characterDao: CharacterDao
    private val characterA = testCharacterA.copy()
    private val characterB = testCharacterB.copy()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun createDb() {
        characterDao = db.characterDao()

        runBlocking {
            characterDao.insertAll(listOf(characterA, characterB))
        }
    }

    @Test fun testGetCharacters() {
        val list = getValue(characterDao.getCharacters())
        assertThat(list.size, equalTo(2))

        // Ensure Character list is sorted by name
        assertThat(list[0].id, equalTo(characterB.id))
        assertThat(list[1].id, equalTo(characterA.id))
    }

    @Test fun testGetCharacter() {
        assertThat(getValue(characterDao.getCharacter(characterA.id)), equalTo(characterA))
    }
}
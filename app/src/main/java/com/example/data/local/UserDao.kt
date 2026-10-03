package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfileFlow(): Flow<UserProfile?>

    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    suspend fun getUserProfile(): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: UserProfile)

    @Query("SELECT * FROM lesson_attempts ORDER BY timestamp DESC LIMIT 50")
    fun getRecentAttempts(): Flow<List<LessonAttempt>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempt(attempt: LessonAttempt)

    @Query("SELECT * FROM weak_vocabulary WHERE languageCode = :lang ORDER BY errorCount DESC LIMIT 30")
    fun getWeakWords(lang: String): Flow<List<WeakVocabulary>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeakWord(word: WeakVocabulary)

    @Query("DELETE FROM weak_vocabulary WHERE languageCode = :lang AND word = :word")
    suspend fun removeWeakWord(lang: String, word: String)

    @Query("UPDATE user_profile SET hearts = :hearts WHERE id = 1")
    suspend fun updateHearts(hearts: Int)

    @Query("UPDATE user_profile SET xp = xp + :gainedXp, gems = gems + :gainedGems WHERE id = 1")
    suspend fun addRewards(gainedXp: Int, gainedGems: Int)
}

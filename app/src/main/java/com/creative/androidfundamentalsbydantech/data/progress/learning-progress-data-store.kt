@file:JvmName("LearningProgressDataStoreKt")

package com.creative.androidfundamentalsbydantech.data.progress

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import java.io.IOException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.learningProgressDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "learning_progress",
    corruptionHandler = ReplaceFileCorruptionHandler {
        emptyPreferences()
    },
)

@Singleton
class LearningProgressDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val keyCompletedLessonIds = stringSetPreferencesKey("completed_lesson_ids")
    private val keyLastLessonId = stringPreferencesKey("last_lesson_id")

    val progress: Flow<LearningProgressState> = context.learningProgressDataStore.data
        .catch { error ->
            if (error is IOException || error is CorruptionException) {
                emit(emptyPreferences())
            } else {
                throw error
            }
        }
        .map { prefs ->
            LearningProgressState(
                completedLessonIds = prefs[keyCompletedLessonIds].orEmpty(),
                lastLessonId = prefs[keyLastLessonId],
            )
        }

    suspend fun setLastLesson(lessonId: String) {
        updateProgress {
            context.learningProgressDataStore.edit { prefs -> prefs[keyLastLessonId] = lessonId }
        }
    }

    suspend fun markCompleted(lessonId: String) {
        updateProgress {
            context.learningProgressDataStore.edit { prefs ->
                prefs[keyCompletedLessonIds] = prefs[keyCompletedLessonIds].orEmpty() + lessonId
                prefs[keyLastLessonId] = lessonId
            }
        }
    }

    private suspend fun updateProgress(block: suspend () -> Unit) {
        try {
            block()
        } catch (error: IOException) {
            // Progress is recoverable UI state; failed writes should not crash the lesson flow.
        } catch (error: CorruptionException) {
            // A later read will emit empty progress through the recovery path above.
        }
    }
}

@file:JvmName("LearningProgressRepositoryKt")

package com.creative.androidfundamentalsbydantech.data.progress

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class LearningProgressRepository @Inject constructor(
    private val store: LearningProgressDataStore,
) {
    val progress: Flow<LearningProgressState> = store.progress

    suspend fun setLastLesson(lessonId: String) {
        store.setLastLesson(lessonId)
    }

    suspend fun markCompleted(lessonId: String) {
        store.markCompleted(lessonId)
    }
}

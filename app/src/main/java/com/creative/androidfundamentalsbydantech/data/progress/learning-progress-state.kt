@file:JvmName("LearningProgressStateKt")

package com.creative.androidfundamentalsbydantech.data.progress

data class LearningProgressState(
    val completedLessonIds: Set<String> = emptySet(),
    val lastLessonId: String? = null,
)

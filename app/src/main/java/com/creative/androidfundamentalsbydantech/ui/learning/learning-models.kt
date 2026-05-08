@file:JvmName("LearningModelsKt")

package com.creative.androidfundamentalsbydantech.ui.learning

data class LessonDefinition(
    val id: String,
    val title: String,
    val moduleTitle: String,
    val summary: String,
    val concept: String,
    val observe: String,
    val challenge: String,
    val wrapUp: String,
    val labRoute: String? = null,
    val minutes: Int = 10,
)

data class LearningModule(
    val title: String,
    val lessons: List<LessonDefinition>,
)

data class LessonProgress(
    val completedIds: Set<String>,
    val lastLessonId: String?,
) {
    fun isCompleted(lessonId: String): Boolean = completedIds.contains(lessonId)
}

data class QuestHomeUiState(
    val modules: List<LearningModule> = emptyList(),
    val lessons: List<LessonDefinition> = emptyList(),
    val progress: LessonProgress = LessonProgress(emptySet(), null),
) {
    val completedCount: Int = lessons.count { progress.isCompleted(it.id) }
    val totalCount: Int = lessons.size
    val progressFraction: Float =
        if (totalCount == 0) 0f else completedCount.toFloat() / totalCount.toFloat()
    val nextLesson: LessonDefinition? =
        LearningProgressResolver.nextLesson(lessons = lessons, progress = progress)
}

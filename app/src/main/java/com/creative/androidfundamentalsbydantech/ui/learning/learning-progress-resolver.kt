@file:JvmName("LearningProgressResolverKt")

package com.creative.androidfundamentalsbydantech.ui.learning

object LearningProgressResolver {
    fun sanitizedProgress(
        lessons: List<LessonDefinition>,
        progress: LessonProgress,
    ): LessonProgress {
        val validIds = lessons.map { it.id }.toSet()
        return progress.copy(
            completedIds = progress.completedIds.intersect(validIds),
            lastLessonId = progress.lastLessonId?.takeIf(validIds::contains),
        )
    }

    fun nextLesson(
        lessons: List<LessonDefinition>,
        progress: LessonProgress,
    ): LessonDefinition? {
        val clean = sanitizedProgress(lessons, progress)
        return lessons.firstOrNull { !clean.isCompleted(it.id) }
    }
}

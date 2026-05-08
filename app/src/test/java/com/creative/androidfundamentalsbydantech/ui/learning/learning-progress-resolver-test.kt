@file:JvmName("LearningProgressResolverTestKt")

package com.creative.androidfundamentalsbydantech.ui.learning

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class LearningProgressResolverTest {
    @Test
    fun sanitizedProgressDropsUnknownLessonIds() {
        val progress = LessonProgress(
            completedIds = setOf(LearningContent.COMPOSE_STATE, "ghost-lesson"),
            lastLessonId = "ghost-lesson",
        )

        val clean = LearningProgressResolver.sanitizedProgress(
            lessons = LearningContent.lessons,
            progress = progress,
        )

        assertEquals(setOf(LearningContent.COMPOSE_STATE), clean.completedIds)
        assertNull(clean.lastLessonId)
    }

    @Test
    fun nextLessonReturnsFirstIncompleteLesson() {
        val progress = LessonProgress(
            completedIds = setOf(LearningContent.COMPOSE_STATE),
            lastLessonId = LearningContent.COMPOSE_STATE,
        )

        val next = LearningProgressResolver.nextLesson(
            lessons = LearningContent.lessons,
            progress = progress,
        )

        assertEquals(LearningContent.VIEWMODEL_UDF, next?.id)
    }

    @Test
    fun nextLessonReturnsNullWhenAllLessonsCompleted() {
        val progress = LessonProgress(
            completedIds = LearningContent.lessons.map { it.id }.toSet(),
            lastLessonId = LearningContent.ROOM_PERSISTENCE,
        )

        val next = LearningProgressResolver.nextLesson(
            lessons = LearningContent.lessons,
            progress = progress,
        )

        assertNull(next)
    }
}

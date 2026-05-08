@file:JvmName("LearningContentTestKt")

package com.creative.androidfundamentalsbydantech.ui.learning

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LearningContentTest {
    @Test
    fun lessonIdsAreUnique() {
        val ids = LearningContent.lessons.map { it.id }

        assertEquals(ids.size, ids.toSet().size)
    }

    @Test
    fun lessonIdsAreRouteSafeSlugs() {
        val slug = Regex("[a-z0-9]+(-[a-z0-9]+)*")

        LearningContent.lessons.forEach { lesson ->
            assertTrue("${lesson.id} is not route-safe", lesson.id.matches(slug))
        }
    }

    @Test
    fun mainPathDoesNotUseComingSoonRoutes() {
        val comingSoonLabRoutes = LearningContent.lessons
            .mapNotNull { it.labRoute }
            .filter { it.startsWith("coming-soon/") }

        assertTrue(comingSoonLabRoutes.isEmpty())
    }

    @Test
    fun mvpLessonsAreFirstInPath() {
        val firstThree = LearningContent.lessons.take(3).map { it.id }

        assertEquals(
            listOf(
                LearningContent.COMPOSE_STATE,
                LearningContent.VIEWMODEL_UDF,
                LearningContent.ROOM_PERSISTENCE,
            ),
            firstThree,
        )
        assertFalse(LearningContent.modules.isEmpty())
    }
}

@file:JvmName("LessonRouteKt")

package com.creative.androidfundamentalsbydantech.ui.learning

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creative.androidfundamentalsbydantech.ui.compose.StateShowcaseScreen
import com.creative.androidfundamentalsbydantech.ui.arch.mvvm.MvvmShowcaseScreen
import com.creative.androidfundamentalsbydantech.ui.data.room.RoomShowcaseScreen

@Composable
fun LessonRoute(
    lessonId: String,
    onBack: () -> Unit,
    onOpenRoute: (String) -> Unit,
    vm: LessonProgressViewModel = hiltViewModel(),
) {
    val lesson = LearningContent.lessonById(lessonId)
    val progress by vm.progress.collectAsStateWithLifecycle()

    if (lesson == null) {
        MissingLessonScreen(lessonId = lessonId, onBack = onBack)
        return
    }

    LaunchedEffect(lesson.id) {
        vm.setLastLesson(lesson.id)
    }

    val completed = progress.isCompleted(lesson.id)
    val markComplete = { vm.markComplete(lesson.id) }
    val openLab = lesson.labRoute?.let { route -> { onOpenRoute(route) } }

    when (lesson.id) {
        LearningContent.COMPOSE_STATE -> StateShowcaseScreen(
            onBack = onBack,
            completed = completed,
            onComplete = markComplete,
            onOpenLab = openLab,
        )
        LearningContent.VIEWMODEL_UDF -> MvvmShowcaseScreen(
            onBack = onBack,
            completed = completed,
            onComplete = markComplete,
            onOpenLab = openLab,
        )
        LearningContent.ROOM_PERSISTENCE -> RoomShowcaseScreen(
            onBack = onBack,
            completed = completed,
            onComplete = markComplete,
            onOpenLab = openLab,
        )
        else -> GenericLessonScreen(
            lesson = lesson,
            completed = completed,
            onBack = onBack,
            onComplete = markComplete,
            onOpenLab = openLab,
        )
    }
}

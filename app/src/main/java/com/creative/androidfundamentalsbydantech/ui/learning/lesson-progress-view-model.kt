@file:JvmName("LessonProgressViewModelKt")

package com.creative.androidfundamentalsbydantech.ui.learning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.androidfundamentalsbydantech.data.progress.LearningProgressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class LessonProgressViewModel @Inject constructor(
    private val progressRepository: LearningProgressRepository,
) : ViewModel() {
    val progress: StateFlow<LessonProgress> = progressRepository.progress
        .map { state ->
            LearningProgressResolver.sanitizedProgress(
                lessons = LearningContent.lessons,
                progress = LessonProgress(
                    completedIds = state.completedLessonIds,
                    lastLessonId = state.lastLessonId,
                ),
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LessonProgress(emptySet(), null),
        )

    fun setLastLesson(lessonId: String) {
        if (LearningContent.lessonById(lessonId) == null) return
        viewModelScope.launch { progressRepository.setLastLesson(lessonId) }
    }

    fun markComplete(lessonId: String) {
        if (LearningContent.lessonById(lessonId) == null) return
        viewModelScope.launch { progressRepository.markCompleted(lessonId) }
    }
}

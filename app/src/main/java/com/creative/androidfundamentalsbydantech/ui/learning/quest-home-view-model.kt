@file:JvmName("QuestHomeViewModelKt")

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

@HiltViewModel
class QuestHomeViewModel @Inject constructor(
    progressRepository: LearningProgressRepository,
) : ViewModel() {
    val uiState: StateFlow<QuestHomeUiState> = progressRepository.progress
        .map { state ->
            val progress = LessonProgress(
                completedIds = state.completedLessonIds,
                lastLessonId = state.lastLessonId,
            )
            QuestHomeUiState(
                modules = LearningContent.modules,
                lessons = LearningContent.lessons,
                progress = LearningProgressResolver.sanitizedProgress(
                    lessons = LearningContent.lessons,
                    progress = progress,
                ),
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = QuestHomeUiState(
                modules = LearningContent.modules,
                lessons = LearningContent.lessons,
            ),
        )
}

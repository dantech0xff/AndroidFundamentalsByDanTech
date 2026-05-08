package com.creative.androidfundamentalsbydantech.ui.data.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.androidfundamentalsbydantech.data.local.NoteDao
import com.creative.androidfundamentalsbydantech.data.local.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val dao: NoteDao,
) : ViewModel() {

    val notes: StateFlow<List<NoteEntity>> = dao.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun add(title: String, body: String) {
        viewModelScope.launch {
            dao.insert(NoteEntity(title = title, body = body))
        }
    }

    fun togglePin(note: NoteEntity) {
        viewModelScope.launch {
            dao.update(note.copy(pinned = !note.pinned))
        }
    }

    fun delete(note: NoteEntity) {
        viewModelScope.launch { dao.delete(note) }
    }

    fun clear() {
        viewModelScope.launch { dao.clear() }
    }
}

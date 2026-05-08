package com.creative.androidfundamentalsbydantech.ui.data.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.androidfundamentalsbydantech.data.prefs.AppSettings
import com.creative.androidfundamentalsbydantech.data.prefs.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val store: SettingsDataStore,
) : ViewModel() {

    val state: StateFlow<AppSettings> = store.settings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AppSettings())

    fun setDarkMode(on: Boolean) {
        viewModelScope.launch { store.setDarkMode(on) }
    }

    fun setUsername(value: String) {
        viewModelScope.launch { store.setUsername(value) }
    }
}

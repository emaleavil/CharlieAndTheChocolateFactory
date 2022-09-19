package com.eeema.android.charlieapp.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eeema.android.charlieapp.ui.constants.Constants
import com.eeema.android.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableStateFlow<DetailState> = MutableStateFlow(DetailState.Loading)
    val state: StateFlow<DetailState>
        get() = _state

    private val id: Int? = savedStateHandle.get<Int>(Constants.characterIdKey)

    init {
        retrieveCharacter(id)
    }

    private fun retrieveCharacter(id: Int?) {
        if (id != null) {
            obtainCharacter(id)
        } else {
            _state.value = DetailState.Failed
        }
    }

    private fun obtainCharacter(id: Int) {
        viewModelScope.launch {
            val character = repository.getCharacter(id)
            _state.value = DetailState.Data(character)
        }
    }
}

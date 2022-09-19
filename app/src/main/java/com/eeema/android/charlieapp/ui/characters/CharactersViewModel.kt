package com.eeema.android.charlieapp.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eeema.android.data.Repository
import com.eeema.android.data.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    companion object {
        const val EMPTY_FILTER = ""
    }

    private val _state = MutableStateFlow<PagingData<Character>>(PagingData.empty())
    val state: StateFlow<PagingData<Character>>
        get() = _state

    private var currentFilter: String = EMPTY_FILTER

    init {
        filter(currentFilter)
    }

    fun filter(input: String) {
        currentFilter = input
        viewModelScope.launch {
            repository.getCharacters(currentFilter)
                .cachedIn(viewModelScope)
                .collectLatest { _state.value = it }
        }
    }

    fun retry() {
        filter(currentFilter)
    }
}

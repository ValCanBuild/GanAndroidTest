package com.valentinhinov.ganandroidtest.feature.browser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valentinhinov.ganandroidtest.data.api.BreakingBadApi
import com.valentinhinov.ganandroidtest.data.models.SeriesCharacter
import com.valentinhinov.ganandroidtest.di.CoroutineContextProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class Command {
    object ShowLoadError : Command()
    data class ShowCharacterDetail(val character: SeriesCharacter) : Command()
}

data class State(
    val characterList: List<SeriesCharacter> = emptyList(),
    val showRetryButton: Boolean = false,
    val isLoading: Boolean = false
)

class BrowserViewModel(
    private val api: BreakingBadApi,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {

    val state: MutableLiveData<State> by lazy {
        MutableLiveData(State(isLoading = true))
    }

    val commands: MutableLiveData<Command> by lazy {
        MutableLiveData()
    }

    private val currentState: State
        get() = requireNotNull(state.value)

    private val handler = CoroutineExceptionHandler { _, _ ->
        commands.value = Command.ShowLoadError
        state.value = currentState.copy(
            isLoading = false,
            showRetryButton = true
        )
    }

    private lateinit var allCharacters: List<SeriesCharacter>
    private val activeSeasons = (1..5).toMutableSet()
    private var currentSearchTerm = ""

    fun loadAllCharacters() {
        state.value = currentState.copy(
            isLoading = true,
            showRetryButton = false
        )

        viewModelScope.launch(handler) {
            allCharacters = withContext(coroutineContextProvider.io) {
                api.getAllCharacters()
            }
            state.value = currentState.copy(
                characterList = allCharacters,
                isLoading = false
            )
        }
    }

    fun searchCharacters(searchTerm: String) {
        currentSearchTerm = searchTerm.trim()
        filterList()
    }

    fun seasonFilterUpdated(index: Int, isChecked: Boolean) {
        val season = index + 1
        if (isChecked) {
            activeSeasons.add(season)
        } else {
            activeSeasons.remove(season)
        }

        filterList()
    }

    fun onCharacterClicked(character: SeriesCharacter) {
        commands.value = Command.ShowCharacterDetail(character)
    }

    private fun filterList() {
        // really basic search
        val listToShow = allCharacters.filter {
            it.name.contains(currentSearchTerm, ignoreCase = true) &&
                    it.seasonAppearances.intersect(activeSeasons).isNotEmpty()
        }

        state.value = currentState.copy(
            characterList = listToShow
        )
    }
}
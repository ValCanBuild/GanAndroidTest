package com.valentinhinov.ganandroidtest.feature.browser

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.valentinhinov.ganandroidtest.data.api.BreakingBadApi
import com.valentinhinov.ganandroidtest.data.models.SeriesCharacter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class Command {
    object ShowLoadError : Command()
}

data class State(
    val characterList: List<SeriesCharacter> = emptyList(),
    val showRetryButton: Boolean = false,
    val isLoading: Boolean = false
)

class BrowserViewModel(
    private val api: BreakingBadApi
) : ViewModel() {

    val state: MutableLiveData<State> by lazy {
        MutableLiveData(State(isLoading = true))
    }

    val commands: MutableLiveData<Command> by lazy {
        MutableLiveData()
    }

    private val currentState: State
        get() = requireNotNull(state.value)

    private lateinit var allCharacters: List<SeriesCharacter>
    private val activeSeasons = (1..5).toMutableSet()
    private var currentSearchTerm = ""

    fun loadAllCharacters() {
        state.value = currentState.copy(
            isLoading = true,
            showRetryButton = false
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                allCharacters = api.getAllCharacters()
                withContext(Dispatchers.Main) {
                    state.value = currentState.copy(
                        characterList = allCharacters,
                        isLoading = false
                    )
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    commands.value = Command.ShowLoadError
                    state.value = currentState.copy(
                        isLoading = false,
                        showRetryButton = true
                    )
                }
                Log.e(BrowserViewModel::class.java.name, "Error while fetching character data", e)
            }
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
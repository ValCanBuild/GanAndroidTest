package com.valentinhinov.ganandroidtest.feature.browser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.valentinhinov.ganandroidtest.data.api.BreakingBadApi
import com.valentinhinov.ganandroidtest.data.models.SeriesCharacter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BrowserViewModel(
    private val api: BreakingBadApi
) : ViewModel() {

    val characterList: MutableLiveData<List<SeriesCharacter>> by lazy {
        MutableLiveData()
    }

    fun loadAllCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            // TODO: would be nice to have some sort of error handling
            val characters = api.getAllCharacters()
            withContext(Dispatchers.Main) {
                characterList.value = characters
            }
        }
    }
}
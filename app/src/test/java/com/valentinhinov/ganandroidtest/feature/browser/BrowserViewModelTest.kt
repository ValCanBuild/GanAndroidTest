package com.valentinhinov.ganandroidtest.feature.browser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.valentinhinov.ganandroidtest.data.api.BreakingBadApi
import com.valentinhinov.ganandroidtest.data.models.SeriesCharacter
import com.valentinhinov.ganandroidtest.testutils.TestCoroutineContextProvider
import com.valentinhinov.ganandroidtest.testutils.TestCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private val WALTER = SeriesCharacter(
    0,
    "Walter White",
    listOf("Teacher", "Kingpin"),
    "",
    "Presumed dead",
    listOf(1, 2, 3, 4, 5),
    "Heisenberg"
)
private val SKYLER = SeriesCharacter(
    1,
    "Skyler White",
    listOf("Housewife", "Accountant"),
    "",
    "Alive",
    listOf(1, 2, 3, 4, 5),
    "Skyler"
)
private val TUCO = SeriesCharacter(
    2,
    "Tuco Salamanca",
    listOf("Meth Distributor"),
    "",
    "Deceased",
    listOf(1, 2),
    "Tuco"
)
private val testCharacterList = listOf(
    WALTER,
    SKYLER,
    TUCO
)

@ExperimentalCoroutinesApi
class BrowserViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockApi: BreakingBadApi = mockk {
        coEvery { getAllCharacters() } returns testCharacterList
    }

    private val viewModel = BrowserViewModel(mockApi, TestCoroutineContextProvider())

    private val mockCommandObserver: Observer<Command> = mockk(relaxUnitFun = true)
    private val mockStateObserver: Observer<State> = mockk(relaxUnitFun = true)

    @Before
    fun setUp() {
        viewModel.commands.observeForever(mockCommandObserver)
        viewModel.state.observeForever(mockStateObserver)
    }

    @Test
    fun `when loadAllCharacters success then starts with loading state then loads characters and shows`() =
        testCoroutineRule.runBlocking {
            viewModel.loadAllCharacters()

            coVerifyOrder {
                mockStateObserver.onChanged(State(isLoading = true, showRetryButton = false))

                mockApi.getAllCharacters()

                mockStateObserver.onChanged(
                    State(isLoading = false, characterList = testCharacterList)
                )
            }
        }

    @Test
    fun `when loadAllCharacters error then starts with loading state then shows retry button`() {
        coEvery { mockApi.getAllCharacters() } throws Throwable()

        testCoroutineRule.runBlocking {
            viewModel.loadAllCharacters()

            coVerifyOrder {
                mockStateObserver.onChanged(State(isLoading = true, showRetryButton = false))

                mockApi.getAllCharacters()

                mockStateObserver.onChanged(
                    State(isLoading = false, showRetryButton = true)
                )
            }
        }
    }

    @Test
    fun `when searchCharacters then filters list by name`() =
        testCoroutineRule.runBlocking {
            viewModel.loadAllCharacters()

            viewModel.searchCharacters("Sky")

            verify {
                mockStateObserver.onChanged(
                    State(characterList = listOf(SKYLER))
                )
            }
        }

    @Test
    fun `when seasonFilterUpdated with season not checked then filters list by season appearance`() =
        testCoroutineRule.runBlocking {
            viewModel.loadAllCharacters()

            viewModel.seasonFilterUpdated(0, false)
            viewModel.seasonFilterUpdated(1, false)

            verify {
                mockStateObserver.onChanged(
                    State(characterList = testCharacterList.minus(TUCO))
                )
            }
        }

    @Test
    fun `when onCharacterClicked then shows character detail`() {
        val mockCharacter: SeriesCharacter = mockk()

        viewModel.onCharacterClicked(mockCharacter)

        verifySequence {
            mockCommandObserver.onChanged(Command.ShowCharacterDetail(mockCharacter))
        }
    }
}
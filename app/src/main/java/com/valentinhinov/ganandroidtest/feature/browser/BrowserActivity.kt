package com.valentinhinov.ganandroidtest.feature.browser

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.valentinhinov.ganandroidtest.R
import com.valentinhinov.ganandroidtest.data.models.SeriesCharacter
import com.valentinhinov.ganandroidtest.feature.detail.DetailBottomSheetFragment
import kotlinx.android.synthetic.main.activity_browser.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.dsl.module

class BrowserActivity : AppCompatActivity(R.layout.activity_browser) {

    private val adapter: BrowserAdapter by lazy {
        BrowserAdapter(this, ::onCharacterClicked)
    }

    private val viewModel: BrowserViewModel by viewModel()

    private val filterSeasons = (0..4).map { true }.toBooleanArray() // 5 seasons

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            GridSpacingDecoration(
                spacingPx = resources.getDimensionPixelSize(R.dimen.character_cell_spacing),
                spanCount = (recyclerView.layoutManager as GridLayoutManager).spanCount
            )
        )

        tryAgainButton.setOnClickListener {
            viewModel.loadAllCharacters()
        }

        searchField.doAfterTextChanged {
            val searchTerm = it?.toString() ?: ""
            viewModel.searchCharacters(searchTerm)
        }

        viewModel.state.observe(this) { state ->
            adapter.submitList(state.characterList)
            tryAgainButton.isVisible = state.showRetryButton
            loadingIndicator.isVisible = state.isLoading
            searchCardView.isVisible = !state.isLoading
        }

        viewModel.commands.observe(this) { command ->
            when (command) {
                Command.ShowLoadError -> {
                    Toast.makeText(this, R.string.browse_error_loading, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.loadAllCharacters()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_browse, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.browse_filter -> {
                showFilterDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showFilterDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.browse_filter_dialog_title)
            .setMultiChoiceItems(R.array.season_names, filterSeasons) { _, which, isChecked ->
                viewModel.seasonFilterUpdated(index = which, isChecked = isChecked)
            }
            .show()
    }

    private fun onCharacterClicked(character: SeriesCharacter) {
        val fragment = DetailBottomSheetFragment.newInstance(character)
        fragment.show(supportFragmentManager, DetailBottomSheetFragment::class.java.name)
    }

    companion object {
        val activityModule = module {
            viewModel { BrowserViewModel(api = get()) }
        }
    }
}
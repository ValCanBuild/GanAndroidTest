package com.valentinhinov.ganandroidtest.feature.browser

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.valentinhinov.ganandroidtest.R
import com.valentinhinov.ganandroidtest.data.models.SeriesCharacter
import com.valentinhinov.ganandroidtest.feature.detail.DetailBottomSheetFragment
import kotlinx.android.synthetic.main.activity_browser.*

class BrowserActivity : AppCompatActivity(R.layout.activity_browser) {

    private val adapter: BrowserAdapter by lazy {
        BrowserAdapter(this, ::onCharacterClicked)
    }

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

        adapter.submitList(
            listOf(
                SeriesCharacter(
                    1,
                    "Walter White",
                    occupations = listOf("High School Chemistry Teacher", "Meth dealer"),
                    imgUrl = "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
                    status = "Presumed dead",
                    seasonAppearances = listOf(1,2,3,4,5),
                    nickname = "Heisenberg",
                    portrayed = "Brian Cranston"
                ),
                SeriesCharacter(
                    2,
                    "Jesse Pinkman",
                    occupations = listOf("Meth dealer"),
                    imgUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/f/f2/Jesse_Pinkman2.jpg/220px-Jesse_Pinkman2.jpg",
                    status = "Alive",
                    seasonAppearances = listOf(1,2,3,5),
                    nickname = "Cap n' Cook",
                    portrayed = "AAron Paul"
                ),
                SeriesCharacter(
                    3,
                    "Skyler White",
                    occupations = listOf("House Wife", "Book Keeper"),
                    imgUrl = "https://s-i.huffpost.com/gen/1317262/images/o-ANNA-GUNN-facebook.jpg",
                    status = "Alive",
                    seasonAppearances = listOf(1,2,3,5),
                    nickname = "Sky",
                    portrayed = ""
                ),
                SeriesCharacter(
                    4,
                    "Walter White Jr.",
                    occupations = emptyList(),
                    imgUrl = "https://media1.popsugar-assets.com/files/thumbor/WeLUSvbAMS_GL4iELYAUzu7Bpv0/fit-in/1024x1024/filters:format_auto-!!-:strip_icc-!!-/2018/01/12/910/n/1922283/fb758e62b5daf3c9_TCDBRBA_EC011/i/RJ-Mitte-Walter-White-Jr.jpg",
                    status = "",
                    seasonAppearances = emptyList(),
                    nickname = "",
                    portrayed = ""
                )
            )
        )
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
            .setMultiChoiceItems(R.array.season_names, filterSeasons) { dialog, which, isChecked ->

            }
            .show()
    }

    private fun onCharacterClicked(character: SeriesCharacter) {
        val fragment = DetailBottomSheetFragment.newInstance(character)
        fragment.show(supportFragmentManager, DetailBottomSheetFragment::class.java.name)
    }
}
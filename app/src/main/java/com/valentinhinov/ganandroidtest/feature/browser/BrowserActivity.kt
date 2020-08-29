package com.valentinhinov.ganandroidtest.feature.browser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.valentinhinov.ganandroidtest.R
import com.valentinhinov.ganandroidtest.data.models.SeriesCharacter
import kotlinx.android.synthetic.main.activity_browser.*

class BrowserActivity : AppCompatActivity(R.layout.activity_browser) {

    private val adapter: BrowserAdapter by lazy {
        BrowserAdapter(this, ::onCharacterClicked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                    occupations = emptyList(),
                    imgUrl = "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
                    status = "",
                    seasonAppearances = emptyList(),
                    nickname = "Heisenberg",
                    portrayed = "Brian Cranston"
                ),
                SeriesCharacter(
                    2,
                    "Jesse Pinkman",
                    occupations = emptyList(),
                    imgUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/f/f2/Jesse_Pinkman2.jpg/220px-Jesse_Pinkman2.jpg",
                    status = "",
                    seasonAppearances = emptyList(),
                    nickname = "Cap n' Cook",
                    portrayed = "AAron Paul"
                ),
                SeriesCharacter(
                    3,
                    "Skyler White",
                    occupations = emptyList(),
                    imgUrl = "https://s-i.huffpost.com/gen/1317262/images/o-ANNA-GUNN-facebook.jpg",
                    status = "",
                    seasonAppearances = emptyList(),
                    nickname = "",
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

    private fun onCharacterClicked(character: SeriesCharacter) {

    }
}
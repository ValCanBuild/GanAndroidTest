package com.valentinhinov.ganandroidtest.feature.detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import coil.load
import coil.request.ImageRequest
import coil.transform.BlurTransformation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.valentinhinov.ganandroidtest.R
import com.valentinhinov.ganandroidtest.data.models.SeriesCharacter
import kotlinx.android.synthetic.main.fragment_detail_bottom_sheet.*

class DetailBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_detail_bottom_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val character = requireNotNull(arguments?.getParcelable<SeriesCharacter>(CHARACTER_EXTRA))

        characterBlurredImageView.load(character.imgUrl) {
            transformations(BlurTransformation(requireContext(), 10f, 2f))
            listener(onError = { _: ImageRequest, _: Throwable ->
                characterBlurredImageView.setBackgroundColor(requireContext().getColor(R.color.colorPrimary))
            })
        }

        characterImageView.load(character.imgUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_baseline_person_24)
            error(R.drawable.ic_baseline_person_24)
        }

        characterName.text =
            getString(R.string.detail_character_name, character.name, character.nickname)
        characterStatus.text = character.status
        characterOccupations.text = character.occupations.joinToString("\n- ", prefix = "- ")
        seasonAppearance.text = getString(
            R.string.detail_season_appearance,
            character.seasonAppearances.joinToString(", ")
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        // It's really annoying that this is the only way to fully expand the bottom sheet when it is first shown
        bottomSheetDialog.setOnShowListener {
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)?.let {
                BottomSheetBehavior.from(it).setState(BottomSheetBehavior.STATE_EXPANDED)
            }
        }

        return bottomSheetDialog
    }

    companion object {
        private const val CHARACTER_EXTRA = "CHARACTER_EXTRA"

        fun newInstance(character: SeriesCharacter): DetailBottomSheetFragment =
            DetailBottomSheetFragment().apply {
                arguments = bundleOf(CHARACTER_EXTRA to character)
            }
    }
}
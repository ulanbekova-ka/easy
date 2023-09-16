package kg.hackathon.template.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.hackathon.template.base.BaseAdapter
import kg.hackathon.template.R
import kg.hackathon.template.databinding.BottomSheetSearchBinding

class FullScreenWithSearchBottomSheet<T,adapter: BaseAdapter<T>> (
    private val adapterCreator: ((dialog: BottomSheetDialog?) -> adapter)? = null,
    listSource: (() -> List<T>)? = null,
    private val filterPerformer: ((query: String?, list: List<T>?) -> List<T>?)?= null
): BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetSearchBinding
    private lateinit var behavior: BottomSheetBehavior<View>

    private val list = listSource?.invoke()
    private lateinit var dialog: BottomSheetDialog
    private var adapter: BaseAdapter<T>?= null


    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(requireContext(), R.layout.bottom_sheet_search,null)
        setupDialogView(view)
        behavior = BottomSheetBehavior.from(view.parent as View).apply {
            peekHeight = getScreenHeight()
        }

        setFullHeightToDialog()
        return dialog
    }

    private fun setupDialogView( view: View) {
        dialog.apply {
            setContentView(view)
            dismissWithAnimation = true
        }
        adapter = adapterCreator?.invoke(dialog)
        binding = BottomSheetSearchBinding.bind(view)
        with(binding) {
            ivClose.setOnClickListener {
                dialog.dismiss()
            }
            recyclerView.adapter = adapter
            etSearch.addTextChangedListener {
                filterPerformer?.invoke(it?.toString(), list)?.let { adapter?.submitList(it) }
            }
        }
    }

    private fun setFullHeightToDialog() {
        val layoutParams = binding.root.layoutParams.apply {
            height = getScreenHeight()
        } as LinearLayout.LayoutParams
        binding.root.layoutParams = layoutParams
    }

    override fun onStart() {
        super.onStart()
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        adapter?.submitList(list ?: listOf())
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}
package kg.hackathon.template.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding, VM : BaseVM<*>>(
    open val bindFactory: (LayoutInflater) -> VB
) : Fragment() {

    abstract val vm: VM
    private var _vb: VB? = null
    val vb: VB
        get() = _vb!!

    protected open var onNavigationClickListener: View.OnClickListener? = View.OnClickListener {
        activity?.onBackPressed()
    }

    private lateinit var loader: AlertDialog
    open var layoutId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _vb = bindFactory(layoutInflater)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTransparentLoadingDialog()
        initViews()
    }

    open fun initViews() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
        loader.dismiss()
    }

    private fun setupTransparentLoadingDialog() {
        loader = AlertDialog.Builder(requireContext(), android.R.style.Theme_Material_Light_Panel)
            .setView(layoutId?.let { LayoutInflater.from(requireContext()).inflate(it, null) })
            .setCancelable(false)
            .create()
        loader.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                loader.dismiss()
            }
            return@setOnKeyListener true
        }
        loader.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    open fun showLoading() {
        loader.show()
    }

    open fun hideLoading() {
        loader.dismiss()
    }

}


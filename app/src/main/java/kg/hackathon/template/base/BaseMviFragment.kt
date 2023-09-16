package kg.hackathon.template.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import kg.hackathon.template.utils.toast
import org.orbitmvi.orbit.viewmodel.observe

abstract class BaseMviFragment<State : Any, VB : ViewBinding, VM : BaseVM<State>>(
    override val bindFactory: (LayoutInflater) -> VB
) : BaseFragment<VB, VM>(bindFactory) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.observe(viewLifecycleOwner, ::onRender, ::onSideEffect)
    }

    open suspend fun onRender(state: State) {}

    open suspend fun onSideEffect(sideEffect: SideEffect) {
        when(sideEffect) {
            is TextToast -> context?.toast(sideEffect.content)
        }
    }
}
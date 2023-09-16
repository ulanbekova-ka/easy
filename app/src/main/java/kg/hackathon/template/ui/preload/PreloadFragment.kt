package kg.hackathon.template.ui.preload

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kg.hackathon.template.base.BaseMviFragment
import kg.hackathon.template.base.SideEffect
import kg.hackathon.template.databinding.FragmentPreloadBinding
import kg.hackathon.template.ui.preload.data.PreloadSideEffect
import kg.hackathon.template.utils.navigateTo

@AndroidEntryPoint
class PreloadFragment :
    BaseMviFragment<Any, FragmentPreloadBinding, PreloadVM>(FragmentPreloadBinding::inflate) {

    override val vm by viewModels<PreloadVM>()

    override suspend fun onSideEffect(effect: SideEffect) {
        when (effect) {
            is PreloadSideEffect.OpenMainScreen -> PreloadFragmentDirections.toMain()
            else -> PreloadFragmentDirections.toLogin()
        }.let { direction -> navigateTo(direction) }
    }
}
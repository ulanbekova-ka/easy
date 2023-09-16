package kg.hackathon.template.ui.main

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kg.hackathon.template.activity.AppActivity
import kg.hackathon.template.base.BaseMviFragment
import kg.hackathon.template.base.SideEffect
import kg.hackathon.template.databinding.FragmentMainBinding
import kg.hackathon.template.ui.main.data.MainSideEffect
import kg.hackathon.template.ui.main.data.MainState
import kg.hackathon.template.utils.convertBase64ToBitmap
import kg.hackathon.template.utils.navigateTo

@AndroidEntryPoint
class MainFragment :
    BaseMviFragment<MainState, FragmentMainBinding, MainVM>(FragmentMainBinding::inflate) {

    override val vm: MainVM by viewModels()

    override suspend fun onRender(state: MainState) {
        state.user?.let {
            with(vb) {
                it.profileImage?.let { avatar.setImageBitmap(it.convertBase64ToBitmap()) }
                name.text = it.fullName
            }
        }
    }

    override suspend fun onSideEffect(sideEffect: SideEffect) {
        super.onSideEffect(sideEffect)
        when (sideEffect) {
            is MainSideEffect.NavigateToLoginScreen -> openLoginScreen()
        }
    }

    override fun initViews() = with(vb) {
        super.initViews()
        (activity as AppActivity).bottomMenuVisibility(true)
    }

    private fun openLoginScreen() {
        navigateTo(MainFragmentDirections.toPreloadFragment())
    }
}
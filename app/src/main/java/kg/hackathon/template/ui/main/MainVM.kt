package kg.hackathon.template.ui.main

import dagger.hilt.android.lifecycle.HiltViewModel
import kg.hackathon.template.base.Action
import kg.hackathon.template.base.BaseVM
import kg.hackathon.template.base.SideEffect
import kg.hackathon.template.data.repository.MainRepository
import kg.hackathon.template.ui.main.data.MainState
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val mainRepo: MainRepository
) : BaseVM<MainState>(MainState()) {

    override val container = container<MainState, SideEffect>(MainState()) {
        getUserFromDB()
    }

    override fun doAction(action: Action) {}

    private fun getUserFromDB() = intent {
        launchWithErrorHandling({
            val user = mainRepo.fetchUserFromDB()
            reduce { state.copy(user = user) }
        })
    }

    override fun hideLoading() = intent {
        reduce { state.copy(loading = false) }
    }

    private fun showLoading() = intent {
        reduce { state.copy(loading = true) }
    }
}
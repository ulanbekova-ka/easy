package kg.hackathon.template.ui.search

import dagger.hilt.android.lifecycle.HiltViewModel
import kg.hackathon.template.base.Action
import kg.hackathon.template.base.BaseVM
import kg.hackathon.template.data.repository.MainRepository
import kg.hackathon.template.ui.search.data.SearchState
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(
    private val mainRepo: MainRepository
) : BaseVM<SearchState>(SearchState()) {

    override fun doAction(action: Action) {}

    override fun hideLoading() = intent {
        reduce { state.copy(loading = false) }
    }

    private fun showLoading() = intent {
        reduce { state.copy(loading = true) }
    }
}
package kg.hackathon.template.ui.search

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kg.hackathon.template.base.BaseMviFragment
import kg.hackathon.template.base.SideEffect
import kg.hackathon.template.data.network.model.Product
import kg.hackathon.template.databinding.FragmentSearchBinding
import kg.hackathon.template.ui.search.data.SearchState
import kg.hackathon.template.ui.search.rv.SearchResultsAdapter

@AndroidEntryPoint
class SearchFragment : BaseMviFragment<SearchState, FragmentSearchBinding, SearchVM>(
        FragmentSearchBinding::inflate
), SearchResultsAdapter.Listener {

    override val vm: SearchVM by viewModels()

    private val adapter: SearchResultsAdapter by lazy {
        SearchResultsAdapter(this)
    }

    override fun initViews() = with(vb) {
        super.initViews()
        rvSearchRes.adapter = adapter
    }

    override suspend fun onRender(state: SearchState) {
        super.onRender(state)
        renderUI(state)
    }

    override suspend fun onSideEffect(sideEffect: SideEffect) {
        super.onSideEffect(sideEffect)
    }

    private fun renderUI(state: SearchState) {
        state.run {
            products?.let { adapter.submitList(it) }
            vb.loader.isVisible = loading
        }
    }

    override fun onClick(item: Product) {
    }

}
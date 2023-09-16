package kg.hackathon.template.ui.search.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.hackathon.template.utils.inflate
import kg.hackathon.template.R
import kg.hackathon.template.data.network.model.Product
import kg.hackathon.template.databinding.ItemEmptyBinding

class SearchResultsVH(private val vb: ItemEmptyBinding) : RecyclerView.ViewHolder(vb.root) {

    private lateinit var product: Product

    fun onBind(item: Product, isFirst: Boolean, isLast: Boolean) = with(vb) {
        product = item
//        cellView.run {
//            setTitle(item.name)
//            setupRoundedModeByPosition(isFirst, isLast)
//            decideAgentState(item)
//        }
    }

    private fun decideAgentState(product: Product) {
//        vb.cellView.setSubtitle("")
//        when {
//            (agent.bindedStandId ?: 0) > 0 -> setSubtitle(R.string.stand)
//            (agent.bindedRouteId ?: 0) > 0 -> setSubtitle(R.string.route)
//            (agent.bindedEventId ?: 0) > 0 -> setSubtitle(R.string.event)
//            (agent.bindedAdvBklDistrId ?: 0) > 0 -> setSubtitle(R.string.ad_route)
//        }
    }
    companion object {
        fun create(parent: ViewGroup, listener: SearchResultsAdapter.Listener): SearchResultsVH {
            val view = parent.inflate(R.layout.item_empty)
            return SearchResultsVH(ItemEmptyBinding.bind(view)).apply {
//                vb.cellView.setOnClickListener {
//                    listener.onClick(agent)
//                }
            }
        }
    }
}
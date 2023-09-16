package kg.hackathon.template.ui.search.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.hackathon.template.base.BaseAdapter
import kg.hackathon.template.data.network.model.Product

class SearchResultsAdapter(private val listener: Listener): BaseAdapter<Product>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchResultsVH.create(parent, listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchResultsVH -> holder.onBind(currentList[position], position == 0, position == currentList.lastIndex)
        }
    }

    interface Listener {
        fun onClick(item: Product)
    }
}
package kg.hackathon.template.base

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val asyncListDiffer by lazy {
        AsyncListDiffer(this, DiffCallBack())
    }

    private inner class DiffCallBack : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any) =
            this@BaseAdapter.areItemsTheSame(oldItem, newItem)

        override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any) =
            this@BaseAdapter.areContentsTheSame(oldItem, newItem)

        override fun getChangePayload(oldItem: T & Any, newItem: T & Any): Any? {
            val result = this@BaseAdapter.getChangePayload(oldItem, newItem)
            return result ?: super.getChangePayload(oldItem, newItem)
        }
    }

    protected open fun areItemsTheSame(oldItem: T & Any, newItem: T & Any) = oldItem == newItem
    protected open fun areContentsTheSame(oldItem: T & Any, newItem: T & Any) = oldItem == newItem

    //return null, to call super class function
    protected open fun getChangePayload(oldItem: T & Any, newItem: T & Any): Any? { return null }

    fun submitList(newList: List<T>) = asyncListDiffer.submitList(newList)
    override fun getItemCount(): Int = asyncListDiffer.currentList.count()

    protected val currentList: List<T>
        get() = asyncListDiffer.currentList
}
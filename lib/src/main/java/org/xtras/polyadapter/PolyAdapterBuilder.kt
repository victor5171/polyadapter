package org.xtras.polyadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class PolyAdapterBuilder<TItem : Any, TViewTypeRetriever : ViewTypeRetriever<TItem>>(
    private val viewTypeRetriever: TViewTypeRetriever
) {
    private val delegatesMap = DelegatesMap<TItem>()

    fun <TSubItem : TItem, TViewHolder : RecyclerView.ViewHolder> registerDelegate(
        viewType: Int,
        delegate: ViewHolderDelegate<TSubItem, TViewHolder>
    ) = apply {
        delegatesMap.registerDelegate(viewType, delegate)
    }

    fun buildForRecyclerView(items: List<TItem>): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        val multiAdapter = buildOnlyAdapter(items::get)
        return object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                multiAdapter.onCreateViewHolder(parent, viewType)

            override fun getItemViewType(position: Int) = multiAdapter.getItemViewType(position)

            override fun getItemCount(): Int = items.size

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
                multiAdapter.onBindViewHolder(holder, position)
        }
    }

    fun buildForListAdapter(asyncDifferConfig: AsyncDifferConfig<TItem>): ListAdapter<TItem, RecyclerView.ViewHolder> {
        return object : ListAdapter<TItem, RecyclerView.ViewHolder>(asyncDifferConfig) {

            private val multiAdapter = buildOnlyAdapter(this::getItem)

            override fun onCreateViewHolder(
                parent: ViewGroup, viewType: Int
            ) = multiAdapter.onCreateViewHolder(parent, viewType)

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
                multiAdapter.onBindViewHolder(holder, position)
        }
    }

    fun buildForListAdapter(diffCallback: DiffUtil.ItemCallback<TItem>): ListAdapter<TItem, RecyclerView.ViewHolder> {
        return object : ListAdapter<TItem, RecyclerView.ViewHolder>(diffCallback) {

            private val multiAdapter = buildOnlyAdapter(this::getItem)

            override fun onCreateViewHolder(
                parent: ViewGroup, viewType: Int
            ) = multiAdapter.onCreateViewHolder(parent, viewType)

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
                multiAdapter.onBindViewHolder(holder, position)
        }
    }

    fun buildOnlyAdapter(itemGetter: ItemGetter<TItem>) =
        PolyAdapter(viewTypeRetriever, delegatesMap, itemGetter)
}

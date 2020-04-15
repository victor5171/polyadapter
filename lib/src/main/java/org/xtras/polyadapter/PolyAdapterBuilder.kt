package org.xtras.polyadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Builder to generate instances of the [PolyAdapter] or some adapter types
 * It has an idiomatic structure to ease the use to add delegates
 * @param TItem the supertype of all items inside the adapter
 * @param TViewTypeRetriever the type of the [ViewTypeRetriever] this PolyAdapter shall use
 * @param viewTypeRetriever Used to generate ViewTypes inside the [PolyAdapter]
 * @see [ClassViewTypeRetriever] and [EnumViewTypeRetriever]
 */
class PolyAdapterBuilder<TItem : Any, in TViewTypeRetriever : ViewTypeRetriever<TItem>>(
    private val viewTypeRetriever: TViewTypeRetriever
) {
    private val delegatesMap = DelegatesMap<TItem>()

    /**
     * Registers a delegate, attaching it to [viewType]
     * If you already have a delegate attached with [viewType] you can replace it calling this function again
     * @param TSubItem A item that inherits [TItem] or also [TItem] itself
     * @param TViewHolder The ViewHolder this delegate works with
     * @param viewType The ViewType [delegate] will be attached with
     * @param delegate The delegate that [viewType] will be attached with
     * @return The same instance of [PolyAdapterBuilder] to make it easier to chain calls
     */
    fun <TSubItem : TItem, TViewHolder : RecyclerView.ViewHolder> registerDelegate(
        viewType: Int,
        delegate: ViewHolderDelegate<TSubItem, TViewHolder>
    ) = apply {
        delegatesMap.registerDelegate(viewType, delegate)
    }

    /**
     * Builds a [RecyclerView.Adapter], already hooking an instance of [PolyAdapter] with it
     * @param items The items you want to show on the adapter
     * @return An instance of [RecyclerView.Adapter], ready to be used with polymorphic types
     */
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

    /**
     * Builds a [ListAdapter], already hooking an instance of [PolyAdapter] with it
     * @param asyncDifferConfig A config used for the [ListAdapter]
     * @return An instance of [ListAdapter], ready to be used with polymorphic types
     */
    fun buildForListAdapter(asyncDifferConfig: AsyncDifferConfig<TItem>): ListAdapter<TItem, RecyclerView.ViewHolder> {
        return object : ListAdapter<TItem, RecyclerView.ViewHolder>(asyncDifferConfig) {

            private val multiAdapter = buildOnlyAdapter(this::getItem)

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ) = multiAdapter.onCreateViewHolder(parent, viewType)

            override fun getItemViewType(position: Int) = multiAdapter.getItemViewType(position)

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
                multiAdapter.onBindViewHolder(holder, position)
        }
    }

    /**
     * Builds a [ListAdapter], already hooking an instance of [PolyAdapter] with it
     * @param diffCallback A [DiffUtil.ItemCallback] used for the [ListAdapter]
     * @return An instance of [ListAdapter], ready to be used with polymorphic types
     */
    fun buildForListAdapter(diffCallback: DiffUtil.ItemCallback<TItem>): ListAdapter<TItem, RecyclerView.ViewHolder> {
        return object : ListAdapter<TItem, RecyclerView.ViewHolder>(diffCallback) {

            private val multiAdapter = buildOnlyAdapter(this::getItem)

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ) = multiAdapter.onCreateViewHolder(parent, viewType)

            override fun getItemViewType(position: Int) = multiAdapter.getItemViewType(position)

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
                multiAdapter.onBindViewHolder(holder, position)
        }
    }

    /**
     * Builds an instance of [PolyAdapter], if you want to use it inside your own adapter for more flexibility
     * @param itemGetter A lambda that retrieves items for a given position
     * @return The instance of [PolyAdapter]
     */
    fun buildOnlyAdapter(itemGetter: ItemGetter<TItem>) =
        PolyAdapter(viewTypeRetriever, delegatesMap, itemGetter)
}

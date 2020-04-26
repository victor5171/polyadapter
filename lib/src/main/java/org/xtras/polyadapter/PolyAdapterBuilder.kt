package org.xtras.polyadapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.xtras.polyadapter.delegatesmap.MutableDelegatesMapBuilder
import org.xtras.polyadapter.paging.EmptyPlaceholderViewHolderDelegate
import org.xtras.polyadapter.paging.NullItem
import org.xtras.polyadapter.paging.NullSafeDelegatesMapBuilder
import org.xtras.polyadapter.paging.NullSafeViewTypeRetriever
import org.xtras.polyadapter.paging.RealItem
import org.xtras.polyadapter.viewtyperetrievers.ViewTypeRetriever

private const val PAGING_PLACEHOLDER_VIEW_TYPE = -1

/**
 * Builder to generate instances of the [PolyAdapter] or some adapter types
 * It has an idiomatic structure to ease the use to add delegates
 * @param TItem the supertype of all items inside the adapter
 * @param TViewTypeRetriever the type of the [ViewTypeRetriever] this PolyAdapter shall use
 * @param viewTypeRetriever Used to generate ViewTypes inside the [PolyAdapter]
 */
class PolyAdapterBuilder<TItem, in TViewTypeRetriever : ViewTypeRetriever<TItem>>(
    private val viewTypeRetriever: TViewTypeRetriever
) {
    private val delegatesMap =
        MutableDelegatesMapBuilder<TItem>()

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
     * Builds a [PagedListAdapter], already hooking an instance of [PolyAdapter] with it
     * @param diffCallback A [DiffUtil.ItemCallback] used for the [PagedListAdapter]
     * @param placeholderViewHolderDelegate A [ViewHolderDelegate] used for the placeholders,
     * if you don't want to use placeholders, don't send anything,
     * for more info check here [https://developer.android.com/topic/libraries/architecture/paging/ui#provide-placeholders]
     * @return An instance of [PagedListAdapter], ready to be used with polymorphic types
     */
    fun buildForPagedListAdapter(
        diffCallback: DiffUtil.ItemCallback<TItem>,
        placeholderViewHolderDelegate: ViewHolderDelegate<NullItem<TItem>, *> = EmptyPlaceholderViewHolderDelegate()
    ): PagedListAdapter<TItem, RecyclerView.ViewHolder> {
        return object : PagedListAdapter<TItem, RecyclerView.ViewHolder>(diffCallback) {

            private val multiAdapter = PolyAdapter(
                NullSafeViewTypeRetriever(
                    viewTypeRetriever,
                    PAGING_PLACEHOLDER_VIEW_TYPE
                ),
                NullSafeDelegatesMapBuilder(
                    PAGING_PLACEHOLDER_VIEW_TYPE,
                    placeholderViewHolderDelegate,
                    delegatesMap
                ).buildMap()
            ) { position ->
                getItem(position)?.let { RealItem(it) } ?: NullItem()
            }

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
     * Builds a [PagedListAdapter], already hooking an instance of [PolyAdapter] with it
     * @param asyncDifferConfig A config used for the [ListAdapter]
     * @param placeholderViewHolderDelegate A [ViewHolderDelegate] used for the placeholders,
     * if you don't want to use placeholders, don't send anything,
     * for more info check here [https://developer.android.com/topic/libraries/architecture/paging/ui#provide-placeholders]
     * @return An instance of [PagedListAdapter], ready to be used with polymorphic types
     */
    fun buildForPagedListAdapter(
        asyncDifferConfig: AsyncDifferConfig<TItem>,
        placeholderViewHolderDelegate: ViewHolderDelegate<NullItem<TItem>, *> = EmptyPlaceholderViewHolderDelegate()
    ): PagedListAdapter<TItem, RecyclerView.ViewHolder> {
        return object : PagedListAdapter<TItem, RecyclerView.ViewHolder>(asyncDifferConfig) {

            private val multiAdapter = PolyAdapter(
                NullSafeViewTypeRetriever(
                    viewTypeRetriever,
                    PAGING_PLACEHOLDER_VIEW_TYPE
                ),
                NullSafeDelegatesMapBuilder(
                    PAGING_PLACEHOLDER_VIEW_TYPE,
                    placeholderViewHolderDelegate,
                    delegatesMap
                ).buildMap()
            ) { position ->
                getItem(position)?.let { RealItem(it) } ?: NullItem()
            }

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
        PolyAdapter(viewTypeRetriever, delegatesMap.buildMap(), itemGetter)
}

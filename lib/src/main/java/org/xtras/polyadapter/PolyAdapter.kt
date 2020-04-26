package org.xtras.polyadapter

import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference
import org.xtras.polyadapter.viewtyperetrievers.ViewTypeRetriever

/**
 * Adapter that encapsulates the work to get different ViewHolders for different items that inherit a similar structure
 * You can use it inside your own adapter or use the [PolyAdapterBuilder] to build one from scratch without having to make a new class
 * Don't instantiate itself, use [PolyAdapterBuilder] to create one
 * @param viewTypeRetriever Translates every value into a ViewType integer
 * @param delegates A map containing the ViewTypes and its delegates, if you are using your own adapter you have to provide your own map
 * @param itemGetter A function that provides items for every position, you can use getItem for ListAdapters for example
 * @param TItem the supertype of the items of this adapter
 */
class PolyAdapter<TItem> internal constructor(
    @VisibleForTesting internal val viewTypeRetriever: ViewTypeRetriever<TItem>,
    private val delegates: Map<Int, ViewHolderDelegate<TItem, RecyclerView.ViewHolder>>,
    private val itemGetter: ItemGetter<TItem>
) {
    /**
     * Holds a map of the last containing the ViewTypes as keys and the last fetched items of each ViewType
     * It's used to try to the get the last item when a error happens trying to get a delegate for a specific item
     * It uses WeakReference so the Adapter doesn't hold unnecessary references to its inner items
     */
    private val mapOfFetchedItems = mutableMapOf<Int, WeakReference<TItem>>()

    /**
     * Returns a delegate for the sent [viewType]
     * @param viewType An integer value representing the ViewType you want to get a delegate
     * @return The delegate that is responsible for handling ViewHolders for this [viewType]
     */
    private fun getDelegate(viewType: Int): ViewHolderDelegate<TItem, RecyclerView.ViewHolder> {
        return delegates[viewType]
            ?: run {
                mapOfFetchedItems[viewType]?.get()?.let {
                    throw DelegateNotFoundException.buildWithDetailedMessage(
                        it,
                        viewType,
                        delegates
                    )
                } ?: throw DelegateNotFoundException.buildWithGenericMessage(viewType, delegates)
            }
    }

    /**
     * Returns an ItemViewType for the item at the [position]
     * @param position The position you want to get the ViewType
     * @return An integer representing the ViewType of the item at [position]
     */
    fun getItemViewType(position: Int): Int {
        val item = itemGetter(position)
        val viewType = viewTypeRetriever.getViewType(item)

        mapOfFetchedItems[viewType] = WeakReference(item)

        return viewType
    }

    /**
     * Creates a ViewHolder for handling the specific [viewType]
     * You can just proxy this call to your Adapter if you want to use your own adapter
     * @param parent The parent ViewGroup, used to instantiate ViewHolders
     * @param viewType The ViewType of the ViewHolder that is going to be created
     * @return The ViewHolder that handles items for [viewType]
     */
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        getDelegate(viewType).onCreateViewHolder(parent)

    /**
     * Binds an item at the [position] into the ViewHolder [holder]
     * You can just proxy this call to your Adapter if you want to use your own adapter
     * @param holder The holder that is going to be receive the item at the [position]
     * @param position The position of the item that is going to be drawn
     */
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val delegate = getDelegate(getItemViewType(position))

        val item = itemGetter(position)

        delegate.onBindViewHolder(holder, item)
    }
}

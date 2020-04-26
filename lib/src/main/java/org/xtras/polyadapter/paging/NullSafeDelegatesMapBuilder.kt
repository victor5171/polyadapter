package org.xtras.polyadapter.paging

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.xtras.polyadapter.ViewHolderDelegate
import org.xtras.polyadapter.delegatesmap.DelegatesMapBuilder

/**
 * Proxies with another [DelegatesMapBuilder], converting the delegates to support Null Safe types and placeholders for paging adapters
 * @param TItem The item of the adapter
 * @param nullViewType The ViewType that should be used for placeholders
 * @param placeholderViewHolderDelegate The delegated that should be assigned for placeholders
 * @param originalDelegatesMapBuilder The original [DelegatesMapBuilder], this builder proxies the call to the original builder for
 * non placeholder types
 */
internal class NullSafeDelegatesMapBuilder<TItem>(
    private val nullViewType: Int,
    private val placeholderViewHolderDelegate: ViewHolderDelegate<NullItem<TItem>, *>,
    private val originalDelegatesMapBuilder: DelegatesMapBuilder<TItem>
) : DelegatesMapBuilder<NullSafeItem<TItem>> {

    /**
     * Proxies the call to the underlying [ViewHolderDelegate], for non null values (not placeholders)
     * @param originalDelegate The original delegate that should receive the calls
     */
    @Suppress("UNCHECKED_CAST")
    private fun proxyViewHolderDelegateForRealItem(
        originalDelegate: ViewHolderDelegate<TItem, RecyclerView.ViewHolder>
    ) = object : ViewHolderDelegate<RealItem<TItem>, RecyclerView.ViewHolder> {
        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            return originalDelegate.onCreateViewHolder(parent)
        }

        override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            item: RealItem<TItem>
        ) {
            return originalDelegate.onBindViewHolder(holder, item.item)
        }
    } as ViewHolderDelegate<NullSafeItem<TItem>, RecyclerView.ViewHolder>

    /**
     * Creates a immutable map where the keys are the viewTypes and the values are [ViewHolderDelegate], related to the viewTypes
     * @return The immutable map
     */
    override fun buildMap(): Map<Int, ViewHolderDelegate<NullSafeItem<TItem>, RecyclerView.ViewHolder>> {

        val originalMap = originalDelegatesMapBuilder.buildMap()

        if (originalMap.containsKey(nullViewType)) {
            throw Exception("There's already a viewType registered with nullViewType: $nullViewType, this value should be dedicated to a null view type! Please change the value for null view type on the constructor!")
        }

        return mutableMapOf<Int, ViewHolderDelegate<NullSafeItem<TItem>, RecyclerView.ViewHolder>>().also {

            @Suppress("UNCHECKED_CAST")
            it[nullViewType] = placeholderViewHolderDelegate as ViewHolderDelegate<NullSafeItem<TItem>, RecyclerView.ViewHolder>

            originalMap.forEach { (viewType, viewHolderDelegate) ->
                it[viewType] = proxyViewHolderDelegateForRealItem(viewHolderDelegate)
            }
        }
    }
}

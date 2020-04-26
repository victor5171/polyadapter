package org.xtras.polyadapter.delegatesmap

import androidx.recyclerview.widget.RecyclerView
import org.xtras.polyadapter.ViewHolderDelegate

/**
 * Represents a mutable structure that holds [ViewHolderDelegate], binding them with ViewTypes
 */
internal class MutableDelegatesMapBuilder<TItem> :
    DelegatesMapBuilder<TItem> {
    private val delegates = mutableMapOf<Int, ViewHolderDelegate<TItem, RecyclerView.ViewHolder>>()

    /**
     * Binds the [delegate] with the [viewType]
     * Use this method if you want to register a delegate for [TChildItem], that inherits from [TItem]
     * It's allowed to replace the delegate for a [viewType] multiple times
     * @param viewType The ViewType you want to register the [delegate]
     * @param delegate The registered delegate
     * @param TChildItem A type that must inherit [TItem]
     * @param TViewHolder The type of the ViewHolder
     * @return The same instance of this [MutableDelegatesMapBuilder], for chaining calls
     */
    fun <TChildItem : TItem, TViewHolder : RecyclerView.ViewHolder> registerDelegate(
        viewType: Int,
        delegate: ViewHolderDelegate<TChildItem, TViewHolder>
    ) = apply {
        @Suppress("UNCHECKED_CAST")
        delegates[viewType] = delegate as ViewHolderDelegate<TItem, RecyclerView.ViewHolder>
    }

    /**
     * Creates a immutable map where the keys are the viewTypes and the values are [ViewHolderDelegate], related to the viewTypes
     * @return The immutable map
     */
    override fun buildMap(): Map<Int, ViewHolderDelegate<TItem, RecyclerView.ViewHolder>> {
        return delegates
    }
}

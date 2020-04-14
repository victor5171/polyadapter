package org.xtras.polyadapter

import androidx.recyclerview.widget.RecyclerView

/**
 * Represents a structure that holds [ViewHolderDelegate], binding them with ViewTypes
 */
class DelegatesMap<TItem> {
    private val delegates = mutableMapOf<Int, ViewHolderDelegate<TItem, RecyclerView.ViewHolder>>()

    /**
     * Gets a [ViewHolderDelegate] for the ViewType
     * @param viewType The ViewType you want to get the [ViewHolderDelegate]
     * @return The value of the [ViewHolderDelegate] for the [viewType], if there's one bound, otherwise null
     */
    operator fun get(viewType: Int): ViewHolderDelegate<TItem, RecyclerView.ViewHolder>? {
        return delegates[viewType]
    }

    /**
     * Binds the [delegate] with the [viewType]
     * Use this method if you want to register a delegate for [TChildItem], that inherits from [TItem]
     * WARNING: Don't try to register another [delegate] for an already registered [viewType], otherwise an exception will be thrown
     * @param viewType The ViewType you want to register the [delegate]
     * @param delegate The registered delegate
     * @param TChildItem A type that must inherit [TItem]
     * @param TViewHolder The type of the ViewHolder
     * @return The same instance of this [DelegatesMap], for chaining calls
     */
    fun <TChildItem : TItem, TViewHolder : RecyclerView.ViewHolder> registerDelegate(
        viewType: Int,
        delegate: ViewHolderDelegate<TChildItem, TViewHolder>
    ) = apply {
        @Suppress("UNCHECKED_CAST")
        delegates[viewType] = delegate as ViewHolderDelegate<TItem, RecyclerView.ViewHolder>
    }

    /**
     * Creates a string representing all the registered delegates
     */
    override fun toString(): String {
        return delegates.toString()
    }
}

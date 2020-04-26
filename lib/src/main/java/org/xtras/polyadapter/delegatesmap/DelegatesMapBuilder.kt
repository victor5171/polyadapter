package org.xtras.polyadapter.delegatesmap

import androidx.recyclerview.widget.RecyclerView
import org.xtras.polyadapter.ViewHolderDelegate

/**
 * Represents a structure that holds [ViewHolderDelegate], binding them with ViewTypes
 */
interface DelegatesMapBuilder<TItem> {
    /**
     * Creates a immutable map where the keys are the viewTypes and the values are [ViewHolderDelegate], related to the viewTypes
     * @return The immutable map
     */
    fun buildMap(): Map<Int, ViewHolderDelegate<TItem, RecyclerView.ViewHolder>>
}

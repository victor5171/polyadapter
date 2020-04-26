package org.xtras.polyadapter.paging

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.xtras.polyadapter.ViewHolderDelegate

/**
 * [ViewHolderDelegate] used for placeholders inside the Paging Library
 * You should use this delegate if you don't want to support placeholders on your adapter
 * The library already uses this implementation in case you don't set a delegate on the builder
 * @param TItem The type of the items inside the adapter
 */
internal class EmptyPlaceholderViewHolderDelegate<TItem> :
    ViewHolderDelegate<NullItem<TItem>, RecyclerView.ViewHolder> {

    /**
     * Returns a empty recycler view
     */
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(parent) {}
    }

    /**
     * Does nothing, because it's an empty ViewHolder
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: NullItem<TItem>) {}
}

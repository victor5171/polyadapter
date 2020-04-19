package org.xtras.polyadapter

import android.view.ViewGroup

class BindableViewHolderDelegate<in TItem, TViewHolder : BindableViewHolder<TItem>>(
    private val viewHolderCreator: (parent: ViewGroup) -> TViewHolder
) : ViewHolderDelegate<TItem, TViewHolder> {

    override fun onCreateViewHolder(parent: ViewGroup) = viewHolderCreator(parent)

    override fun onBindViewHolder(holder: TViewHolder, item: TItem) {
        holder.bind(item)
    }
}

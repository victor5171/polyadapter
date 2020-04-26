package org.xtras.polyadapter

import android.view.ViewGroup

/**
 * An easy-to-use [ViewHolderDelegate] that works with [BindableViewHolder]
 * @param TItem The type of items that this delegate will work with
 * @param TViewHolder The type of the ViewHolder that this delegate will create and bind, it should inherit [BindableViewHolder]
 * @param viewHolderCreator A lambda that returns a new instance of [TViewHolder], using a parent [ViewGroup] to inflate layouts
 */
class BindableViewHolderDelegate<in TItem, TViewHolder : BindableViewHolder<TItem>>(
    private val viewHolderCreator: (parent: ViewGroup) -> TViewHolder
) : ViewHolderDelegate<TItem, TViewHolder> {

    override fun onCreateViewHolder(parent: ViewGroup) = viewHolderCreator(parent)

    override fun onBindViewHolder(holder: TViewHolder, item: TItem) {
        holder.bind(item)
    }
}

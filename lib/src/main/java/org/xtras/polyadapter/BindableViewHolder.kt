package org.xtras.polyadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * An easy to use ViewHolder, that takes care of inflating the layout and has a ready-to-use bind method so you can bind the data
 * @param TItem The type of the item that will be displayed on this ViewHolder
 * @param parent The parent view that this ViewHolder will be drawn into
 */
abstract class BindableViewHolder<in TItem>(parent: View) :
    RecyclerView.ViewHolder(parent) {

    /**
     * Option to already inflate [layoutRes] into [containerView], if you use this overload you just need to send the layout
     * that will be used for this ViewHolder
     */
    constructor(containerView: ViewGroup, @LayoutRes layoutRes: Int) : this(containerView.context.let {
        val layoutInflater = LayoutInflater.from(it)
        layoutInflater.inflate(layoutRes, containerView, false)
    })

    /**
     * Binds [item] into this ViewHolder
     * Here you should set labels, views, etc …
     * @param item The item that will be bound into this ViewHolder
     */
    abstract fun bind(item: TItem)
}

package org.xtras.polyadapter.app.viewholders

import android.view.ViewGroup
import org.xtras.polyadapter.BindableViewHolder
import org.xtras.polyadapter.app.R
import org.xtras.polyadapter.app.items.LoadingItem

class LoadingViewHolder(parent: ViewGroup) :
    BindableViewHolder<LoadingItem>(parent, R.layout.list_item_loading) {
    override fun bind(item: LoadingItem) {}
}

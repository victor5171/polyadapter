package org.xtras.polyadapter.app.viewholders

import android.view.ViewGroup
import org.xtras.polyadapter.BindableViewHolder
import org.xtras.polyadapter.app.R
import org.xtras.polyadapter.app.items.BodyItem
import org.xtras.polyadapter.paging.NullItem

class PlaceholderViewHolder(parent: ViewGroup) :
    BindableViewHolder<NullItem<BodyItem>>(parent, R.layout.list_item_placeholder) {
    override fun bind(item: NullItem<BodyItem>) {
        println()
    }
}

package org.xtras.polyadapter.app.viewholders

import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_icon_category.view.*
import org.xtras.polyadapter.BindableViewHolder
import org.xtras.polyadapter.app.R
import org.xtras.polyadapter.app.items.IconCategory

class IconCategoryViewHolder(parent: ViewGroup) :
    BindableViewHolder<IconCategory>(parent, R.layout.list_item_icon_category) {
    override fun bind(item: IconCategory) {
        with(itemView) {
            icon.setImageResource(item.iconResId)
            textView.text = item.name
        }
    }
}

package org.xtras.polyadapter.app.viewholders

import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_background_category.view.*
import org.xtras.polyadapter.BindableViewHolder
import org.xtras.polyadapter.app.R
import org.xtras.polyadapter.app.items.BackgroundCategory

class BackgroundCategoryViewHolder(parent: ViewGroup) :
    BindableViewHolder<BackgroundCategory>(parent, R.layout.list_item_background_category) {
    override fun bind(item: BackgroundCategory) {
        with(itemView) {
            parentLayout.setBackgroundColor(item.backgroundColor)

            val hexColor = String.format("#%06X", 0xFFFFFF and item.backgroundColor)
            textView.text = hexColor
        }
    }
}

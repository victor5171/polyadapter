package org.xtras.polyadapter.app.viewholders

import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_text_category.view.*
import org.xtras.polyadapter.BindableViewHolder
import org.xtras.polyadapter.app.R
import org.xtras.polyadapter.app.items.TextCategory

class TextCategoryViewHolder(parent: ViewGroup) :
    BindableViewHolder<TextCategory>(parent, R.layout.list_item_text_category) {

    override fun bind(item: TextCategory) {
        itemView.textView.text = item.text
    }
}

package org.xtras.polyadapter.app.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_header.view.*
import org.xtras.polyadapter.app.R

class HeaderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    parent.let {
        val layoutInflater = LayoutInflater.from(it.context)
        layoutInflater.inflate(R.layout.list_item_header, parent, false)
    }
) {
    fun setText(text: String) {
        itemView.headerTextView.text = text
    }
}

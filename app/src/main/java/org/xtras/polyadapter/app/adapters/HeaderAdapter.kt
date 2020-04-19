package org.xtras.polyadapter.app.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.xtras.polyadapter.app.viewholders.HeaderViewHolder

class HeaderAdapter(private val texts: List<String>) : RecyclerView.Adapter<HeaderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return HeaderViewHolder(parent)
    }

    override fun getItemCount() = texts.size

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.setText(texts[position])
    }
}

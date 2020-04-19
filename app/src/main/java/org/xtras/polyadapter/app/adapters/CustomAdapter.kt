package org.xtras.polyadapter.app.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.xtras.polyadapter.app.adapterbuilders.BodyAdapterBuilder
import org.xtras.polyadapter.app.items.BodyItem

class CustomAdapter(val items: List<BodyItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val polyAdapter = BodyAdapterBuilder.buildForCustomAdapter(items)

    override fun getItemViewType(position: Int) = polyAdapter.getItemViewType(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        polyAdapter.onCreateViewHolder(parent, viewType)

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        polyAdapter.onBindViewHolder(holder, position)
}

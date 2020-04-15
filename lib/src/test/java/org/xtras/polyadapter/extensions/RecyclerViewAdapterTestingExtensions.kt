package org.xtras.polyadapter.extensions

import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView

/**
 * Simulates a adapter creating the holders and calling bind
 * @param parent Required parent to create a ViewHolder
 * @param position The position you want to simulate the drawing
 */
@VisibleForTesting
fun RecyclerView.Adapter<RecyclerView.ViewHolder>.callUntilBind(parent: ViewGroup, position: Int) {
    val viewType = getItemViewType(position)
    val viewHolder = createViewHolder(parent, viewType)
    bindViewHolder(viewHolder, position)
}

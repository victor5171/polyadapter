package org.xtras.polyadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BindableViewHolder<in TItem>(parent: View) :
    RecyclerView.ViewHolder(parent) {

    constructor(containerView: ViewGroup, @LayoutRes layoutRes: Int) : this(containerView.context.let {
        val layoutInflater = LayoutInflater.from(it)
        layoutInflater.inflate(layoutRes, containerView, false)
    })

    abstract fun bind(item: TItem)
}

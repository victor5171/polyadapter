package org.xtras.polyadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Delegates the logic for a ViewHolder responsible for drawing [TValue] items
 *  @param TValue the value that this ViewHolder wraps
 *  @param TViewHolder
 */
interface ViewHolderDelegate<in TValue, TViewHolder : RecyclerView.ViewHolder> {
    /**
     * Creates the ViewHolder for drawing [TValue] items
     * @param parent Use this ViewGroup to instantiate your ViewHolder
     * @return The ViewHolder that will draw every [TValue]
     */
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    /**
     * Binds the data inside [item] into the ViewHolder
     * Here you have to cast [holder] to your ViewHolder
     */
    fun onBindViewHolder(holder: TViewHolder, item: TValue)
}

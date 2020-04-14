package org.xtras.polyadapter

/**
 * Retriever the ViewType of [TItem] to be used inside the [PolyAdapter]
 * You have to provide an [Int] value so the RecyclerView Adapter can differentiate between items
 */
interface ViewTypeRetriever<in TItem> {

    /**
     * It should return an [Int] value that represents [TItem] inside the Adapter
     */
    fun getViewType(value: TItem): Int
}

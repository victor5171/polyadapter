package org.xtras.polyadapter.paging

import org.xtras.polyadapter.viewtyperetrievers.ViewTypeRetriever

/**
 * [ViewTypeRetriever] used for Paging Adapters, it supports placeholders and proxies the call to
 * the [originalViewTypeRetriever] if the value is not a placeholder
 * @param TItem The item used on the adapter
 * @param originalViewTypeRetriever The original ViewTypeRetriever that don't support placeholders or null values
 * @param nullViewType The view type that should be returned for placeholders
 */
internal class NullSafeViewTypeRetriever<TItem>(
    private val originalViewTypeRetriever: ViewTypeRetriever<TItem>,
    private val nullViewType: Int
) : ViewTypeRetriever<NullSafeItem<TItem>> {

    /**
     * It should return an [Int] value that represents [TItem] inside the Adapter
     */
    override fun getViewType(value: NullSafeItem<TItem>): Int {
        return when (value) {
            is RealItem -> return originalViewTypeRetriever.getViewType(value.item)
            is NullItem -> nullViewType
        }
    }
}

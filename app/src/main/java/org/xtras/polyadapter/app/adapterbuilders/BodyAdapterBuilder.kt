package org.xtras.polyadapter.app.adapterbuilders

import androidx.annotation.VisibleForTesting
import org.xtras.polyadapter.BindableViewHolderDelegate
import org.xtras.polyadapter.ClassViewTypeRetriever
import org.xtras.polyadapter.PolyAdapterBuilder
import org.xtras.polyadapter.app.items.BodyItem
import org.xtras.polyadapter.app.viewholders.BackgroundCategoryViewHolder
import org.xtras.polyadapter.app.viewholders.IconCategoryViewHolder
import org.xtras.polyadapter.app.viewholders.LoadingViewHolder
import org.xtras.polyadapter.app.viewholders.TextCategoryViewHolder
import org.xtras.polyadapter.registerDelegate

internal object BodyAdapterBuilder {
    @VisibleForTesting
    fun createPolyAdapterBuilder(): PolyAdapterBuilder<BodyItem, ClassViewTypeRetriever<BodyItem>> {
        val classViewTypeRetriever = ClassViewTypeRetriever<BodyItem>()
        return PolyAdapterBuilder(classViewTypeRetriever)
            .registerDelegate(BindableViewHolderDelegate { IconCategoryViewHolder(it) })
            .registerDelegate(BindableViewHolderDelegate { TextCategoryViewHolder(it) })
            .registerDelegate(BindableViewHolderDelegate { BackgroundCategoryViewHolder(it) })
            .registerDelegate(BindableViewHolderDelegate { LoadingViewHolder(it) })
    }

    fun buildForRecyclerView(items: List<BodyItem>) = createPolyAdapterBuilder().buildForRecyclerView(items)
}

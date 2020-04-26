package org.xtras.polyadapter.testhelpers

import android.content.Context
import android.widget.FrameLayout
import androidx.annotation.VisibleForTesting
import org.xtras.polyadapter.PolyAdapter
import org.xtras.polyadapter.PolyAdapterBuilder
import org.xtras.polyadapter.viewtyperetrievers.ViewTypeRetriever

/**
 * Useful class to write Unit tests for your adapter and to check if all delegates were registered
 * @param TItem The supertype of your adapter
 * @param TViewTypeRetriever The type of the [ViewTypeRetriever] used in your adapter
 * @param polyAdapterBuilder You have to use a [PolyAdapterTester] so this adapter can build a custom [PolyAdapter]
 * @param allItemsGenerator A lambda that generates all the possible types the adapter will use for [TItem]
 * @see [EnumItemGenerator] and [SealedClassItemGenerator]
 */
@VisibleForTesting
class PolyAdapterTester<TItem, TViewTypeRetriever : ViewTypeRetriever<TItem>>(
    polyAdapterBuilder: PolyAdapterBuilder<TItem, TViewTypeRetriever>,
    allItemsGenerator: AllItemsGenerator<TItem>
) {

    private val allItems = allItemsGenerator().toList()

    private val polyAdapter = polyAdapterBuilder.buildOnlyAdapter(allItems::get)

    /**
     * Test the generated adapter, calling getViewItemType for all possible instances
     * @param equalsAsserter An assert function, here you can use the assertEquals function of your favorite Unit testing asserting library
     */
    @VisibleForTesting
    fun testGetItemViewType(equalsAsserter: (viewTypeFromViewTypeRetriever: Int, viewTypeFromAdapter: Int) -> Unit) {
        allItems.forEachIndexed { index, item ->
            equalsAsserter(
                polyAdapter.viewTypeRetriever.getViewType(item),
                polyAdapter.getItemViewType(index)
            )
        }
    }

    /**
     * Test the generated adapter, calling onCreateViewHolder for all possible instances
     * @param context A context for generating the parent layout, you can mock it using androidx.test:core-ktx for example
     */
    @VisibleForTesting
    fun testOnCreateViewHolder(context: Context) {
        val parent = FrameLayout(context)

        allItems.indices.forEach { index ->
            polyAdapter.onCreateViewHolder(parent, polyAdapter.getItemViewType(index))
        }
    }

    /**
     * Test the generated adapter, calling onBindViewHolder for all possible instances
     * @param context A context for generating the parent layout, you can mock it using androidx.test:core-ktx for example
     */
    @VisibleForTesting
    fun testOnBindViewHolder(context: Context) {
        val parent = FrameLayout(context)

        allItems.indices.forEach { index ->
            val viewHolder =
                polyAdapter.onCreateViewHolder(parent, polyAdapter.getItemViewType(index))
            polyAdapter.onBindViewHolder(viewHolder, index)
        }
    }

    /**
     * Test the generated adapter, calling all the methods from PolyAdapter
     * @param equalsAsserter An assert function, here you can use the assertEquals function of your favorite Unit testing asserting library
     * @param context A context for generating the parent layout, you can mock it using androidx.test:core-ktx for example
     */
    @VisibleForTesting
    fun testAll(
        context: Context,
        equalsAsserter: (viewTypeFromViewTypeRetriever: Int, viewTypeFromAdapter: Int) -> Unit
    ) {
        testGetItemViewType(equalsAsserter)
        testOnCreateViewHolder(context)
        testOnBindViewHolder(context)
    }
}

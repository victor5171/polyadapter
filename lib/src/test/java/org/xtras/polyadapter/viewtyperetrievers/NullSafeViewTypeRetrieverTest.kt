package org.xtras.polyadapter.viewtyperetrievers

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import org.xtras.polyadapter.paging.NullItem
import org.xtras.polyadapter.paging.NullSafeViewTypeRetriever
import org.xtras.polyadapter.paging.RealItem

class NullSafeViewTypeRetrieverTest {

    private interface Supertype

    @Test
    fun `When I try to get a viewType for a Null item, it should give the set null item type`() {
        val originalViewTypeRetriever = mockk<ViewTypeRetriever<Supertype>>()

        val nullSafeViewTypeRetriever =
            NullSafeViewTypeRetriever(
                originalViewTypeRetriever,
                -1
            )

        Assert.assertEquals(-1, nullSafeViewTypeRetriever.getViewType(NullItem()))
    }

    @Test
    fun `When I try to get a view type for a item that has a value, it should give the item view type returned by the original retriever`() {
        val supertypeInstance = mockk<Supertype>()

        val originalViewTypeRetriever = mockk<ViewTypeRetriever<Supertype>> {
            every { getViewType(supertypeInstance) } returns 1
        }

        val nullSafeViewTypeRetriever =
            NullSafeViewTypeRetriever(
                originalViewTypeRetriever,
                -1
            )

        Assert.assertEquals(1, nullSafeViewTypeRetriever.getViewType(
            RealItem(
                supertypeInstance
            )
        ))
    }
}

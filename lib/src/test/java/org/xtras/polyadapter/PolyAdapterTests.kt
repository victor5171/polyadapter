package org.xtras.polyadapter

import org.junit.Assert
import org.junit.Test

class PolyAdapterTests {

    @Suppress("UNCHECKED_CAST")
    private val delegates = DelegatesMap<Supertype>()
        .registerDelegate(1, Children1ViewHolderDelegate())
        .registerDelegate(2, Children2ViewHolderDelegate())

    @Test
    fun `When I try to get the ViewType for each item, it should work`() {
        val polyAdapter = PolyAdapter(SupertypeViewTypeRetriever, delegates, SupertypeItemGetter)

        Assert.assertEquals(1, polyAdapter.getItemViewType(0))
        Assert.assertEquals(2, polyAdapter.getItemViewType(1))
    }

    @Test(expected = Exception::class)
    fun `When I try to get the ViewType for a unregistered item, it shouldn't work`() {
        val failureItemGetter: ItemGetter<Supertype> = {
            UnregisteredChildren3
        }

        val polyAdapter = PolyAdapter(SupertypeViewTypeRetriever, delegates, failureItemGetter)

        polyAdapter.getItemViewType(0)
    }
}

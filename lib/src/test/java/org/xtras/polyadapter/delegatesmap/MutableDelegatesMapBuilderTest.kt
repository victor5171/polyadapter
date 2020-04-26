package org.xtras.polyadapter.delegatesmap

import org.junit.Assert
import org.junit.Test
import org.xtras.polyadapter.Children1ViewHolderDelegate
import org.xtras.polyadapter.Supertype
import org.xtras.polyadapter.SupertypeViewHolderDelegate

class MutableDelegatesMapBuilderTest {
    @Test
    fun `When I try to register the delegate for the view type, registering as a super type, it should work`() {
        val delegatesMap =
            MutableDelegatesMapBuilder<Supertype>()

        delegatesMap.registerDelegate(1,
            SupertypeViewHolderDelegate()
        )

        Assert.assertNotNull(delegatesMap.buildMap()[1])
    }

    @Test
    fun `When I try to register the delegate for the view type, it should work`() {
        val delegatesMap =
            MutableDelegatesMapBuilder<Supertype>()

        delegatesMap.registerDelegate(1,
            Children1ViewHolderDelegate()
        )

        Assert.assertNotNull(delegatesMap.buildMap()[1])
    }

    @Test
    fun `When I try to get a non registered delegate for a view type, it should return null`() {
        val delegatesMap =
            MutableDelegatesMapBuilder<Supertype>()

        Assert.assertNull(delegatesMap.buildMap()[1])
    }
}

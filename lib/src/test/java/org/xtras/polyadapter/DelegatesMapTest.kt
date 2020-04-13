package org.xtras.polyadapter

import org.junit.Assert
import org.junit.Test

class DelegatesMapTest {
    @Test
    fun `When I try to register the delegate for the view type, registering as a super type, it should work`() {
        val delegatesMap = DelegatesMap<Supertype>()

        delegatesMap.registerDelegate(1, SupertypeViewHolderDelegate())

        Assert.assertNotNull(delegatesMap[1])
    }

    @Test(expected = DelegateAlreadyRegisteredException::class)
    fun `When I try to register the same delegate for the view type, registering as a super type, it shouldn't work`() {
        val delegatesMap = DelegatesMap<Supertype>()

        delegatesMap.registerDelegate(1, SupertypeViewHolderDelegate())
        delegatesMap.registerDelegate(1, SupertypeViewHolderDelegate())
    }

    @Test
    fun `When I try to register the delegate for the view type, it should work`() {
        val delegatesMap = DelegatesMap<Supertype>()

        delegatesMap.registerDelegate(1, Children1ViewHolderDelegate())

        Assert.assertNotNull(delegatesMap[1])
    }

    @Test(expected = DelegateAlreadyRegisteredException::class)
    fun `When I try to register the same delegate for the view type, it shouldn't work`() {
        val delegatesMap = DelegatesMap<Supertype>()

        delegatesMap.registerDelegate(1, Children1ViewHolderDelegate())
        delegatesMap.registerDelegate(1, Children1ViewHolderDelegate())
    }

    @Test
    fun `When I try to get a non registered delegate for a view type, it should return null`() {
        val delegatesMap = DelegatesMap<Supertype>()

        Assert.assertNull(delegatesMap[1])
    }
}

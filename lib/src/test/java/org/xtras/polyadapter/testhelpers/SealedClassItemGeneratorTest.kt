@file:Suppress("CanSealedSubClassBeObject")

package org.xtras.polyadapter.testhelpers

import io.mockk.mockkClass
import org.junit.Assert
import org.junit.Test

class SealedClassItemGeneratorTest {
    private sealed class SupersealedClass {
        class Children1 : SupersealedClass()
        class Children2 : SupersealedClass()
    }

    @Test
    fun `When I try to use a SealedClassItemGenerator for SupersealedClass, it should give me all its children types to mock`() {
        val sealedClassItemGenerator =
            SealedClassItemGenerator.create<SupersealedClass> { mockkClass(it, relaxed = true) }

        val items = sealedClassItemGenerator().toList()

        Assert.assertEquals(2, items.size)
        Assert.assertEquals(1, items.count { it is SupersealedClass.Children1 })
        Assert.assertEquals(1, items.count { it is SupersealedClass.Children2 })
    }
}

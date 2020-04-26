package org.xtras.polyadapter

import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class BindableViewHolderDelegateTest {
    @Test
    fun `When I try to create a view holder and then bind an item to it, using the bindable view holder delegate, it should work`() {
        val bindableViewHolderDelegate =
            BindableViewHolderDelegate<Supertype, BindableViewHolder<Supertype>> {
                mockk(relaxed = true)
            }

        val viewHolder = bindableViewHolderDelegate.onCreateViewHolder(mockk())

        val supertype = mockk<Supertype>()
        bindableViewHolderDelegate.onBindViewHolder(viewHolder, supertype)

        verify { viewHolder.bind(supertype) }
    }
}

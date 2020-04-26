package org.xtras.polyadapter.delegatesmap

import android.content.Context
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.xtras.polyadapter.ViewHolderDelegate
import org.xtras.polyadapter.paging.NullItem
import org.xtras.polyadapter.paging.NullSafeDelegatesMapBuilder
import org.xtras.polyadapter.paging.RealItem

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class NullSafeDelegatesMapBuilderTest {

    private interface Supertype

    @Test
    fun `When I try to get a delegate for a null view type, it should give the sent placeholder delegate`() {
        val placeholderDelegate =
            mockk<ViewHolderDelegate<NullItem<Supertype>, RecyclerView.ViewHolder>>()
        val nullSafeDelegatesMap =
            NullSafeDelegatesMapBuilder(
                -1,
                placeholderDelegate,
                mockk(relaxed = true)
            )

        Assert.assertEquals(placeholderDelegate, nullSafeDelegatesMap.buildMap()[-1])
    }

    @Test(expected = Exception::class)
    fun `When I try to register a delegate for a real type, using the same view type for a null view type, it should throw an exception`() {
        val delegatesMap = mockk<DelegatesMapBuilder<Supertype>> {
            every { buildMap() } returns mapOf(-1 to mockk())
        }

        val nullSafeDelegatesMap =
            NullSafeDelegatesMapBuilder(
                -1,
                mockk(),
                delegatesMap
            )

        nullSafeDelegatesMap.buildMap()
    }

    @Test
    fun `When I try to use the delegate for a real type, it should proxy the calls to the original delegate`() {
        val originalDelegate = mockk<ViewHolderDelegate<Supertype, RecyclerView.ViewHolder>>(relaxed = true)

        val delegatesMap = mockk<DelegatesMapBuilder<Supertype>> {
            every { buildMap() } returns mapOf(1 to originalDelegate)
        }

        val nullSafeDelegatesMap =
            NullSafeDelegatesMapBuilder(
                -1,
                mockk(),
                delegatesMap
            )

        val delegate = nullSafeDelegatesMap.buildMap()[1]

        val context = ApplicationProvider.getApplicationContext<Context>()

        val parent = LinearLayout(context)

        val viewHolder = delegate!!.onCreateViewHolder(parent)

        verify(exactly = 1) { originalDelegate.onCreateViewHolder(parent) }

        val supertypeValue = mockk<Supertype>()

        delegate.onBindViewHolder(viewHolder,
            RealItem(supertypeValue)
        )

        verify(exactly = 1) { originalDelegate.onBindViewHolder(viewHolder, supertypeValue) }

        verify(exactly = 1) { delegatesMap.buildMap() }

        confirmVerified(originalDelegate, delegatesMap, supertypeValue)
    }
}

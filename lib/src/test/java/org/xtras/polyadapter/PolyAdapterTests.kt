package org.xtras.polyadapter

import android.content.Context
import android.widget.LinearLayout
import androidx.test.core.app.ApplicationProvider
import io.mockk.confirmVerified
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class PolyAdapterTests {

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

    @Test
    fun `When I try to create the view holders, it should give the correct view holders`() {
        val itemGetter: ItemGetter<Supertype> = {
            when (it) {
                0 -> Children1
                1 -> Children2
                else -> throw UnsupportedOperationException()
            }
        }

        val polyAdapter = PolyAdapter(SupertypeViewTypeRetriever, delegates, itemGetter)

        val context = ApplicationProvider.getApplicationContext<Context>()

        val parent = LinearLayout(context)

        Assert.assertTrue(polyAdapter.onCreateViewHolder(parent, 1) is Children1ViewHolder)
        Assert.assertTrue(polyAdapter.onCreateViewHolder(parent, 2) is Children2ViewHolder)
    }

    @Test
    fun `When I try to bind the view holders, it should call the function on the delegates`() {
        val itemGetter: ItemGetter<Supertype> = {
            when (it) {
                0 -> Children1
                1 -> Children2
                else -> throw UnsupportedOperationException()
            }
        }

        val spyChildren1ViewHolderDelegate = spyk<Children1ViewHolderDelegate>()

        val spyChildren2ViewHolderDelegate = spyk<Children2ViewHolderDelegate>()

        val delegates = DelegatesMap<Supertype>()
            .registerDelegate(1, spyChildren1ViewHolderDelegate)
            .registerDelegate(2, spyChildren2ViewHolderDelegate)

        val polyAdapter = PolyAdapter(SupertypeViewTypeRetriever, delegates, itemGetter)

        val context = ApplicationProvider.getApplicationContext<Context>()

        val parent = LinearLayout(context)

        val children1ViewHolder = Children1ViewHolder(parent)

        polyAdapter.onBindViewHolder(children1ViewHolder, 0)

        verify(exactly = 1) { spyChildren1ViewHolderDelegate.onBindViewHolder(children1ViewHolder, Children1) }

        val children2ViewHolder = Children2ViewHolder(parent)

        polyAdapter.onBindViewHolder(children2ViewHolder, 1)

        verify(exactly = 1) { spyChildren2ViewHolderDelegate.onBindViewHolder(children2ViewHolder, Children2) }

        confirmVerified(spyChildren1ViewHolderDelegate, spyChildren2ViewHolderDelegate)
    }
}

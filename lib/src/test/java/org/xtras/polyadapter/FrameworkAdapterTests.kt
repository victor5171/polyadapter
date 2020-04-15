package org.xtras.polyadapter

import android.content.Context
import android.widget.LinearLayout
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.test.core.app.ApplicationProvider
import io.mockk.confirmVerified
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.xtras.polyadapter.extensions.callUntilBind

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class FrameworkAdapterTests {
    @Test
    fun `When I try to create a polymorphic RecyclerView Adapter and bind its view holders, it should work`() {
        val spyChildren1ViewHolderDelegate = spyk<Children1ViewHolderDelegate>()

        val spyChildren2ViewHolderDelegate = spyk<Children2ViewHolderDelegate>()

        val recyclerViewAdapter = PolyAdapterBuilder(SupertypeViewTypeRetriever)
            .registerDelegate(1, spyChildren1ViewHolderDelegate)
            .registerDelegate(2, spyChildren2ViewHolderDelegate)
            .buildForRecyclerView(listOf(Children1, Children2))

        Assert.assertEquals(2, recyclerViewAdapter.itemCount)

        val context = ApplicationProvider.getApplicationContext<Context>()

        val parent = LinearLayout(context)

        recyclerViewAdapter.callUntilBind(parent, 0)

        verify(exactly = 1) { spyChildren1ViewHolderDelegate.onCreateViewHolder(parent) }
        verify(exactly = 1) { spyChildren1ViewHolderDelegate.onBindViewHolder(any(), Children1) }

        recyclerViewAdapter.callUntilBind(parent, 1)

        verify(exactly = 1) { spyChildren2ViewHolderDelegate.onCreateViewHolder(parent) }
        verify(exactly = 1) { spyChildren2ViewHolderDelegate.onBindViewHolder(any(), Children2) }

        confirmVerified(spyChildren1ViewHolderDelegate, spyChildren2ViewHolderDelegate)
    }

    @Test
    fun `When I try to create a polymorphic List Adapter with DiffUtil and bind its view holders, it should work`() {
        val spyChildren1ViewHolderDelegate = spyk<Children1ViewHolderDelegate>()

        val spyChildren2ViewHolderDelegate = spyk<Children2ViewHolderDelegate>()

        val diffUtil = object : DiffUtil.ItemCallback<Supertype>() {
            override fun areItemsTheSame(oldItem: Supertype, newItem: Supertype): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Supertype, newItem: Supertype): Boolean {
                return oldItem == newItem
            }
        }

        val listAdapter = PolyAdapterBuilder(SupertypeViewTypeRetriever)
            .registerDelegate(1, spyChildren1ViewHolderDelegate)
            .registerDelegate(2, spyChildren2ViewHolderDelegate)
            .buildForListAdapter(diffUtil)

        listAdapter.submitList(listOf(Children1, Children2))

        val context = ApplicationProvider.getApplicationContext<Context>()

        val parent = LinearLayout(context)

        listAdapter.callUntilBind(parent, 0)

        verify(exactly = 1) { spyChildren1ViewHolderDelegate.onCreateViewHolder(parent) }
        verify(exactly = 1) { spyChildren1ViewHolderDelegate.onBindViewHolder(any(), Children1) }

        listAdapter.callUntilBind(parent, 1)

        verify(exactly = 1) { spyChildren2ViewHolderDelegate.onCreateViewHolder(parent) }
        verify(exactly = 1) { spyChildren2ViewHolderDelegate.onBindViewHolder(any(), Children2) }

        confirmVerified(spyChildren1ViewHolderDelegate, spyChildren2ViewHolderDelegate)
    }

    @Test
    fun `When I try to create a polymorphic List Adapter with AsyncDifferConfig and bind its view holders, it should work`() {
        val spyChildren1ViewHolderDelegate = spyk<Children1ViewHolderDelegate>()

        val spyChildren2ViewHolderDelegate = spyk<Children2ViewHolderDelegate>()

        val diffUtil = object : DiffUtil.ItemCallback<Supertype>() {
            override fun areItemsTheSame(oldItem: Supertype, newItem: Supertype): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Supertype, newItem: Supertype): Boolean {
                return oldItem == newItem
            }
        }

        val asyncDifferConfig = AsyncDifferConfig.Builder(diffUtil)
            .build()

        val listAdapter = PolyAdapterBuilder(SupertypeViewTypeRetriever)
            .registerDelegate(1, spyChildren1ViewHolderDelegate)
            .registerDelegate(2, spyChildren2ViewHolderDelegate)
            .buildForListAdapter(asyncDifferConfig)

        listAdapter.submitList(listOf(Children1, Children2))

        val context = ApplicationProvider.getApplicationContext<Context>()

        val parent = LinearLayout(context)

        listAdapter.callUntilBind(parent, 0)

        verify(exactly = 1) { spyChildren1ViewHolderDelegate.onCreateViewHolder(parent) }
        verify(exactly = 1) { spyChildren1ViewHolderDelegate.onBindViewHolder(any(), Children1) }

        listAdapter.callUntilBind(parent, 1)

        verify(exactly = 1) { spyChildren2ViewHolderDelegate.onCreateViewHolder(parent) }
        verify(exactly = 1) { spyChildren2ViewHolderDelegate.onBindViewHolder(any(), Children2) }

        confirmVerified(spyChildren1ViewHolderDelegate, spyChildren2ViewHolderDelegate)
    }
}

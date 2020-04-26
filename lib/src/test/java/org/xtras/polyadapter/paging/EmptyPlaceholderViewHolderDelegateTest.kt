package org.xtras.polyadapter.paging

import android.widget.LinearLayout
import androidx.test.core.app.ApplicationProvider
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.xtras.polyadapter.Supertype

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class EmptyPlaceholderViewHolderDelegateTest {
    @Test
    fun `When I try to create a view holder and bind something to it, using an empty placeholder delegate, it should work`() {
        val parent = LinearLayout(ApplicationProvider.getApplicationContext())

        val emptyPlaceholderViewHolderDelegate = EmptyPlaceholderViewHolderDelegate<Supertype>()

        val viewHolder = emptyPlaceholderViewHolderDelegate.onCreateViewHolder(parent)

        emptyPlaceholderViewHolderDelegate.onBindViewHolder(viewHolder, NullItem())
    }
}

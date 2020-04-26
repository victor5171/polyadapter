package org.xtras.polyadapter

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.test.core.app.ApplicationProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class BindableViewHolderTest {
    @Test
    fun `When I try to create a children of BindableViewHolder inflating a layout, it should work`() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val parent = LinearLayout(context)
        val layoutId = 0

        val mockedLayout = mockk<LinearLayout>()

        mockkStatic(LayoutInflater::class)
        every { LayoutInflater.from(context) } returns mockk {
            every { inflate(layoutId, parent, false) } returns mockedLayout
        }

        val bindableViewHolder = object : BindableViewHolder<Supertype>(parent, layoutId) {
            override fun bind(item: Supertype) {
            }
        }

        Assert.assertEquals(mockedLayout, bindableViewHolder.itemView)

        unmockkStatic(LayoutInflater::class)
    }
}

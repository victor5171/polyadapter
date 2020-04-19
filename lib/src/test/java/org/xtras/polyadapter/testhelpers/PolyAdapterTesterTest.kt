package org.xtras.polyadapter.testhelpers

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.xtras.polyadapter.PolyAdapterBuilder
import org.xtras.polyadapter.ViewHolderDelegate
import org.xtras.polyadapter.ViewTypeRetriever

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class PolyAdapterTesterTest {

    private interface Supertype
    private object Children1 : Supertype
    private object Children2 : Supertype

    private val viewTypeRetriever = object : ViewTypeRetriever<Supertype> {
        override fun getViewType(value: Supertype): Int {
            return when (value) {
                is Children1 -> 1
                is Children2 -> 2
                else -> throw UnsupportedOperationException()
            }
        }
    }

    private val allItemsGenerator: AllItemsGenerator<Supertype> = {
        listOf(Children1, Children2)
    }

    @Test(expected = Exception::class)
    fun `When I try to call testAll with not all delegates registered, it should fail`() {
        val polyAdapterBuilder = PolyAdapterBuilder(viewTypeRetriever)
            .registerDelegate(1, mockk<ViewHolderDelegate<Children1, RecyclerView.ViewHolder>>())

        val polyAdapterTester = PolyAdapterTester(polyAdapterBuilder, allItemsGenerator)

        val context = ApplicationProvider.getApplicationContext<Context>()

        polyAdapterTester.testAll(context, Assert::assertEquals)
    }

    @Test
    fun `When I try to call testAll with all delegates registered, it should pass`() {
        val polyAdapterBuilder = PolyAdapterBuilder(viewTypeRetriever)
            .registerDelegate(
                1,
                mockk<ViewHolderDelegate<Children1, RecyclerView.ViewHolder>>(relaxed = true)
            )
            .registerDelegate(
                2,
                mockk<ViewHolderDelegate<Children2, RecyclerView.ViewHolder>>(relaxed = true)
            )

        val polyAdapterTester = PolyAdapterTester(polyAdapterBuilder, allItemsGenerator)

        val context = ApplicationProvider.getApplicationContext<Context>()

        polyAdapterTester.testAll(context, Assert::assertEquals)
    }

    @Test(expected = Exception::class)
    fun `When I try to call testAll with all delegates, but one with a wrong supertype, it should fail`() {

        class Children1ViewHolderDelegate : ViewHolderDelegate<Children1, RecyclerView.ViewHolder> {
            override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
                return mockk()
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Children1) {}
        }

        val polyAdapterBuilder = PolyAdapterBuilder(viewTypeRetriever)
            .registerDelegate(
                1,
                Children1ViewHolderDelegate()
            )
            .registerDelegate(
                2,
                Children1ViewHolderDelegate() // Wrong ViewHolderDelegate here
            )

        val polyAdapterTester = PolyAdapterTester(polyAdapterBuilder, allItemsGenerator)

        val context = ApplicationProvider.getApplicationContext<Context>()

        polyAdapterTester.testAll(context, Assert::assertEquals)
    }
}

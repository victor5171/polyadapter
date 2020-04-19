package org.xtras.polyadapter

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import io.mockk.mockkClass
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.xtras.polyadapter.app.adapterbuilders.BodyAdapterBuilder
import org.xtras.polyadapter.testhelpers.PolyAdapterTester
import org.xtras.polyadapter.testhelpers.SealedClassItemGenerator
import org.xtras.polyadapter.testhelpers.create

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class BodyAdapterBuilderTest {
    @Test
    fun `When I create a PolyAdapter for the body items, all delegates should be registered and working`() {
        val polyAdapterTester = PolyAdapterTester(
            BodyAdapterBuilder.createPolyAdapterBuilder(),
            SealedClassItemGenerator.create { mockkClass(it, relaxed = true) }
        )

        val context = ApplicationProvider.getApplicationContext<Context>()

        polyAdapterTester.testAll(context) { viewTypeFromViewTypeRetriever, viewTypeFromAdapter ->
            Assert.assertEquals(viewTypeFromViewTypeRetriever, viewTypeFromAdapter)
        }
    }
}

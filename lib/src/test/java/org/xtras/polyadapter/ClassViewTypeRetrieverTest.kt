package org.xtras.polyadapter

import androidx.recyclerview.widget.RecyclerView
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

sealed class SealedFirstLevel

object SecondLevelChild : SealedFirstLevel()

sealed class SealedSecondLevelChild : SealedFirstLevel()
abstract class AbstractSecondLevelChild : SealedFirstLevel()
object ThirdLevelChild1 : SealedSecondLevelChild()
object ThirdLevelChild2 : SealedSecondLevelChild()

class ClassViewTypeRetrieverTest {
    @Test
    fun `When I try to generate a ViewType for all instances, it should work`() {
        val classViewTypeRetriever = ClassViewTypeRetriever<SealedFirstLevel>()

        Assert.assertEquals(
            SecondLevelChild::class.hashCode(),
            classViewTypeRetriever.getViewType(SecondLevelChild)
        )

        Assert.assertEquals(
            ThirdLevelChild1::class.hashCode(),
            classViewTypeRetriever.getViewType(ThirdLevelChild1)
        )

        Assert.assertEquals(
            ThirdLevelChild2::class.hashCode(),
            classViewTypeRetriever.getViewType(ThirdLevelChild2)
        )
    }

    @Test
    fun `When I try to get all the children, it should give me all the children downwards, excluding sealed and abstract classes`() {
        val childrenFromFirstLevel = childrenRecursively(SealedFirstLevel::class).toList()
        Assert.assertEquals(3, childrenFromFirstLevel.size)
        Assert.assertTrue(childrenFromFirstLevel.toTypedArray().contentDeepEquals(arrayOf(
            SecondLevelChild::class, ThirdLevelChild1::class, ThirdLevelChild2::class
        )))

        val childrenFromSecondLevel = childrenRecursively(SealedSecondLevelChild::class).toList()
        Assert.assertEquals(2, childrenFromSecondLevel.size)
        Assert.assertTrue(childrenFromSecondLevel.toTypedArray().contentDeepEquals(arrayOf(
            ThirdLevelChild1::class, ThirdLevelChild2::class
        )))

        val childrenFromThirdLevel = childrenRecursively(ThirdLevelChild1::class).toList()
        Assert.assertEquals(0, childrenFromThirdLevel.size)
    }

    @Test
    fun `When I try to register a delete only for the topmost parent, it should use the delegate for its children`() {
        val classViewTypeRetriever = ClassViewTypeRetriever<SealedFirstLevel>()

        val mockedViewHolder = mockk<RecyclerView.ViewHolder>()

        val polyAdapter = PolyAdapterBuilder(classViewTypeRetriever)
            .registerDelegate(delegate = mockk<ViewHolderDelegate<SealedFirstLevel, RecyclerView.ViewHolder>> {
                every { onCreateViewHolder(any()) } returns mockedViewHolder
            })
            .buildOnlyAdapter { throw UnsupportedOperationException("Unused") }

        Assert.assertEquals(mockedViewHolder, polyAdapter.onCreateViewHolder(mockk(), SecondLevelChild::class.hashCode()))
        Assert.assertEquals(mockedViewHolder, polyAdapter.onCreateViewHolder(mockk(), ThirdLevelChild1::class.hashCode()))
        Assert.assertEquals(mockedViewHolder, polyAdapter.onCreateViewHolder(mockk(), ThirdLevelChild2::class.hashCode()))
    }

    @Test
    fun `When I try to register a delete for every children, starting from the topmost level to the bottom most, replacing, it should use the correct delegates`() {
        val classViewTypeRetriever = ClassViewTypeRetriever<SealedFirstLevel>()

        val polyAdapter = PolyAdapterBuilder(classViewTypeRetriever)
            .registerDelegate(delegate = mockk<ViewHolderDelegate<SealedFirstLevel, RecyclerView.ViewHolder>>())
            .registerDelegate(delegate = mockk<ViewHolderDelegate<SecondLevelChild, RecyclerView.ViewHolder>>())
            .registerDelegate(delegate = mockk<ViewHolderDelegate<SealedSecondLevelChild, RecyclerView.ViewHolder>>())
            .registerDelegate(delegate = mockk<ViewHolderDelegate<ThirdLevelChild1, RecyclerView.ViewHolder>>())
            .registerDelegate(delegate = mockk<ViewHolderDelegate<ThirdLevelChild2, RecyclerView.ViewHolder>>())
            .buildOnlyAdapter {
                when (it) {
                    0 -> SecondLevelChild
                    1 -> ThirdLevelChild1
                    2 -> ThirdLevelChild2
                    else -> throw UnsupportedOperationException()
                }
            }

        Assert.assertEquals(SecondLevelChild::class.hashCode(), polyAdapter.getItemViewType(0))
        Assert.assertEquals(ThirdLevelChild1::class.hashCode(), polyAdapter.getItemViewType(1))
        Assert.assertEquals(ThirdLevelChild2::class.hashCode(), polyAdapter.getItemViewType(2))
    }
}

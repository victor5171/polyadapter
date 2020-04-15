package org.xtras.polyadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.junit.Assert
import org.junit.Test

enum class TestEnum {
    Value1,
    Value2
}

interface ParentTestEnumOwner {
    val testEnum: TestEnum
}

object Child1EnumOwner : ParentTestEnumOwner {
    override val testEnum = TestEnum.Value1
}

class Child1EnumOwnerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent)

class Child1EnumOwnerDelegate : ViewHolderDelegate<Child1EnumOwner, Child1EnumOwnerViewHolder> {
    override fun onCreateViewHolder(parent: ViewGroup) = Child1EnumOwnerViewHolder(parent)

    override fun onBindViewHolder(holder: Child1EnumOwnerViewHolder, item: Child1EnumOwner) {}
}

object Child2EnumOwner : ParentTestEnumOwner {
    override val testEnum = TestEnum.Value2
}

class EnumViewTypeRetrieverTest {
    @Test
    fun `When I try to generate a ViewType for all values in the enum, it should work`() {
        val enumViewTypeRetriever =
            EnumViewTypeRetriever<ParentTestEnumOwner, TestEnum> { testEnum }
        Assert.assertEquals(
            TestEnum.Value1.ordinal,
            enumViewTypeRetriever.getViewType(Child1EnumOwner)
        )
        Assert.assertEquals(
            TestEnum.Value2.ordinal,
            enumViewTypeRetriever.getViewType(Child2EnumOwner)
        )
    }

    @Test
    fun `When I try to register a delegate for Value1, using the extension, it should be registered correctly`() {
        val enumViewTypeRetriever =
            EnumViewTypeRetriever<ParentTestEnumOwner, TestEnum> { testEnum }

        val polyAdapter = PolyAdapterBuilder(enumViewTypeRetriever)
            .registerDelegate(TestEnum.Value1, Child1EnumOwnerDelegate())
            .buildOnlyAdapter { Child1EnumOwner }

        Assert.assertEquals(TestEnum.Value1.ordinal, polyAdapter.getItemViewType(0))
    }

    @Test
    fun `When I have a non abstract type, but use different viewtypes for different values of the enum, it should give different view types`() {
        data class ConcreteType(val enum: TestEnum)

        class ConcreteTypeViewHolderForValue1(parent: ViewGroup) : RecyclerView.ViewHolder(parent)
        class ConcreteTypeViewHolderForValue2(parent: ViewGroup) : RecyclerView.ViewHolder(parent)
        class ConcreteTypeDelegateForValue1 :
            ViewHolderDelegate<ConcreteType, ConcreteTypeViewHolderForValue1> {
            override fun onCreateViewHolder(parent: ViewGroup) =
                ConcreteTypeViewHolderForValue1(parent)

            override fun onBindViewHolder(
                holder: ConcreteTypeViewHolderForValue1,
                item: ConcreteType
            ) {
            }
        }

        class ConcreteTypeDelegateForValue2 :
            ViewHolderDelegate<ConcreteType, ConcreteTypeViewHolderForValue2> {
            override fun onCreateViewHolder(parent: ViewGroup) =
                ConcreteTypeViewHolderForValue2(parent)

            override fun onBindViewHolder(
                holder: ConcreteTypeViewHolderForValue2,
                item: ConcreteType
            ) {
            }
        }

        val enumTypeRetriever = EnumViewTypeRetriever<ConcreteType, TestEnum> { enum }

        val polyAdapter = PolyAdapterBuilder(enumTypeRetriever)
            .registerDelegate(TestEnum.Value1, ConcreteTypeDelegateForValue1())
            .registerDelegate(TestEnum.Value2, ConcreteTypeDelegateForValue2())
            .buildOnlyAdapter {
                when (it) {
                    0 -> ConcreteType(TestEnum.Value1)
                    1 -> ConcreteType(TestEnum.Value2)
                    else -> throw UnsupportedOperationException()
                }
            }

        Assert.assertEquals(TestEnum.Value1.ordinal, polyAdapter.getItemViewType(0))
        Assert.assertEquals(TestEnum.Value2.ordinal, polyAdapter.getItemViewType(1))
    }
}

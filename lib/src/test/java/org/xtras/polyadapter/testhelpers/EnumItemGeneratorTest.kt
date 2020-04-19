package org.xtras.polyadapter.testhelpers

import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class EnumItemGeneratorTest {
    private enum class TestEnum {
        Value1,
        Value2
    }

    private interface EnumContainer {
        val enumValue: TestEnum
    }

    private class Value1Container : EnumContainer {
        override val enumValue = TestEnum.Value1
    }

    private class Value2Container : EnumContainer {
        override val enumValue = TestEnum.Value2
    }

    @Test
    fun `When I try to use EnumItemGenerator for EnumContainer and TestEnum, it should give me all the values and containers from them`() {
        val enumItemGenerator = EnumItemGenerator.create<EnumContainer, TestEnum> {
            when (it) {
                TestEnum.Value1 -> mockk<Value1Container>(relaxed = true)
                TestEnum.Value2 -> mockk<Value2Container>(relaxed = true)
            }
        }

        val items = enumItemGenerator().toList()

        Assert.assertEquals(2, items.size)
        Assert.assertEquals(1, items.count { it is Value1Container })
        Assert.assertEquals(1, items.count { it is Value2Container })
    }
}

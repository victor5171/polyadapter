package org.xtras.polyadapter.testhelpers

import androidx.annotation.VisibleForTesting

/**
 * An implementation of [AllItemsGenerator], which generate all the items needed to cover all the values from [TEnum]
 * @param TItem The supertype of the adapter
 * @param TEnum The type of the enum which is used to generate view types
 * @param enumValues An array containing all the values of [TEnum]
 * @param itemGenerator A function that shall return an instance of [TItem] which has a enum with an specific value inside it
 */
@VisibleForTesting
class EnumItemGenerator<TItem, TEnum : Enum<TEnum>>(
    private val enumValues: Array<TEnum>,
    private val itemGenerator: (enumValue: TEnum) -> TItem
) : AllItemsGenerator<TItem> {
    companion object;

    override fun invoke(): Iterable<TItem> {
        return enumValues.map(itemGenerator::invoke)
    }
}

/**
 * Use this method to instantiate [EnumItemGenerator] because it's more clean to use
 * @param TItem The supertype of the adapter
 * @param TEnum The type of the enum which is used to generate view types
 * @param itemGenerator A function that shall return an instance of [TItem] which has a enum with an specific value inside it
 * @return An instance of [EnumItemGenerator]
 */
inline fun <TItem, reified TEnum : Enum<TEnum>> EnumItemGenerator.Companion.create(
    noinline itemGenerator: (enumValue: TEnum) -> TItem
): EnumItemGenerator<TItem, TEnum> {
    val enumValues = enumValues<TEnum>()
    return EnumItemGenerator(enumValues, itemGenerator)
}

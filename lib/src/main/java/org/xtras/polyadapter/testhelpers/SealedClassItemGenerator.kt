package org.xtras.polyadapter.testhelpers

import androidx.annotation.VisibleForTesting
import kotlin.reflect.KClass
import org.xtras.polyadapter.childrenRecursively

/**
 * An implementation of [AllItemsGenerator], meant to be used if [TItem] is a sealed class
 * This implementation generate instances for every non-abstract children of [TItem]
 * @param TItem The supertype of your adapter
 * @param clazz The KClass instance of the supertype of your adapter
 * @param mocker A function which gives an instance of [TItem] for every [KClass], you can plug your favorite mock framework here
 */
@VisibleForTesting
class SealedClassItemGenerator<TItem : Any>(
    private val clazz: KClass<TItem>,
    private val mocker: (KClass<out TItem>) -> TItem
) : AllItemsGenerator<TItem> {

    companion object;

    override fun invoke(): Iterable<TItem> {
        return childrenRecursively(clazz)
            .map { mocker(it) }
            .toList()
    }
}

/**
 * Use this method to instantiate [SealedClassItemGenerator] because it's more clean to use
 * @param TItem The supertype of your adapter
 * @param mocker A function which gives an instance of [TItem] for every [KClass], you can plug your favorite mock framework here
 */
inline fun <reified TItem : Any> SealedClassItemGenerator.Companion.create(
    noinline mocker: (KClass<out TItem>) -> TItem
) = SealedClassItemGenerator(TItem::class, mocker)

package org.xtras.polyadapter.paging

/**
 * Represents a type that can be null
 * Used for supporting placeholders for the paging library
 * @param TItem The wrapped type
 */
sealed class NullSafeItem<out TItem>

/**
 * Represents a item with a value
 * @param TItem The wrapped type
 * @param item the inner value
 */
data class RealItem<out TItem>(val item: TItem) : NullSafeItem<TItem>()

/**
 * Represents a null item
 * @param TItem The wrapped type
 */
class NullItem<out TItem> : NullSafeItem<TItem>()

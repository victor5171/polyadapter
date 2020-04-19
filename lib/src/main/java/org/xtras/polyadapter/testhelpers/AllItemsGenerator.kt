package org.xtras.polyadapter.testhelpers

/**
 * Represents a lambda that generates all the possible variations of TItem
 * @param TItem The supertype of the adapter
 */
typealias AllItemsGenerator<TItem> = () -> Iterable<TItem>

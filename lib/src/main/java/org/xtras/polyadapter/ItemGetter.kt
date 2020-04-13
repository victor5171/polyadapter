package org.xtras.polyadapter

/**
 * Represents a function that gets an item at the position
 */
typealias ItemGetter<TItem> = (position: Int) -> TItem

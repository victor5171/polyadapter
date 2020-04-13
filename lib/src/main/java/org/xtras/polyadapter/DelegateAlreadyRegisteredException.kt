@file:Suppress("MemberVisibilityCanBePrivate")

package org.xtras.polyadapter

import kotlin.reflect.KClass

class DelegateAlreadyRegisteredException(
    viewType: Int,
    delegateClass: KClass<*>
) : RuntimeException() {
    override val message = "There's already a subAdapter registered for $viewType! : ${delegateClass.qualifiedName}"
}

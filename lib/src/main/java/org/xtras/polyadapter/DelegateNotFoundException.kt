package org.xtras.polyadapter

/**
 * Exception thrown every time a Delegate wasn't able to be found
 */
class DelegateNotFoundException private constructor(override val message: String) :
    RuntimeException() {

    companion object {
        /**
         * Builds a generic message, where it wasn't possible to get the item that was going to be drawn
         */
        fun buildWithGenericMessage(
            viewType: Int,
            delegates: DelegatesMap<*>
        ) =
            DelegateNotFoundException("Delegate not found for $viewType! All delegates: $delegates")

        /**
         * Builds a detailed message showing the item that was going to be drawn, making the fix easier
         */
        fun buildWithDetailedMessage(
            value: Any,
            viewType: Int,
            delegates: DelegatesMap<*>
        ) =
            DelegateNotFoundException(("Delegate not found for the value: $value, it's viewType was $viewType, all delegates: $delegates"))
    }
}

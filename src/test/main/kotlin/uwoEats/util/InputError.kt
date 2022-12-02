package uwoEats.util

class InputError(val errors: Map<String, List<String>>) : Throwable() {
    companion object {
        fun build(field: String, error: String) = InputError(mapOf(field to listOf(error)))

        fun build(vararg pairs: Pair<String, List<String>>) = InputError(mapOf(*pairs))
    }
}

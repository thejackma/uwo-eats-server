package uwoEats.db

import uwoEats.model.Store
import uwoEats.util.InputError
import uwoEats.util.key

fun getStore(id: Long): Store {
    return Table.store.getItem(key(id))
        ?: throw InputError.build("email", "not found")
}

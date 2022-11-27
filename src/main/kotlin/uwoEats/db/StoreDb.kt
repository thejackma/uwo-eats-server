package uwoEats.db

import uwoEats.model.Store
import uwoEats.util.InputError
import uwoEats.util.key

fun getStore(storeId: Long): Store {
    return Table.store.getItem(key(storeId))
        ?: throw InputError.build("storeId", "not found")
}

package templatePJ.database

import org.jetbrains.exposed.dao.IntIdTable

open class incrementalIdWithFriendlyIdTable(
        name: String = "",
        columnName: String = "id",
        friendlyIdColumnName: String = "fid", // friendly_id
        friendlyIdIndexName: String
) : IntIdTable(name, columnName) {
    val friendlyId = varchar(friendlyIdColumnName, 20).uniqueIndex(friendlyIdIndexName)
}

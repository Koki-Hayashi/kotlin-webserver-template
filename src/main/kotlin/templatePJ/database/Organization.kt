package templatePJ.database

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import templatePJ.exception.response.ResourceNotFoundException
import templatePJ.model.Organization

object Organizations : incrementalIdWithFriendlyIdTable(
        name = "organizations",
        friendlyIdIndexName = "idx_organizations_friendly_id"
) {
    val name = varchar("name", 30)
}

fun IntEntityClass<OrganizationDAO>.findByFriendlyId(friendlyId: String): OrganizationDAO {
    OrganizationDAO.find { Organizations.friendlyId eq friendlyId }.also {
        if (it.empty()) {
            throw ResourceNotFoundException("organization with friendly_id $friendlyId not found")
        }

        return it.first()
    }
}

class OrganizationDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<OrganizationDAO>(Organizations)

    var friendlyId by Organizations.friendlyId
    var name by Organizations.name

    fun toOrganization(): Organization {
        return Organization(
                id = this.friendlyId,
                name = this.name
        )
    }
}


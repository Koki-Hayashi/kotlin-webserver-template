package templatePJ.database

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import templatePJ.exception.response.ResourceNotFoundException
import templatePJ.model.User

object Users : incrementalIdWithFriendlyIdTable(
        name = "users",
        friendlyIdIndexName = "idx_users_friendly_id"
) {
    val organization = reference("organization_id", Organizations)
    val name = varchar("name", 30)
    val email = varchar("email", 128)
}

fun IntEntityClass<UserDAO>.findByFriendlyId(friendlyId: String): UserDAO {
    UserDAO.find { Users.friendlyId eq friendlyId }.also {
        if (it.empty()) {
            throw ResourceNotFoundException("user with friendly_id $friendlyId not found")
        }

        return it.first()
    }
}

fun IntEntityClass<UserDAO>.existsByEmail(email: String): Boolean {
    UserDAO.find { Users.email eq email }.also {
        return !it.empty()
    }
}


class UserDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDAO>(Users)

    var friendlyId by Users.friendlyId
    var organization by OrganizationDAO referencedOn Users.organization
    var name by Users.name
    var email by Users.email

    fun toUser(): User {
        return User(
                id = this.friendlyId,
                name = this.name,
                organization = this.organization.toOrganization(),
                email = this.email
        )
    }
}

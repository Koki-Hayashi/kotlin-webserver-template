package templatePJ.service

import templatePJ.database.OrganizationDAO
import templatePJ.database.UserDAO
import templatePJ.database.findByFriendlyId
import templatePJ.model.User
import templatePJ.util.UUIDUtils
import javax.inject.Singleton

@Singleton
class UserService {

    fun getUser(friendlyId: String): User {
        return UserDAO.findByFriendlyId(friendlyId).toUser()
    }

    fun createUser(name: String, email: String, orgId: String) {
        val orgDAO = OrganizationDAO.findByFriendlyId(orgId)

        UserDAO.new {
            this.friendlyId = UUIDUtils.generateFriendlyId()
            this.name = name
            this.email = email
            this.organization = orgDAO
        }
    }
}

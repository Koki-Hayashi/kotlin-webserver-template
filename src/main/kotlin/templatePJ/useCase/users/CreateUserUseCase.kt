package templatePJ.useCase.users

import org.jetbrains.exposed.sql.transactions.transaction
import templatePJ.database.UserDAO
import templatePJ.database.existsByEmail
import templatePJ.exception.response.IllegalStateException
import templatePJ.service.UserService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateUserUseCase {

    @Inject
    private lateinit var userService: UserService

    fun run(name: String, email: String, orgId: String) {
        transaction {
            if (UserDAO.existsByEmail(email)) {
                throw IllegalStateException("User with email $email is already existing")
            }

            userService.createUser(
                    name = name,
                    orgId = orgId,
                    email = email
            )
        }
    }
}



package templatePJ.useCase.users

import org.jetbrains.exposed.sql.transactions.transaction
import templatePJ.model.User
import templatePJ.service.UserService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserUseCase {

    @Inject
    private lateinit var userService: UserService

    fun run(userId: String): User {
        return transaction { userService.getUser(userId) };
    }

}

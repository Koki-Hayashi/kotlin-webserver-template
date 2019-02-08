package templatePJ.useCase.users

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.jupiter.api.assertThrows
import org.mockito.internal.util.reflection.Whitebox
import templatePJ.database.UserDAO
import templatePJ.database.existsByEmail
import templatePJ.exception.response.IllegalStateException
import templatePJ.service.UserService
import templatePJ.testUtil.DatabaseFactory


object CreateUserUseCaseSpek : Spek({

    lateinit var createUserUseCase: CreateUserUseCase

    beforeGroup {
        DatabaseFactory.init()
    }

    beforeEachTest {
        transaction {
            DatabaseFactory.resetDatabase()
        }

        createUserUseCase = CreateUserUseCase()
    }

    describe("run") {

        context("email is already taken") {
            beforeEachTest {
                mockkStatic("templatePJ.database.UserKt")
                every { UserDAO.existsByEmail(any()) } returns true
            }

            it("return true") {
                assertThrows<IllegalStateException> {
                    transaction {
                        createUserUseCase.run(
                                name = "name",
                                orgId = "orgId",
                                email = "email"
                        )
                    }
                }
            }
        }

        context("email is not taken") {
            val name = "name"
            val orgId = "orgId"
            val email = "email"
            lateinit var mockUserService: UserService

            beforeEachTest {
                mockkStatic("templatePJ.database.UserKt")
                every { UserDAO.existsByEmail(any()) } returns false

                mockUserService = mockk()
                every { mockUserService.createUser(any(), any(), any()) } returns Unit
                Whitebox.setInternalState(
                        createUserUseCase,
                        "userService",
                        mockUserService
                )
            }

            it("processes") {
                createUserUseCase.run(
                        name = name,
                        orgId = orgId,
                        email = email
                )

                verify {
                    mockUserService.createUser(
                            name = name,
                            orgId = orgId,
                            email = email
                    )
                }
            }
        }
    }
})

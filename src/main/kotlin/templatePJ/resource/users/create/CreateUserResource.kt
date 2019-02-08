package templatePJ.resource.users.create

import mu.KotlinLogging
import templatePJ.dto.users.CreateUserInDto
import templatePJ.exception.response.MyResponseException
import templatePJ.exception.response.ValidationException
import templatePJ.useCase.users.CreateUserUseCase
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/users")
class CreateUserResource {

    private val logger = KotlinLogging.logger {}

    @Inject
    lateinit var createUserUseCase: CreateUserUseCase

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun createToken(inDto: CreateUserInDto): Response {
        val errorMsgs = validate(inDto);
        if (errorMsgs.isNotEmpty()) {
            throw ValidationException(errorMsgs.joinToString(separator = ", "))
        }
        val user = inDto.user
        try {
            createUserUseCase.run(
                    name = user.name,
                    orgId = user.orgId,
                    email = user.email
            )
            return Response.status(Response.Status.CREATED).build()

        } catch (e: MyResponseException) {
            logger.error { e.printStackTrace() }
            throw e
        } catch (e: Exception) {
            logger.error { e.printStackTrace() }
            throw InternalServerErrorException(e.localizedMessage)
        }
    }


}

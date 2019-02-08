package templatePJ.resource.users.get

import mu.KotlinLogging
import templatePJ.dto.users.GetUserOutDto
import templatePJ.dto.users.from
import templatePJ.exception.response.MyResponseException
import templatePJ.exception.response.ValidationException
import templatePJ.useCase.users.GetUserUseCase
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/users")
class GetUserResource {

    private val logger = KotlinLogging.logger {}

    @Inject
    lateinit var getUserUseCase: GetUserUseCase

    @Path("/{user_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getToken(
            @PathParam("user_id") userFid: String
    ): GetUserOutDto {
        val errorMsg = validate(userFid)
        if (errorMsg.isNotEmpty()) {
            throw ValidationException(errorMsg)
        }

        try {
            return GetUserOutDto(from(getUserUseCase.run(userFid)))
        } catch (e: MyResponseException) {
            logger.error { e.printStackTrace() }
            throw e
        } catch (e: Exception) {
            logger.error { e.printStackTrace() }
            throw InternalServerErrorException(e.localizedMessage)
        }
    }
}

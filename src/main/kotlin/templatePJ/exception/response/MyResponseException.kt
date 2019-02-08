package templatePJ.exception.response

import templatePJ.dto.ErrorDto
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response

open class MyResponseException(status: Response.Status = Response.Status.INTERNAL_SERVER_ERROR, errorDto: ErrorDto) :
        WebApplicationException(
                Response
                        .status(status)
                        .entity(errorDto)
                        .build()
        )


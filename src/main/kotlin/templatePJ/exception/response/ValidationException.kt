package templatePJ.exception.response

import templatePJ.dto.ErrorDto
import javax.ws.rs.core.Response

class ValidationException(message: String) :
    MyResponseException(
        Response.Status.BAD_REQUEST,
        ErrorDto(ValidationException::class.java.simpleName, message)
    )

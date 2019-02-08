package templatePJ.exception.response

import templatePJ.dto.ErrorDto
import javax.ws.rs.core.Response

class IllegalStateException(message: String) :
        MyResponseException(Response.Status.BAD_REQUEST,
                ErrorDto(IllegalStateException::class.java.simpleName, message))

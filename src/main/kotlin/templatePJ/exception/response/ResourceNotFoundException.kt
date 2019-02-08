package templatePJ.exception.response

import templatePJ.dto.ErrorDto
import javax.ws.rs.core.Response

class ResourceNotFoundException(message: String) :
        MyResponseException(Response.Status.NOT_FOUND,
                ErrorDto(ResourceNotFoundException::class.java.simpleName, message))

package templatePJ.provider

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.ext.Provider

@Provider
@Produces(MediaType.APPLICATION_JSON)
class CustomJsonProvider : JacksonJaxbJsonProvider() {

    init {
        setMapper(mapper)
    }

    companion object {
        private val mapper = ObjectMapper().registerKotlinModule().registerModule(JavaTimeModule())
    }
}

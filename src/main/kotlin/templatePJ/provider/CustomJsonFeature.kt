package templatePJ.provider

import javax.ws.rs.core.Feature
import javax.ws.rs.core.FeatureContext
import javax.ws.rs.ext.MessageBodyReader
import javax.ws.rs.ext.MessageBodyWriter

class CustomJsonFeature : Feature {

    override fun configure(context: FeatureContext): Boolean {
        context.register(CustomJsonProvider::class.java, MessageBodyReader::class.java, MessageBodyWriter::class.java)
        return true
    }
}

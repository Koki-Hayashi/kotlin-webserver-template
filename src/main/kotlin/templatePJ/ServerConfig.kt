package templatePJ

import com.google.inject.Guice
import com.google.inject.servlet.ServletModule
import mu.KotlinLogging
import org.glassfish.hk2.api.ServiceLocator
import org.glassfish.jersey.server.ResourceConfig
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jvnet.hk2.guice.bridge.api.GuiceBridge
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge
import templatePJ.database.*
import templatePJ.provider.CustomJsonFeature
import javax.ws.rs.core.Context

class ServerConfig(@Context serviceLocator: ServiceLocator) : ResourceConfig() {

    private val logger = KotlinLogging.logger {}

    init {
        logger.info { "Launching server..." }
        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator)
        register(CustomJsonFeature::class.java)

        serviceLocator.getService(GuiceIntoHK2Bridge::class.java).bridgeGuiceInjector(Guice.createInjector(object : ServletModule() {}))


        DatabaseFactory.init()

/*        transaction {
            SchemaUtils.create(
                    Organizations,
                    Users
            )

            val org = OrganizationDAO.new {
                friendlyId = "sample_org"
                name = "Sample Organization"
            }

            UserDAO.new {
                friendlyId = "sample_user"
                name = "sample_name"
                email = "email@sample.com"
                organization = org
            }

        }*/
        logger.info { "launched" }
    }
}

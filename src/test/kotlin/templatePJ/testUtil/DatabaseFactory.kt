package templatePJ.testUtil

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.newFixedThreadPoolContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import templatePJ.database.Organizations
import templatePJ.database.Users
import kotlin.coroutines.CoroutineContext

object DatabaseFactory {

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "com.mysql.cj.jdbc.Driver"
        config.jdbcUrl = "jdbc:mysql://localhost/test"
        config.username = "root"
        config.password = "password"
        config.maximumPoolSize = 5
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    fun init() {
        Database.connect(hikari())

        transaction {
            SchemaUtils.create(
                    Organizations,
                    Users
            )
        }
    }

    private val dispatcher: CoroutineContext

    init {
        dispatcher = newFixedThreadPoolContext(5, "database-thread-pool")
    }

    fun resetDatabase() {
        transaction {
            Users.deleteAll()
            Organizations.deleteAll()
        }
    }
}

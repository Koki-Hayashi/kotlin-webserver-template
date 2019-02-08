package templatePJ.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import templatePJ.util.getDbHost
import templatePJ.util.getDbPassword
import templatePJ.util.getDbUser
import templatePJ.util.getJdbcUrl
import kotlin.coroutines.CoroutineContext

object DatabaseFactory {

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "com.mysql.cj.jdbc.Driver"
        config.jdbcUrl = getJdbcUrl()
        config.username = getDbUser()
        config.password = getDbPassword()
        config.maximumPoolSize = 5
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    fun init() {
        Database.connect(hikari())
    }
}

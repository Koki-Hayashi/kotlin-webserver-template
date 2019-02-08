package templatePJ.database

import templatePJ.testUtil.DatabaseFactory
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.jupiter.api.assertThrows
import org.assertj.core.api.Assertions.*
import templatePJ.exception.response.ResourceNotFoundException


object OrganizationSpek : Spek({

    beforeGroup {
        DatabaseFactory.init()
    }

    beforeEachTest {
        transaction {
            DatabaseFactory.resetDatabase()
        }
    }

    describe("findByNameFriendlyId") {

        context("there's no organization with the friendly id") {
            beforeEachTest {
                transaction {
                    OrganizationDAO.new {
                        this.friendlyId = "friendly_id"
                        this.name = "org_name"
                    }
                }
            }

            it("throws") {
                assertThrows<ResourceNotFoundException> {
                    transaction {
                        OrganizationDAO.findByFriendlyId("different_fid")
                    }
                }
            }
        }

        context("there's an organization with the friendly id") {

            val friendlyId = "friendly_id"

            beforeEachTest {
                transaction {
                    OrganizationDAO.new {
                        this.friendlyId = friendlyId
                        this.name = "org_name"
                    }
                }
            }

            it("returns") {
                transaction {
                    OrganizationDAO.findByFriendlyId(friendlyId).also {
                        assertThat(it.friendlyId).isEqualTo(friendlyId)
                    }
                }
            }
        }
    }
})

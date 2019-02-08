package templatePJ.model

data class User(
        val id: String,
        val name: String,
        val organization: Organization,
        val email: String
)

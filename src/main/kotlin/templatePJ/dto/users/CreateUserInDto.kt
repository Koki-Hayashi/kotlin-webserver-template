package templatePJ.dto.users

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateUserInDto(
        @JsonProperty("user")
        val user: UserInfo
) {
    data class UserInfo(
            @JsonProperty("organization_id")
            val orgId: String,
            @JsonProperty("email")
            val email: String,
            @JsonProperty("name")
            val name: String
    )
}


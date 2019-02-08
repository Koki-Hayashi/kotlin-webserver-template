package templatePJ.dto.users

import com.fasterxml.jackson.annotation.JsonProperty
import templatePJ.model.User

data class GetUserOutDto(
        @JsonProperty
        val user: UserInfo
) {
    data class UserInfo(
            val id: String,
            @JsonProperty("organization_id")
            val organizationId: String,
            val email: String,
            val name: String
    )
}


fun from(user: User): GetUserOutDto.UserInfo {
    return with(user) {
        GetUserOutDto.UserInfo(
                id = this.id,
                organizationId = this.organization.id,
                name = this.name,
                email = this.email
        )
    }
}


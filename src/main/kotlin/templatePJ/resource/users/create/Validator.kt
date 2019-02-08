package templatePJ.resource.users.create

import templatePJ.dto.users.CreateUserInDto
import templatePJ.resource.users.validateEmail
import templatePJ.resource.users.validateRequiredString



fun validate(inDto: CreateUserInDto): List<String> {
    val user = inDto.user
    return listOf(
            validateRequiredString(user.name, "name"),
            validateRequiredString(user.orgId, "organization_id"),
            validateEmail(user.email)
    ).map { it }
            .filter { it.isNotEmpty() }
            .toList()
}



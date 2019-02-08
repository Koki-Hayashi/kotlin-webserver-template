package templatePJ.resource.users.get

import templatePJ.resource.users.validateRequiredString

fun validate(userId: String): String {
    return validateRequiredString(userId, "user_id")
}

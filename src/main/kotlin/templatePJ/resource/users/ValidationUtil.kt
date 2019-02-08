package templatePJ.resource.users

fun validateRequiredString(str: String, name: String): String {
    return if (str.isEmpty()) return "$name is missing" else ""
}

// easy one
fun validateEmail(email: String): String {
    if (email.isEmpty()) return "email is missing"

    return if (email.contains("@")) return "" else "$email is invalid format"
}

package com.example.se114.exception

sealed class CustomException(message: String): Exception() {
    object EmptyBlankException: CustomException("Please fill all the blanks")
    object MemberAlreadyExistsException: CustomException("This member already existed")
    object UserAlreadyExistsException: CustomException("This email already existed")
    object LogInFailException: CustomException("Log in failed")
    object CreateUserFailed: CustomException("Cannot create user")
    object UpdateUserFailed: CustomException("Cannot update user")
    object RetrieveUserDataFailed: CustomException("Cannot retrieve user data")
    object RetrieveGroupDataFailed: CustomException("Cannot retrieve group data")
    object GroupNotExistedException: CustomException("Group not existed")
    object RetrieveMemberListDataFailed: CustomException("Cannot retrieve group data")
}
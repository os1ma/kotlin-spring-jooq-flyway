package com.example.demo.domain.model.user

// aggregate
data class User(val id: UserId, val name: UserName)

// value objects
data class UserId(val value: Int)

data class UserName(val value: String)

// first class collections
data class UserNames(val list: List<UserName>) {

    companion object {
        fun ofEmpty(): UserNames {
            return UserNames(emptyList())
        }
    }

}
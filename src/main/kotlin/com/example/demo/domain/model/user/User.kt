package com.example.demo.domain.model.user

// aggregate
data class User(val id: UserId, val name: UserName)

data class UserId(val value: Int)
data class UserName(val value: String)
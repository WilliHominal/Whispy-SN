package com.warh.whispy_sn.model

data class UserModel (val username: String, var urlProfileImage: String, var city: String, var country: String, var friends: List<String>, var posts: List<PostModel>)
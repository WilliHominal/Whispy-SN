package com.warh.whispy_sn.repository

import com.warh.whispy_sn.model.PostModel
import com.warh.whispy_sn.model.UserModel

class DataProvider {
    companion object {
        private var myUser: UserModel? = null

        private val users = listOf(
            UserModel(
                "MaryJafanna",
                "https://this-person-does-not-exist.com/img/avatar-c9b4e772e8ded519893f6e842c3c5115.jpg",
                "Jaffna",
                "Sri Lanka",
                listOf("ValeryH", "Papourê", "Willito"),
                listOf(
                    PostModel(0,
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean et nisl tincidunt, efficitur tortor commodo, sollicitudin leo. Proin eleifend at libero tempor ultricies. Suspendisse semper fermentum pretium. Aliquam in risus tempus, hendrerit massa in, interdum elit.",
                        "https://api.lorem.space/image/face?w=150&h=150"
                    ),
                    PostModel(1,
                        "Sed lacinia condimentum odio, quis sollicitudin sem varius eget. Quisque viverra tortor libero, vel consequat urna euismod a. Phasellus id imperdiet metus. Suspendisse sollicitudin suscipit purus.",
                        "https://api.lorem.space/image/fashion?w=150&h=150"
                    ),
                    PostModel(2,
                    "Maecenas ultricies aliquet neque et iaculis. Nullam vel consequat ipsum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Duis vestibulum eros eget eros ultricies, non eleifend massa facilisis.",
                    ""
                    )
                )
            ),

            UserModel(
                "ValeryH",
                "https://this-person-does-not-exist.com/img/avatar-e0bf1f3ad4d379814efbdbe14cd23145.jpg",
                "Valera",
                "Venezuela",
                listOf("MaryJafanna"),
                listOf(
                    PostModel(0,
                        "Pellentesque in turpis aliquet dolor dignissim facilisis non vehicula metus. Aenean lobortis bibendum justo ullamcorper sodales.",
                        ""
                    ),
                    PostModel(1,
                        "",
                        "https://api.lorem.space/image/furniture?w=150&h=150"
                    ),
                )
            ),

            UserModel(
                "Papourê",
                "https://placekitten.com/200/200",
                "Amiens",
                "Francia",
                listOf("MaryJafanna", "Willito"),
                listOf(
                    PostModel(0,
                        "Vestibulum sit amet dui et arcu fermentum vestibulum. Etiam suscipit dui diam, sit amet pellentesque metus pulvinar mattis. In aliquam orci magna, id rutrum odio vulputate non. Proin porttitor, dui id auctor vulputate, sapien arcu egestas odio, in gravida nulla nisl eu ex. Duis vitae lectus ultrices lorem aliquet varius.",
                        "https://api.lorem.space/image/pizza?w=150&h=150"
                    ),
                    PostModel(1,
                        "Nullam vel consequat ipsum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Duis vestibulum eros eget eros ultricies, non eleifend massa facilisis.",
                        "https://api.lorem.space/image/burger?w=150&h=150"
                    ),
                    PostModel(2,
                        "Quisque viverra tortor libero, vel consequat urna euismod a. Phasellus id imperdiet metus. Suspendisse sollicitudin suscipit purus. Sed tempor, sapien at cursus tincidunt, enim turpis lobortis nisl, eu suscipit dolor quam sollicitudin nulla.",
                        "https://api.lorem.space/image/drink?w=150&h=150"
                    ),
                )
            ),

            UserModel(
                "Willito",
                "https://this-person-does-not-exist.com/img/avatar-db5098ad1b699ea09ece70281b879f06.jpg",
                "Esperanza",
                "Argentina",
                listOf("MaryJafanna", "Papourê"),
                listOf(
                    PostModel(0,
                        "Nullam vel consequat ipsum.",
                        "https://api.lorem.space/image/house?w=150&h=150"
                    ),
                    PostModel(1,
                        "Pellentesque in turpis aliquet dolor dignissim facilisis non vehicula metus.",
                        ""
                    ),
                )
            ),
        )

        fun getMyUser(): UserModel{
            if (myUser == null) myUser = users.random()
            return myUser!!
        }

        fun isFriend(username: String): Boolean = getMyUser().friends.contains(username)

        fun getUserByUsername(username: String): UserModel = users.filter{ it.username == username }[0]

        fun getUserPosts(username: String): List<PostModel> = getUserByUsername(username).posts

        fun getMyPosts(): List<PostModel> = getMyUser().posts

        fun getFriendsPosts(): List<PostModel> {
            val resultList = mutableListOf<PostModel>()
            getMyUser().friends.forEach { friend ->
                resultList.addAll(getUserPosts(friend))
            }
            return resultList
        }

        fun getFriendsPostsMap(): Map<PostModel, String> {
            val resultMap = mutableMapOf<PostModel, String>()
            getMyUser().friends.forEach { friend ->
                getUserPosts(friend).forEach { post ->
                    resultMap[post] = friend
                }
            }
            return resultMap
        }

        fun getOtherUsers(): List<UserModel> = users.filter { it != getMyUser() }
    }
}
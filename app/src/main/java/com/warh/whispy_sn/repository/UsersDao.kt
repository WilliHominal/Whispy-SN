package com.warh.whispy_sn.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.warh.whispy_sn.model.PostModel
import com.warh.whispy_sn.model.UserModel


interface UsersDao {
    fun getUsers(callback: UsersListCallback)

    fun isFriend(oUsername: String, callback: IsFriendCallback)

    fun addFriend(oUsername: String, callback: FriendsCallback)
    fun removeFriend(oUsername: String, callback: FriendsCallback)

    fun getFriends(callback: FriendsCallback)

    fun getOwnPosts(): List<PostModel>

    fun getFriendsPosts(): List<PostModel>

    fun getMyUserInfo(callback: UserInfoCallback)

    fun getUserInfo(username: String, callback: UserInfoCallback)
    fun getUsersInfo(usernames: List<String>, callback: UsersListCallback)
}

@Suppress("UNCHECKED_CAST")
class UsersDaoImpl: UsersDao {
    companion object {
        private const val TAG = "USERS_DAO_IMPL"
        private const val USERS_PATH = "users"
        private const val USERNAMESLIST_PATH = "usernamesList"
        private const val DEFAULT_PROFILE_URL = "https://firebasestorage.googleapis.com/v0/b/whispy-d7f2b.appspot.com/o/images%2Fuser-default.png?alt=media&token=20f06fda-a67a-4b4e-bc48-590d65db81e7"
    }

    private val auth = FirebaseAuth.getInstance()
    private val database = Firebase.database.reference

    override fun getUsers(callback: UsersListCallback) {
        database.child(USERS_PATH).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val usersList = mutableListOf<UserModel>()
                    val data = snapshot.value as HashMap<String,*>
                    data.forEach {(key, _) ->
                        val countryTemp = snapshot.child(key).child("country").value as String
                        val cityTemp = snapshot.child(key).child("city").value as String
                        val urlProfileImage = snapshot.child(key).child("urlProfileImage").value as String
                        Log.d(TAG, snapshot.child(key).child("friends").value.toString())
                        val friends = snapshot.child(key).child("friends").value as HashMap<String, String>
                        val posts = snapshot.child(key).child("posts").value as? HashMap<String, PostModel>

                        val friendsAsList = ArrayList<String>(friends.values)
                        val postsAsList = posts?.let {ArrayList<PostModel>(posts.values) } ?: emptyList()

                        val userTemp = UserModel(key,
                            urlProfileImage.ifEmpty { DEFAULT_PROFILE_URL },
                            cityTemp,
                            countryTemp,
                            friendsAsList,
                            postsAsList
                        )
                        usersList.add(userTemp)
                    }
                    callback.getUsers(true, usersList)

                    Log.d(TAG, usersList.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "getUsers:onCancelled", error.toException())
            }

        })

    }

    override fun getFriends(callback: FriendsCallback) {
        database.child(USERS_PATH).child(auth.currentUser!!.displayName!!).child("friends").addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val friends = (snapshot.value as HashMap<String, String>).values.toList()
                    callback.getFriends(true, friends)
                } else {
                    callback.getFriends(true, emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "getFriends:onCancelled", error.toException())
                callback.getFriends(false, emptyList())
            }

        })
    }

    override fun isFriend(oUsername: String, callback: IsFriendCallback) {
        database.child(USERS_PATH).child(auth.currentUser!!.displayName!!).child("friends").addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    Log.d(TAG, "${snapshot.value.toString()} isFriend: ${(snapshot.value as HashMap<String, String>).containsValue(oUsername)}")
                    callback.isFriend((snapshot.value as HashMap<String, String>).containsValue(oUsername))
                } else {
                    callback.isFriend(false)
                }
                Log.d(TAG, "IsFriend:success")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "isFriend:onCancelled", error.toException())
            }

        })
    }

    override fun addFriend(oUsername: String, callback: FriendsCallback){
        val childRef: DatabaseReference = database.child(USERS_PATH).child(auth.currentUser!!.displayName!!).child("friends").push()
        val childKey: String? = childRef.key
        childKey.let {database.child(USERS_PATH).child(auth.currentUser!!.displayName!!).child("friends").child(childKey!!).setValue(oUsername).addOnCompleteListener {
            getFriends(callback)
        }}
    }

    override fun removeFriend(oUsername: String, callback: FriendsCallback) {
        database.child(USERS_PATH).child(auth.currentUser!!.displayName!!).child("friends").addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val friends = snapshot.value as HashMap<String, String>
                    val keys = friends.filterValues { it == oUsername }.keys
                    database.child(USERS_PATH).child(auth.currentUser!!.displayName!!).child("friends").child(keys.first()).removeValue()

                    getFriends(callback)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "removeFriend:onCancelled", error.toException())
            }

        })
    }

    override fun getMyUserInfo(callback: UserInfoCallback) {
        getUserInfo(auth.currentUser?.displayName ?: "", callback)
    }

    override fun getUserInfo(username: String, callback: UserInfoCallback) {
        database.child(USERS_PATH).child(username).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val countryTemp = snapshot.child("country").value as String?
                    val cityTemp = snapshot.child("city").value as String?
                    val urlProfileImage = snapshot.child("urlProfileImage").value as String?
                    val friends = snapshot.child("friends").value as HashMap<String, String>?
                    val posts: HashMap<String, HashMap<*,*>>? =
                        if (snapshot.child("posts").exists())
                            (snapshot.child("posts").value as HashMap<String, HashMap<*,*>>)
                        else
                            null

                    val postsList = mutableListOf<PostModel>()

                    posts?.let {
                        posts.forEach { (_, postMappedInfo) ->
                            postsList.add(
                                PostModel(
                                    (postMappedInfo as HashMap<String, String>)["timestamp"]!!,
                                    postMappedInfo["textContent"]!!,
                                    postMappedInfo["urlToImage"]!!
                                )
                            )
                        }
                    }

                    val friendsAsList = ArrayList<String>(friends?.values ?: emptyList())

                    val userTemp = UserModel(
                        username,
                        urlProfileImage?.ifEmpty { DEFAULT_PROFILE_URL } ?: DEFAULT_PROFILE_URL,
                        cityTemp ?: "",
                        countryTemp ?: "",
                        friendsAsList,
                        postsList
                    )

                    callback.getInfo(true, userTemp)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "getMyUserInfo:onCancelled", error.toException())
                callback.getInfo(false, null)
            }

        })
    }

    override fun getUsersInfo(usernames: List<String>, callback: UsersListCallback){
        database.child(USERS_PATH).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val result = mutableListOf<UserModel>()

                    usernames.filter { it != "" }.forEach { usernameTemp ->
                        val countryTemp = snapshot.child(usernameTemp).child("country").value as String
                        val cityTemp = snapshot.child(usernameTemp).child("city").value as String
                        val urlProfileImageTemp = snapshot.child(usernameTemp).child("urlProfileImage").value as String
                        val posts: HashMap<String, HashMap<*,*>>? =
                            if (snapshot.child(usernameTemp).child("posts").exists())
                                (snapshot.child(usernameTemp).child("posts").value as HashMap<String, HashMap<*,*>>)
                            else
                                null

                        val postsList = mutableListOf<PostModel>()

                        posts?.let {
                            posts.forEach { (_, postMappedInfo) ->
                                postsList.add(
                                    PostModel(
                                        (postMappedInfo as HashMap<String, String>)["timestamp"]!!,
                                        postMappedInfo["textContent"]!!,
                                        postMappedInfo["urlToImage"]!!
                                    )
                                )
                            }
                        }

                        val tempFriend = UserModel(usernameTemp, urlProfileImageTemp.ifEmpty { DEFAULT_PROFILE_URL }, cityTemp, countryTemp, emptyList(), postsList)
                        result.add(tempFriend)
                    }

                    callback.getUsers(true, result)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback.getUsers(false, emptyList())
            }

        })
    }

    override fun getOwnPosts(): List<PostModel> {
        TODO("Not yet implemented")
    }

    override fun getFriendsPosts(): List<PostModel> {
        TODO("Not yet implemented")
    }

}

fun interface UsersListCallback {
    fun getUsers(success: Boolean, usersList: List<UserModel>)
}

fun interface IsFriendCallback {
    fun isFriend(result: Boolean)
}

fun interface FriendsCallback {
    fun getFriends(success: Boolean, friendsList: List<String>)
}

fun interface UserInfoCallback {
    fun getInfo(success: Boolean, userInfo: UserModel?)
}
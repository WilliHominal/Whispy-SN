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


}

@Suppress("UNCHECKED_CAST")
class UsersDaoImpl: UsersDao {
    companion object {
        private const val TAG = "USERS_DAO_IMPL"
        private const val USERS_PATH = "users"
        private const val USERNAMESLIST_PATH = "usernamesList"
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
                        val posts = snapshot.child(key).child("posts").value as List<PostModel>

                        val friendsAsList = ArrayList<String>(friends.values)

                        val userTemp = UserModel(key,
                            if (urlProfileImage == "") "https://img.freepik.com/free-vector/mother-holding-with-baby-character_40876-2370.jpg?t=st=1648932627~exp=1648933227~hmac=594237a8b84ee67bf90f676ce7d6a5cc868ec3a83c7479e0b4af26173456365c&w=826" else urlProfileImage,
                            cityTemp,
                            countryTemp,
                            friendsAsList,
                            posts
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
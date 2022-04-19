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
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

sealed class EventResponse {
    data class Changed(val snapshot: DataSnapshot): EventResponse()
    data class Cancelled(val error: DatabaseError): EventResponse()
}

suspend fun DatabaseReference.singleValueEvent(): EventResponse = suspendCoroutine { continuation ->
    val valueEventListener = object : ValueEventListener {
        override fun onCancelled(error: DatabaseError) {
            continuation.resume(EventResponse.Cancelled(error))
        }

        override fun onDataChange(snapshot: DataSnapshot) {
            continuation.resume(EventResponse.Changed(snapshot))
        }
    }
    addListenerForSingleValueEvent(valueEventListener)
}

@Suppress("UNCHECKED_CAST")
class UsersDaoImpl {
    companion object {
        private const val TAG = "USERS_DAO_IMPL"
        private const val USERS_PATH = "users"
        //private const val USERNAMESLIST_PATH = "usernamesList"
        private const val DEFAULT_PROFILE_URL = "https://firebasestorage.googleapis.com/v0/b/whispy-d7f2b.appspot.com/o/images%2Fuser-default.png?alt=media&token=20f06fda-a67a-4b4e-bc48-590d65db81e7"
    }

    private val auth = FirebaseAuth.getInstance()
    private val database = Firebase.database.reference

    suspend fun getUsers() : List<UserModel> {
        when (val result = database.child(USERS_PATH).singleValueEvent()) {
            is EventResponse.Changed -> {
                val snapshot = result.snapshot

                if (!snapshot.exists()) return emptyList()

                val dataResponse = snapshot.value as HashMap<String, *>
                val usersList = mutableListOf<UserModel>()

                dataResponse.forEach {(key, _) ->
                    val countryTemp = snapshot.child(key).child("country").value as String
                    val cityTemp = snapshot.child(key).child("city").value as String
                    val urlProfileImage = snapshot.child(key).child("urlProfileImage").value as String
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

                return usersList
            }
            is EventResponse.Cancelled -> {
                val message = result.error.toException().message
                Log.e(TAG, "Error: $message")

                throw DatabaseRequestCancelledException("getUsers")
            }
        }
    }

    suspend fun getFriends() : List<String> {
        when (val result = database.child(USERS_PATH).child(auth.currentUser!!.displayName!!).child("friends").singleValueEvent()){
            is EventResponse.Changed -> {
                val snapshot = result.snapshot
                if (!snapshot.exists()) return emptyList()

                val dataResponse = snapshot.value as HashMap<String, String>
                return dataResponse.values.toList()
            }
            is EventResponse.Cancelled -> {
                val message = result.error.toException().message
                Log.e(TAG, "Error: $message")

                throw DatabaseRequestCancelledException("getFriends")
            }
        }
    }

    fun addFriend(oUsername: String){
        val childRef: DatabaseReference = database.child(USERS_PATH).child(auth.currentUser!!.displayName!!).child("friends").push()
        val childKey: String? = childRef.key
        childKey.let {database.child(USERS_PATH).child(auth.currentUser!!.displayName!!).child("friends").child(childKey!!).setValue(oUsername)}
    }

    fun removeFriend(oUsername: String) {
        database.child(USERS_PATH).child(auth.currentUser!!.displayName!!).child("friends").addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val friends = snapshot.value as HashMap<String, String>
                    val keys = friends.filterValues { it == oUsername }.keys
                    database.child(USERS_PATH).child(auth.currentUser!!.displayName!!).child("friends").child(keys.first()).removeValue()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "removeFriend:onCancelled", error.toException())
            }

        })
    }

    suspend fun getMyUserInfo() : UserModel = getUserInfo(auth.currentUser?.displayName ?: "")

    suspend fun getUserInfo(username: String) : UserModel {
        when (val result = database.child(USERS_PATH).child(username).singleValueEvent()) {
            is EventResponse.Changed -> {
                val snapshot = result.snapshot
                if (!snapshot.exists()) throw SnapshotDoesntExistsException("root/$USERS_PATH/$username")

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

                return UserModel(
                    username,
                    urlProfileImage?.ifEmpty { DEFAULT_PROFILE_URL } ?: DEFAULT_PROFILE_URL,
                    cityTemp ?: "",
                    countryTemp ?: "",
                    friendsAsList,
                    postsList
                )
            }
            is EventResponse.Cancelled -> {
                val message = result.error.toException().message
                Log.e(TAG, "Error: $message")
                throw DatabaseRequestCancelledException("getUserInfo")
            }
        }
    }

    suspend fun getUsersInfo(usernames: List<String>) : List<UserModel> {
        val result = mutableListOf<UserModel>()
        usernames.forEach { username ->
            result.add(getUserInfo(username))
        }
        return result
    }
}

class SnapshotDoesntExistsException(path: String) : Exception("No data found. Path: $path")

class DatabaseRequestCancelledException(from: String) : Exception("Request cancelled: $from")
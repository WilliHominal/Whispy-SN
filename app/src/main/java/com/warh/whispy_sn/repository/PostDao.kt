package com.warh.whispy_sn.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.warh.whispy_sn.model.PostModel

interface PostDao {
    fun addPost(post: PostModel)
}

class PostDaoImpl: PostDao {
    private val auth = FirebaseAuth.getInstance()
    private val database = Firebase.database.reference

    companion object {
        private const val TAG = "POST_DAO_IMPL"
        private const val USERS_PATH = "users"
        private const val USERNAMESLIST_PATH = "usernamesList"
    }

    override fun addPost(post: PostModel) {
        val childRef: DatabaseReference = database.child(USERS_PATH).child(auth.currentUser!!.displayName!!).child("posts").push()
        val childKey: String? = childRef.key
        database.child(USERS_PATH).child(auth.currentUser!!.displayName!!).child("posts").child(childKey!!).setValue(post)
    }

}
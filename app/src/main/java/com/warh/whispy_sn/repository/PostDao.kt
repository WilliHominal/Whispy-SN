package com.warh.whispy_sn.repository

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.warh.whispy_sn.model.PostModel
import java.io.ByteArrayOutputStream

interface PostDao {
    fun addPost(timestamp: String, postContent: String, imageView: ImageView? = null)
}

class PostDaoImpl: PostDao {
    private val auth = FirebaseAuth.getInstance()
    private val database = Firebase.database.reference
    private val storageRef = Firebase.storage.reference

    companion object {
        private const val TAG = "POST_DAO_IMPL"
        private const val USERS_PATH = "users"
        private const val USERNAMESLIST_PATH = "usernamesList"
    }

    override fun addPost(timestamp: String, postContent: String, imageView: ImageView?) {
        val childRef: DatabaseReference = database.child(USERS_PATH).child(auth.currentUser!!.displayName!!).child("posts").push()
        val childKey: String? = childRef.key

        if (imageView != null){
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            val finalRef = storageRef
                .child("images")
                .child(auth.currentUser!!.displayName!!)
                .child("post")
                .child("$timestamp.jpg")

            val uploadTask = finalRef.putBytes(data)

            uploadTask.addOnFailureListener{ exception ->
                Log.w(TAG, "uploadTask:onFailure", exception)
            }.addOnSuccessListener { taskSnapshot ->
                Log.d(TAG, "uploadTask:onSuccess -> metadata: ${taskSnapshot.metadata}")
                finalRef.downloadUrl.addOnSuccessListener { uri ->
                    val post = PostModel(timestamp, postContent, uri.toString())
                    database.child(USERS_PATH).child(auth.currentUser!!.displayName!!).child("posts").child(childKey!!).setValue(post)
                }
            }
        } else {
            val post = PostModel(timestamp, postContent, "")
            database.child(USERS_PATH).child(auth.currentUser!!.displayName!!).child("posts").child(childKey!!).setValue(post)
        }






    }

}
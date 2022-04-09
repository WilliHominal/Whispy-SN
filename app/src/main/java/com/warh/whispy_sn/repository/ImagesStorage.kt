package com.warh.whispy_sn.repository

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class ImagesStorage {
    companion object{
        private const val TAG = "IMAGES_STORAGE"
    }

    private val auth = FirebaseAuth.getInstance()

    private val storage = Firebase.storage
    private val storageRef = storage.reference

    fun uploadProfilePhoto(imageView: ImageView){
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = storageRef
            .child("images")
            .child(auth.currentUser!!.displayName!!)
            .child("profile")
            .child("profile_img.jpg")
            .putBytes(data)

        uploadTask.addOnFailureListener{ exception ->
            Log.w(TAG, "uploadTask:onFailure", exception)
        }.addOnSuccessListener { taskSnapshot ->
            Log.d(TAG, "uploadTask:onSuccess -> metadata: ${taskSnapshot.metadata.toString()}")
        }
    }

    fun uploadPostPhoto(idImage: String, imageView: ImageView){
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val finalRef = storageRef
            .child("images")
            .child(auth.currentUser!!.displayName!!)
            .child("post")
            .child("$idImage.jpg")

        val uploadTask = finalRef.putBytes(data)

        uploadTask.addOnFailureListener{ exception ->
            Log.w(TAG, "uploadTask:onFailure", exception)
        }.addOnSuccessListener { taskSnapshot ->
            Log.d(TAG, "uploadTask:onSuccess -> metadata: ${taskSnapshot.metadata}")
        }

        val imageUrl = finalRef.downloadUrl.addOnSuccessListener {

        }
    }
}
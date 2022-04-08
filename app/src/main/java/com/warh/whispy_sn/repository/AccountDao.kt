package com.warh.whispy_sn.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.warh.whispy_sn.model.PostModel


interface AccountDao {
    fun registerAccount(username: String, email: String, password: String, city: String, country: String, callback: RegisterAccountCallback)
    fun login(username: String, password: String, callback: LoginAccountCallback)
}

class AccountDaoImpl() : AccountDao {
    companion object {
        private const val TAG = "ACCOUNT_DAO_IMPL"
        private const val USERS_PATH = "users"
        private const val USERNAMESLIST_PATH = "usernamesList"
    }

    private val database: DatabaseReference = Firebase.database.reference
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun registerAccount(username: String, email: String, password: String, city: String, country: String, callback: RegisterAccountCallback) {
        database.child(USERNAMESLIST_PATH).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    if ((snapshot.value as HashMap<*, *>).containsValue(username)) {
                        callback.registerAccount(false, null, "Username already in use")
                    } else {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(TAG, "createUserWithEmail:success")

                                    val profileUpdates = UserProfileChangeRequest.Builder()
                                        .setDisplayName(username).build()

                                    auth.currentUser?.updateProfile(profileUpdates)

                                    sendEmailVerification()
                                    saveUserInDatabase(username, email, country, city)

                                    callback.registerAccount(true, auth.currentUser, null)
                                } else {
                                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                    callback.registerAccount(false, null, "Create account failed")
                                }
                            }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "register:onCancelled", error.toException())
            }

        })
    }

    private fun saveUserInDatabase(username: String, email: String, country: String, city: String) {
        val mapaValores = mutableMapOf<String, Any>()
        mapaValores["email"] = email
        mapaValores["country"] = country
        mapaValores["city"] = city
        mapaValores["urlProfileImage"] = ""

        database.child(USERS_PATH).child(username).setValue(mapaValores)

        val friendsRef = database.child(USERS_PATH).child(username).child("friends").push()
        val friendsKey = friendsRef.key
        friendsKey.let { database.child(USERS_PATH).child(username).child("friends").child(friendsKey!!).setValue("") }

        val childRef: DatabaseReference = database.child(USERNAMESLIST_PATH).push()
        val childKey: String? = childRef.key
        childKey.let {database.child(USERNAMESLIST_PATH).child(childKey!!).setValue(username)}
    }

    private fun sendEmailVerification(){
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Log.d(TAG, "sendEmailVerification:success")
                } else {
                    Log.w(TAG, "sendEmailVerification:failure")
                }
            }
    }

    override fun login(username: String, password: String, callback: LoginAccountCallback) {
        val emailLogin: Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()

        if (emailLogin) {
            auth.signInWithEmailAndPassword(username, password).addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    Log.d(TAG, "signInWithEmail: Success")
                    callback.loginAccount(true, auth.currentUser, null)
                } else {
                    Log.w(TAG, "signInWithEmail: Failure", result.exception)
                    callback.loginAccount(false, null, result.exception.toString())
                }
            }
        } else {
            database.child(USERS_PATH).child(username).child("email").addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        auth.signInWithEmailAndPassword(snapshot.value as String, password).addOnCompleteListener { result ->
                            if (result.isSuccessful){
                                Log.d(TAG, "signInWithEmail: Success")
                                callback.loginAccount(true, auth.currentUser, null)
                            } else {
                                Log.w(TAG, "signInWithEmail: Failure", result.exception)
                                callback.loginAccount(false, null, result.exception.toString())
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "login:onCancelled", error.toException())
                }

            })
        }
    }
}

fun interface RegisterAccountCallback {
    fun registerAccount(success: Boolean, user: FirebaseUser?, error: String?)
}

fun interface LoginAccountCallback {
    fun loginAccount(success: Boolean, user: FirebaseUser?, error: String?)
}
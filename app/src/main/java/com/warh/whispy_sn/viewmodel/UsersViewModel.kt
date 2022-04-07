package com.warh.whispy_sn.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.warh.whispy_sn.model.UserModel
import com.warh.whispy_sn.repository.UsersDaoImpl

class UsersViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val usersDao = UsersDaoImpl()

    val users = MutableLiveData<List<UserModel>>()
    var friends = MutableLiveData<List<String>>()

    fun updateUsersList(){
        usersDao.getUsers { success, usersList ->
            if (success) users.postValue(usersList.filter { user -> user.username != auth.currentUser!!.displayName })
        }
    }

    fun updateFriendsList(){
        usersDao.getFriends { success, friendsList ->
            if (success) friends.postValue(friendsList)
        }
    }

    fun addFriend(oUsername: String){
        usersDao.addFriend(oUsername){ success, friendsList ->
            if (success) friends.postValue(friendsList)
        }
    }

    fun removeFriend(oUsername: String){
        usersDao.removeFriend(oUsername){ success, friendsList ->
            if (success) friends.postValue(friendsList)
        }
    }

}
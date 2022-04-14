package com.warh.whispy_sn.viewmodel

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.warh.whispy_sn.model.UserModel
import com.warh.whispy_sn.repository.ImagesStorage
import com.warh.whispy_sn.repository.PostDaoImpl
import com.warh.whispy_sn.repository.UsersDaoImpl

class UsersViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val usersDao = UsersDaoImpl()
    private val postDao = PostDaoImpl()

    private val storage = ImagesStorage()

    val users = MutableLiveData<List<UserModel>>()
    val friends = MutableLiveData<List<String>>()
    val myInfo = MutableLiveData<UserModel>()
    val friendsInfo = MutableLiveData<List<UserModel>>()

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

    private fun getMyUserInfo(){
        usersDao.getMyUserInfo{ success, userInfo ->
            if (success) myInfo.postValue(userInfo)
        }
    }

    private fun updateFriendsInfo(){
        myInfo.value?.let {
            usersDao.getUsersInfo(it.friends){ success, friendsInfoList ->
                if (success) friendsInfo.postValue(friendsInfoList)
            }
        }
    }

    fun loadData(){
        getMyUserInfo()
        updateFriendsInfo()
    }

    fun addPost(timestamp: String, postContent: String, imageView: ImageView? = null){
        postDao.addPost(timestamp, postContent, imageView)
    }

    fun editProfile(imageView: ImageView){
        storage.uploadProfilePhoto(imageView)
    }
}
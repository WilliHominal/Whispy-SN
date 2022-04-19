package com.warh.whispy_sn.viewmodel

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warh.whispy_sn.model.UserModel
import com.warh.whispy_sn.repository.ImagesStorage
import com.warh.whispy_sn.repository.PostDaoImpl
import com.warh.whispy_sn.repository.UsersDaoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersViewModel: ViewModel() {
    private val usersDao = UsersDaoImpl()
    private val postDao = PostDaoImpl()

    private val storage = ImagesStorage()

    val users = MutableLiveData<List<UserModel>>()
    val friends = MutableLiveData<List<String>>()
    val myInfo = MutableLiveData<UserModel>()
    val friendsInfo = MutableLiveData<List<UserModel>>()

    fun updateUsersList(){
        viewModelScope.launch(Dispatchers.IO) {
            users.postValue(usersDao.getUsers())
        }
    }

    fun updateFriendsList(){
        viewModelScope.launch(Dispatchers.IO) {
            friends.postValue(usersDao.getFriends())
        }
    }

    fun addFriend(oUsername: String){
        viewModelScope.launch(Dispatchers.IO) {
            usersDao.addFriend(oUsername)
            friends.postValue(usersDao.getFriends())
        }
    }

    fun removeFriend(oUsername: String){
        viewModelScope.launch(Dispatchers.IO) {
            usersDao.removeFriend(oUsername)
            friends.postValue(usersDao.getFriends())
        }
    }

    fun loadData(){
        viewModelScope.launch(Dispatchers.IO){
            launch {
                myInfo.postValue(usersDao.getMyUserInfo())
            }
            launch {
                myInfo.value?.let {
                    friendsInfo.postValue(usersDao.getUsersInfo(it.friends))
                }
            }
        }
    }

    fun addPost(timestamp: String, postContent: String, imageView: ImageView? = null){
        postDao.addPost(timestamp, postContent, imageView)
    }

    fun editProfile(imageView: ImageView){
        storage.uploadProfilePhoto(imageView)
    }
}
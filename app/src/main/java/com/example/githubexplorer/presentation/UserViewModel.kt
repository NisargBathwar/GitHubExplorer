package com.example.githubexplorer.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.example.githubexplorer.domain.usecase.DetailUseCase
import com.example.githubexplorer.domain.usecase.GetRepoUseCase
import com.example.githubexplorer.domain.usecase.GetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val getUseCase: GetUseCase , private val detailUseCase: DetailUseCase , private val getRepoUseCase: GetRepoUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState : StateFlow<UserUiState> = _uiState.asStateFlow()

    private var currentPage = 1
    private var isLoadMore = false

    fun onActions(actions : UserActions){
        when(actions){
            is UserActions.GetUsers -> {
                viewModelScope.launch {
                    try {
                        _uiState.value = _uiState.value.copy(
                            query = actions.query,
                            isLoading = true
                        )
                        currentPage = 1
                        val users = getUseCase(actions.query , currentPage)

                        _uiState.value = _uiState.value.copy(
                            users = users ,
                            isLoading = false ,
                            error =  null
                        )
                    }catch (e : Exception){
                        _uiState.value = _uiState.value.copy(
                            users = emptyList(),
                            isLoading = false ,
                            error = e.message
                        )
                    }
                }
            }
            is UserActions.QueryChanged -> {
                _uiState.value = _uiState.value.copy(
                    query = actions.newQuery
                )
            }
            is UserActions.DetailUser -> {
               viewModelScope.launch {
                   _uiState.value = _uiState.value.copy(
                       detailUser =  null ,
                       isLoading = false
                   )
                    try {

                        val users = detailUseCase(actions.userName)

                        _uiState.value = _uiState.value.copy(
                            detailUser = users,
                            isLoading = false,
                            error = null
                        )

                    } catch (e: Exception) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = e.message
                        )
                    }
                }
            }
            is UserActions.LoadMore -> {
                viewModelScope.launch{
                    isLoadMore = true
                    try{
                        currentPage++

                        val moreUsers = getUseCase(_uiState.value.query, currentPage)

                        _uiState.value = _uiState.value.copy(
                            users = _uiState.value.users + moreUsers,
                            isLoadingMore = false
                        )
                    }finally {
                        isLoadMore = false
                    }
                }
            }

            is UserActions.GetRepos -> {
                viewModelScope.launch {
                    try {
                        Log.d("REPO", "Starting repo request")
                        val repo = getRepoUseCase.invoke(actions.username)
                        Log.d("REPO", "Received ${actions.username} repos")
                        _uiState.value =_uiState.value.copy(
                            repos = repo
                        )
                    }catch (e: HttpException) {
                        Log.d("HTTP", e.response()?.errorBody()?.string().toString())
                    }
                }
            }
        }
    }
}

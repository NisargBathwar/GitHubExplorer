package com.example.githubexplorer.di

import com.example.githubexplorer.data.repo.RepoImpl
import com.example.githubexplorer.domain.repo.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    @Singleton
    abstract fun provideRepo(repoImpl: RepoImpl) : Repository

}
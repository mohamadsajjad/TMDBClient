package com.example.tmdbclient.presentation.di.tvShow

import com.example.tmdbclient.presentation.tvShow.TvShowActivity
import dagger.Subcomponent

@Subcomponent(modules = [TvShowModule::class])
interface TvShowSubComponent {
    fun inject(tvShowActivity: TvShowActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create():TvShowSubComponent
    }
}
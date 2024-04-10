package com.iitism.hackfestapp.ui.aboutus

import com.iitism.hackfestapp.auth.Refractor.BaseRepository
import com.iitism.hackfestapp.ui.aboutus.retrofit.AboutUsApi

class AboutUsRepository constructor(private val api: AboutUsApi) {
    suspend fun getAllOrganizers() = api.getAllOrganizers()

    suspend fun getAllNotices(end:String) = api.getAllNotices(endpoint = "announcement/"+end)

    suspend fun getAllProblems() = api.getAllProblems()

}
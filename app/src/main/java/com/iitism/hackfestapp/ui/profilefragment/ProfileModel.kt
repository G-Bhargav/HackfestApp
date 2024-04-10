package com.iitism.hackfestapp.ui.profilefragment

import com.iitism.hackfestapp.auth.Data

data class ProfileResponse(
    val `data`: Data,
    val message: String
)
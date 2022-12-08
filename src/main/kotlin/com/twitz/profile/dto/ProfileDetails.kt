package com.twitz.profile.dto

import com.twitz.profile.ProfileType

data class ProfileDetails(
        var name: String,
        var username: String,
        var email: String,
        var type: ProfileType
)

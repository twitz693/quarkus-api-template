package com.twitz.profile.model.dto

import com.twitz.profile.ProfileType
import com.twitz.profile.model.Profile

data class ProfileDetails(
        var name: String,
        var username: String,
        var email: String,
        var type: ProfileType
) {
    companion object {
        fun fromEntity(profile: Profile): ProfileDetails {
            return ProfileDetails(
                    profile.name,
                    profile.username,
                    profile.email,
                    profile.type
            )
        }
    }
}


package com.twitz.profile.model.dto

import com.twitz.profile.ProfileType
import com.twitz.profile.model.Profile

data class CreateProfile(
        var name: String,
        var username: String,
        var password: String,
        var email: String,
) {
        companion object {
                fun toEntity(createProfile: CreateProfile): Profile {
                        return Profile(
                                createProfile.name,
                                createProfile.username,
                                createProfile.password,
                                createProfile.email,
                                ProfileType.GUEST
                        )
                }
        }
}

package com.twitz.profile

import com.twitz.profile.dto.CreateProfile
import com.twitz.profile.dto.ProfileDetails
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path

@Path("/profiles")
class ProfileResource {

    @GET
    fun getProfiles(): List<ProfileDetails> {
        return listOf(
                ProfileDetails("John Doe", "jdoe", "password", ProfileType.USER),
                ProfileDetails("Amara", "amara", "password", ProfileType.ADMIN),
        )
    }

    @GET
    @Path("/{username}")
    fun getProfile(username: String): ProfileDetails {
        return ProfileDetails("John Doe", "jdoe", "password", ProfileType.USER)
    }

    @POST
    fun createProfile(newProfile: CreateProfile): ProfileDetails {
        return ProfileDetails(
                newProfile.name,
                newProfile.username,
                newProfile.email,
                ProfileType.USER)
    }

    @DELETE
    @Path("/{username}")
    fun deleteProfile(username: String) {
        return
    }
}
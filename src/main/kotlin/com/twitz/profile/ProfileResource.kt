package com.twitz.profile

import com.twitz.CollectionResponse
import com.twitz.ResourceDoesNotExistsException
import com.twitz.ResourcesAlreadyExistsException
import com.twitz.profile.model.dto.CreateProfile
import com.twitz.profile.model.dto.ProfileDetails
import io.quarkus.security.Authenticated
import javax.annotation.security.RolesAllowed
import javax.inject.Inject
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path

@Path("/profiles")
@Authenticated
class ProfileResource {

    @Inject
    lateinit var profileService: ProfileService

    @GET
    fun getProfiles(): CollectionResponse<List<ProfileDetails>> {
        val listOfProfiles = profileService.listAll()
        val responseList = listOfProfiles.map(ProfileDetails.Companion::fromEntity)
        return CollectionResponse(responseList)
    }

    @GET
    @Path("/{username}")
    fun getProfile(username: String): ProfileDetails {
        val resultProfile = profileService.find(username)

        resultProfile?.let {
            return ProfileDetails.fromEntity(it)
        } ?: throw ResourceDoesNotExistsException()
    }

    @POST
    fun createProfile(profileToCreate: CreateProfile): ProfileDetails {
        val entityToCreate = CreateProfile.toEntity(profileToCreate)
        val profileCreated = profileService.create(entityToCreate)

        profileCreated?.let {
            return ProfileDetails.fromEntity(it)
        } ?: throw ResourcesAlreadyExistsException()

    }

    @DELETE
    @Path("/{username}")
    fun deleteProfile(username: String): Unit {
        profileService.delete(username)
        return
    }
}
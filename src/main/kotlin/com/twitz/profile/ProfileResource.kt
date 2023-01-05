package com.twitz.profile

import com.twitz.CollectionResponse
import com.twitz.ResourceDoesNotExistsException
import com.twitz.profile.model.Profile
import com.twitz.profile.model.dto.CreateProfile
import com.twitz.profile.model.dto.ProfileDetails
import io.smallrye.mutiny.Uni
import javax.inject.Inject
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path

@Path("/profiles")
class ProfileResource {

    @Inject
    lateinit var profileService: ProfileService

    @GET
    fun getProfiles(): Uni<CollectionResponse<List<ProfileDetails>>> {
        return profileService.listAll()
            .map(ProfileDetails.Companion::fromEntity)
            .collect().asList()
            .map { list -> CollectionResponse(list) }
    }

    @GET
    @Path("/{username}")
    fun getProfile(username: String): Uni<ProfileDetails> {
        return profileService.find(username)
            .onItem().ifNull().failWith { ResourceDoesNotExistsException() }
            .onItem().ifNotNull().transform { ent -> ent?.let { ProfileDetails.fromEntity(ent) } }
    }

    @POST
    fun createProfile(profileToCreate: CreateProfile): Uni<ProfileDetails> {
        return profileService
            .create(CreateProfile.toEntity(profileToCreate))
            .onItem()
            .transform { p -> ProfileDetails(p.name, p.username, p.email, p.type) }

    }

    @DELETE
    @Path("/{username}")
    fun deleteProfile(username: String) : Uni<Void> {
        return profileService.delete(username).map { null }
    }
}
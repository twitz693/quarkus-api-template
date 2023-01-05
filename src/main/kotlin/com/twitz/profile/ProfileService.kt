package com.twitz.profile

import com.twitz.ResourceDoesNotExistsException
import com.twitz.ResourcesAlreadyExistsException
import com.twitz.profile.model.Profile
import io.quarkus.logging.Log
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import java.lang.RuntimeException
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class ProfileService {

    @Inject
    lateinit var profileRepository: ProfileRepository

    fun create(profile: Profile): Uni<Profile> {
        return Uni.createFrom().item(profile)
            .invoke { p -> Log.info("Creating profile for username: ${p.username}") }
            .chain { p -> find(p.username) }
            .onItem().ifNotNull().failWith { ResourcesAlreadyExistsException() }
            .onFailure(ResourceDoesNotExistsException::class.java)
            .recoverWithUni { _ -> profileRepository.persist(profile) }
    }

    fun find(username: String): Uni<Profile> {
        return profileRepository
            .find("username", username)

            .firstResult()
            .onItem().ifNull().failWith { ResourceDoesNotExistsException() }
            .onItem().ifNotNull().transform { p -> p}
    }

    fun listAll(): Multi<Profile> {
        return profileRepository.streamAll()
    }

    fun delete(username: String): Uni<Nothing> {
        return Uni.createFrom().item(username)
            .invoke { user -> Log.info("Deleting profile with username: $user") }
            .chain { user -> find(user) }
            .chain { p -> p.id?.let { profileRepository.deleteById(it) } }
            .invoke { wasDeleted -> if (!wasDeleted) Log.error("Could not delete profile with username: $username") }
            .map { null }
    }
}
package com.twitz.profile

import com.twitz.profile.model.Profile
import io.quarkus.logging.Log
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class ProfileService {

    @Inject
    lateinit var profileRepository: ProfileRepository

    fun create(profile: Profile): Profile? {

        if (isUsernameAvailable(profile.username)) {
            profileRepository.persist(profile)
            Log.info("Profile for username [${profile.username}] created.")
            return profile
        } else {
            return null
        }
    }

    fun find(username: String): Profile? {
        return profileRepository
            .find("username", username)
            .firstResult()
    }

    fun listAll(): List<Profile> {
        return profileRepository.listAll()
    }

    private fun isUsernameAvailable(username: String): Boolean {
        val isUsernameAvailable = find(username)?.let { false } ?: true

        if (!isUsernameAvailable) {
            Log.info("Username [$username] is already taken.")
        }

        return isUsernameAvailable
    }

    fun delete(username: String): Unit {
        if (!isUsernameAvailable(username)) {
            profileRepository.delete("username", username)
            Log.info("Profile for username [$username] deleted.")
        } else {
            Log.warn("Trying to delete username [$username] that does not exists.")

        }
    }
}
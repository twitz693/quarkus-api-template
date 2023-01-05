package com.twitz.profile

import com.twitz.profile.model.Profile
import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ProfileRepository : ReactivePanacheMongoRepository<Profile> {
}
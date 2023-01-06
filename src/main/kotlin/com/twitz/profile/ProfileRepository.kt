package com.twitz.profile

import com.twitz.profile.model.Profile
import io.quarkus.mongodb.panache.kotlin.PanacheMongoRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ProfileRepository : PanacheMongoRepository<Profile> {
}
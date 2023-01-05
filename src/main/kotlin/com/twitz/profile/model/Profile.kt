package com.twitz.profile.model

import com.twitz.profile.ProfileType
import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntityBase
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Profile (
    var name: String = "",
    var username: String = "",
    var password: String = "",
    var email: String = "",
    var type: ProfileType = ProfileType.UNKNOWN,
) : PanacheMongoEntity()
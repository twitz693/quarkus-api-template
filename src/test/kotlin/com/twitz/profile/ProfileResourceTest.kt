package com.twitz.profile

import com.twitz.profile.model.Profile
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import org.mockito.Mockito

@QuarkusTest
class ProfileResourceTest {

    @InjectMock
    lateinit var profileRepository: ProfileRepository

    @Test
    fun whenRequestingAllProfilesWithEmptyDatasourceThenReturnEmptyList() {

        Mockito.doReturn(emptyList<Profile>())
            .`when`(profileRepository)
            .listAll()


        val expectedBody = JSONFormatter.formatWithoutSpaces(
            """
                {
                    "data": []
                }
            """
        )

        given()
            .`when`().get("/profiles")
            .then()
            .statusCode(200)
            .body(`is`(expectedBody))
    }

    @Test
    fun whenRequestingAllProfilesWithTwoProfilesSavedThenReturnListOfProfiles() {

        val profileOne = Profile("name1", "username1", "surname1", "email1", ProfileType.GUEST)
        val profileTwo = Profile("name2", "username2", "surname2", "email2", ProfileType.GUEST)
        val mockedList = listOf(profileOne, profileTwo)

        Mockito.doReturn(mockedList)
            .`when`(profileRepository)
            .listAll()

        val stringBodyExpected = """
                {
                    "data": [
                        {
                            "name": "name1",
                            "username": "username1",
                            "email": "email1",
                            "type": "GUEST"
                        },
                        {
                            "name": "name2",
                            "username": "username2",
                            "email": "email2",
                            "type": "GUEST"
                        }
                    ]
                }
            """

        val expectedBody = JSONFormatter.formatWithoutSpaces(stringBodyExpected)

        given()
            .`when`().get("/profiles")
            .then()
            .statusCode(200)
            .body(`is`(expectedBody))
    }
}
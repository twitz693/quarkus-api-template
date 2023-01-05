package com.twitz

class ResourcesAlreadyExistsException : Exception() {
    override val message: String?
        get() = "Resources already exists"
}
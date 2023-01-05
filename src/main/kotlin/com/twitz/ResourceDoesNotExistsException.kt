package com.twitz

class ResourceDoesNotExistsException : Exception() {
    override val message: String?
        get() = "Resource does not exists"
}
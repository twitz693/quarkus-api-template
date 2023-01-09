package com.twitz.profile

class JSONFormatter {

    companion object {
        fun formatWithoutSpaces(json: String): String {
            return json
                .replace("\n", "")
                .replace("\\", "")
                .replace(" ", "")
        }
    }
}
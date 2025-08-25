package com.example.utils.functional

import kotlinx.serialization.json.Json

inline fun <reified T>deserialize(data: String) = Json.decodeFromString<T>(data)

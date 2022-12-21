package com.dev.nicehash.domain.models

import com.dev.nicehash.helpers.EnumCollections

/**
 * Created by Alex Gladkov on 25.07.18.
 * Model for server status (domain layer)
 */
data class Status(val id: Int, val header: String, val title: String, val status: EnumCollections.Status)
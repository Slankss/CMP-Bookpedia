package com.plcoding.bookpedia.core.domain

import kotlinx.datetime.Clock

fun getCurrentTime(): Long {
    return Clock.System.now().toEpochMilliseconds()
}
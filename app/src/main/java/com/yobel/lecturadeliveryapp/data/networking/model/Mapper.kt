package com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model

import com.yobel.lecturadeliveryapp.data.database.model.LabelEntity
import com.yobel.lecturadeliveryapp.domain.model.Label
import com.yobel.lecturadeliveryapp.domain.model.User
import kotlin.math.tan

fun UserDto.toUser(): User {
    return User(
        name = user,
        enterprises = cia
    )
}

fun List<LabelDto>.toLabelList():List<Label> = map {
    Label(
        sequence = it.sequence,
        zone1 = it.zone1,
        zone2 = it.zone2,
        route = it.route,
        upload = it.upload,
        trackId = it.trackId,
        container = it.container ?: "",
        date = it.date
    )
}
fun LabelDto.toLabel():Label {
    return Label(
        sequence = sequence,
        zone1 = zone1,
        zone2 = zone2,
        route = route,
        upload = upload,
        trackId = trackId,
        container = container ?: "",
        date = date
    )
}

fun List<LabelDto>.toLabelEntity():List<LabelEntity> = map {
    LabelEntity(
        sequence = it.sequence,
        zone1 = it.zone1,
        zone2 = it.zone2,
        route = it.route,
        upload = it.upload,
        trackId = it.trackId,
        container = it.container ?: "",
        date = it.date,
        status = "S",
        readDate = ""
    )
}

fun LabelEntity.toEntityLabel():Label {
    return Label(
        sequence = sequence,
        zone1 = zone1,
        zone2 = zone2,
        route = route,
        upload = upload,
        trackId = trackId,
        container = container,
        date = date
    )
}

package com.kirikstreltsov.customerbot.dto

import com.kirikstreltsov.customerbot.dto.GetKeyboardComponentDto

data class Keyboard(
    val id: Long, val case: GetKeyboardComponentDto?, val switch: GetKeyboardComponentDto?,
    val keycap: GetKeyboardComponentDto?
) {
    constructor() : this(0L, null, null, null)
    constructor(
        case: GetKeyboardComponentDto?,
        switch: GetKeyboardComponentDto?,
        keycap: GetKeyboardComponentDto?
    ) : this(0L, case, switch, keycap)
}
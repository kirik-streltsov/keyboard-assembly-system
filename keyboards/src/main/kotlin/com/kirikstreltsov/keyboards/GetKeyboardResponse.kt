package com.kirikstreltsov.keyboards

import com.kirikstreltsov.components.dto.response.GetComponentResponse
import com.kirikstreltsov.entities.Keyboard

data class GetKeyboardResponse(val id: Long, val case: GetComponentResponse, val switch: GetComponentResponse,
                               val keycap: GetComponentResponse) {
    companion object {
        fun fromKeyboard(keyboard: Keyboard) : GetKeyboardResponse {
            return GetKeyboardResponse(
                keyboard.id,
                GetComponentResponse(keyboard.case.id, keyboard.case.name, keyboard.case.price),
                GetComponentResponse(keyboard.switch.id, keyboard.switch.name, keyboard.switch.price),
                GetComponentResponse(keyboard.keycap.id, keyboard.keycap.name, keyboard.switch.price)
            )
        }
    }
}

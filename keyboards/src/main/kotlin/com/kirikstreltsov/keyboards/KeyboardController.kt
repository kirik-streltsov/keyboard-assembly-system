package com.kirikstreltsov.keyboards

import com.kirikstreltsov.components.dto.response.GetComponentResponse
import com.kirikstreltsov.componentsclient.ComponentsClient
import com.kirikstreltsov.entities.Case
import com.kirikstreltsov.entities.Keyboard
import com.kirikstreltsov.entities.Keycap
import com.kirikstreltsov.entities.Switch
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/keyboards")
class KeyboardController(private val keyboardService: KeyboardService, private val componentsClient: ComponentsClient) {
    @GetMapping
    fun getAllKeyboards() : List<GetKeyboardResponse> = keyboardService.findAll().map {
        GetKeyboardResponse.fromKeyboard(it)
    }

    @GetMapping("/{id}")
    fun getKeyboardById(@PathVariable("id") id : Long) : GetKeyboardResponse? {
        val keyboard = keyboardService.findById(id) ?: return null
        return GetKeyboardResponse.fromKeyboard(keyboard)
    }

    @PostMapping
    fun createKeyboard(@RequestBody dto: CreateKeyboardRequest) : GetKeyboardResponse {

        val caseDto : GetComponentResponse = componentsClient.getCaseById(dto.caseId)!!
        val switchDto : GetComponentResponse = componentsClient.getSwitchById(dto.switchId)!!
        val keycapDto : GetComponentResponse = componentsClient.getKeycapById(dto.keycapId)!!

        val case = Case(caseDto.id, caseDto.name, caseDto.price)
        val switch = Switch(switchDto.id, switchDto.name, switchDto.price)
        val keycap = Keycap(keycapDto.id, keycapDto.name, keycapDto.price)

        val keyboard = Keyboard(case, switch, keycap)
        keyboardService.saveKeyboard(keyboard)

        return GetKeyboardResponse.fromKeyboard(keyboard)
    }
}
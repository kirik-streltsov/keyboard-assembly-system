package com.kirikstreltsov.keyboards

import com.kirikstreltsov.entities.Keyboard
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface KeyboardRepository : JpaRepository<Keyboard, Long>
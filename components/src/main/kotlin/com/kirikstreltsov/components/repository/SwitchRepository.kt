package com.kirikstreltsov.components.repository

import com.kirikstreltsov.entities.Switch
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SwitchRepository : JpaRepository<Switch, Long>
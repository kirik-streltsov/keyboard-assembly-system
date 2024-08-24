package com.kirikstreltsov.components.repository

import com.kirikstreltsov.entities.Keycap
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface KeycapRepository : JpaRepository<Keycap, Long>
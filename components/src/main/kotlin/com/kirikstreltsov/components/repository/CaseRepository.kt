package com.kirikstreltsov.components.repository

import com.kirikstreltsov.entities.Case
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CaseRepository : JpaRepository<Case, Long>
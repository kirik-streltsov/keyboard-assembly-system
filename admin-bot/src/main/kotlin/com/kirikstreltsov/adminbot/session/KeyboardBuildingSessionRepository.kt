package com.kirikstreltsov.adminbot.session

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface KeyboardBuildingSessionRepository : CrudRepository<KeyboardBuildingSession, Long>
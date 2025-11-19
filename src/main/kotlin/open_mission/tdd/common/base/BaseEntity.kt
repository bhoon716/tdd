package open_mission.tdd.common.base

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import lombok.Getter
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseEntity(
    @CreatedDate @Column(updatable = false, name = "created_at")
    private var createdAt: LocalDateTime? = null,
    @LastModifiedDate @Column(name = "updated_at")
    private var updatedAt: LocalDateTime? = null
)
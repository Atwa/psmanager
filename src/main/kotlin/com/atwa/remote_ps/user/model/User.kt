package com.atwa.remote_ps.user.model

import com.atwa.remote_ps.shop.Shop
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(unique = true)
    @Size(min = 3, max = 20)
    val username: String,
    @JsonIgnore
    @Size(min = 8, max = 40)
    var password: String,
    val enabled : Boolean = true,
    val createdAt: LocalDateTime? = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = LocalDateTime.now(),
    var token :String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    var shop: Shop?=null,

    @Fetch(FetchMode.JOIN)
    @Column var roles: HashSet<Role> = HashSet()
) {
    fun isAdmin(): Boolean {
        return roles.contains(Role.ROLE_ADMIN)
    }

}

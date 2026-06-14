package com.example.beautyclub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.beautyclub.data.local.entity.MemberEntity

@Dao
interface MemberDao {

    @Insert
    suspend fun insertMember(
        member: MemberEntity
    )

    @Update
    suspend fun updateMember(
        member: MemberEntity
    )

    @Query(
        "SELECT * FROM members WHERE id = :memberId"
    )
    suspend fun getMemberById(
        memberId: Int
    ): MemberEntity?

    @Query("""
    SELECT * FROM members
    WHERE email = :email
    AND password = :password
    LIMIT 1
    """)
    suspend fun login(
        email: String,
        password: String
    ): MemberEntity?

}
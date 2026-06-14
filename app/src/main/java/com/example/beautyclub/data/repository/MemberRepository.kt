package com.example.beautyclub.data.repository

import com.example.beautyclub.data.local.dao.MemberDao
import com.example.beautyclub.data.local.entity.MemberEntity

class MemberRepository(
    private val memberDao: MemberDao
) {

    suspend fun addMember(
        member: MemberEntity
    ) {
        memberDao.insertMember(member)
    }

    suspend fun getMember(
        memberId: Int
    ): MemberEntity? {
        return memberDao.getMemberById(memberId)
    }

    suspend fun updateMember(
        member: MemberEntity
    ) {
        memberDao.updateMember(member)
    }

    suspend fun login(
        email: String,
        password: String
    ): MemberEntity? {

        return memberDao.login(
            email,
            password
        )
    }
}
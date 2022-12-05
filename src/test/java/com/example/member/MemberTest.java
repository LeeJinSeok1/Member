package com.example.member;

import com.example.member.dto.MemberDTO;
import com.example.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberTest {
    @Autowired
    MemberService memberService;

    // 회원가입 테스트
    // 신규회원 데이터 생성 (DTO)
    // 회원가입 기능 실행
    // 가입 성공 후 가져온 id 값으로 DB조회를 하고
    // 가입시 사용한 email과 DB에서 조회한 email이 일치하는지를 판단
    @Test
    @Transactional
    @Rollback(value = true)
    public void memberSaveTest() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("testEmail2");
        memberDTO.setMemberPassword("testPassword");
        memberDTO.setMemberName("testName");
        memberDTO.setMemberAge(22);
        memberDTO.setMemberPhone("010-1111-1111");
        Long saveId = memberService.memberSave(memberDTO);

        MemberDTO saveMember = memberService.memberDetail(saveId);

        assertThat(memberDTO.getMemberEmail()).isEqualTo(saveMember.getMemberEmail());


    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("로그인 테스트 ")
    public void loginTest() {
        // 1. 회원가입
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("test1");
        memberDTO.setMemberPassword("testPassword");
        memberDTO.setMemberName("testName");
        memberDTO.setMemberAge(22);
        memberDTO.setMemberPhone("010-1111-1111");
        Long saveId= memberService.memberSave(memberDTO);
        // 2. 로그인 수행
        MemberDTO loginDTO = new MemberDTO();
        loginDTO.setMemberEmail("test1");
        loginDTO.setMemberPassword("testPassword");
        MemberDTO loginResult = memberService.memberLogin(loginDTO);

        // 3. 로그인 결과가 null 이 아니면 테스트 통과
        assertThat(loginResult).isNotNull();
    }
    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("수정 테스트")
    public void updateTest() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("test1");
        memberDTO.setMemberPassword("testPassword");
        memberDTO.setMemberName("testName");
        memberDTO.setMemberAge(22);
        memberDTO.setMemberPhone("010-1111-1111");
        Long saveId= memberService.memberSave(memberDTO);



        MemberDTO updateDTO = new MemberDTO();
        updateDTO.setId(saveId);
        updateDTO.setMemberEmail("asdf");
        updateDTO.setMemberPassword("asdf");
        updateDTO.setMemberName("석진이");
        updateDTO.setMemberAge(2);
        updateDTO.setMemberPhone("01000000000");

            memberService.memberUpdate(updateDTO);
            MemberDTO memberByDB = memberService.memberDetail(saveId);
            assertThat(memberByDB.getMemberName()).isEqualTo(updateDTO.getMemberName());

    }
    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("삭제 테스트")
    public void deleteTest() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("test1");
        memberDTO.setMemberPassword("testPassword");
        memberDTO.setMemberName("testName");
        memberDTO.setMemberAge(22);
        memberDTO.setMemberPhone("010-1111-1111");
        Long saveId= memberService.memberSave(memberDTO);
        //가입해주고

//        MemberDTO member=  memberService.memberDetail(saveId);

        memberService.delete(saveId);
        //삭제해주고

        assertThat(memberService.memberDetail(saveId)).isNull();
        //그 아이딧값의 회원이 없냐!


    }





}

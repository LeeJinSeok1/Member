package com.example.member.service;

import com.example.member.dto.MemberDTO;
import com.example.member.entity.MemberEntity;
import com.example.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public Long memberSave(MemberDTO memberDTO) {
//        memberRepository.save();
      MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
       Long saveId = memberRepository.save(memberEntity).getId();
       return saveId;
    }

    public List<MemberDTO> memberList() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();

        List<MemberDTO> memberDTOList = new ArrayList<>();

        for (MemberEntity memberEntity: memberEntityList){
            MemberDTO memberDTO= MemberDTO.toDTO(memberEntity);
            memberDTOList.add(memberDTO);
        }
        return memberDTOList;

    }

    public MemberDTO memberDetail(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);

        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
            return memberDTO;
        }else{
            return null;
        }
    }

    public MemberDTO memberLogin(MemberDTO memberDTO) {
//        email로 DB에서 조회를 하고
//        사용자가 입력한 비밀번호와 DB에서 조회한 비밀번호가 일치하는지를 판단해서
//        로그인 성공, 실패 여부를 리턴
//        단, email 조회결과가 없을 때도 실패
        Optional<MemberEntity> optionalMemberEntity= memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        //이메일로 디비에 맞는게 있는지 확인하기 위해 레파지에 이메일 컬럼 추가하고 이메일과 맞는게 있는지 optionalMemberEntity로 가져온다
        if(optionalMemberEntity.isPresent()){
            // 만약 그 값이 있다면 아래 실행
            MemberEntity memberEntity = optionalMemberEntity.get();
            // 가져온값을 memberEntity에 담아 주고
            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword()) ){
                //그담은 값의 비밀번호와 입력한 비밀번호가 맞으면 아래 실행
                MemberDTO memberDTO1 = MemberDTO.toDTO(memberEntity);
                // memberEntity의 정보를 memberDTO1 에 담아준다
                //리턴값이 MemberDTO 타입이기 때문에
                return memberDTO1;
                //마지막으로 그 값을 리턴
            }else{
                return null;
            }
        }else{
            return null;
        }

    }

    public MemberDTO findByMemberEmail(String loginEmail) {
        Optional<MemberEntity> optionalMemberEntity= memberRepository.findByMemberEmail(loginEmail);
        if(optionalMemberEntity.isPresent()) {
            return  MemberDTO.toDTO(optionalMemberEntity.get());
        }else{
            return null;
        }
    }


    public void memberUpdate(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toUpdateEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    public MemberDTO emailCk(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity= memberRepository.findByMemberEmail(memberEmail);
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
            return memberDTO;
        }else{
            return null;
        }
    }
}

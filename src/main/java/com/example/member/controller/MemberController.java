package com.example.member.controller;

import com.example.member.dto.MemberDTO;
import com.example.member.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/memberSave")
    public String memberSavePage() {
        return "memberPages/memberSave";
    }

    @PostMapping("/memberSave")
    public String memberSave(@ModelAttribute MemberDTO memberDTO) {
       memberService.memberSave(memberDTO);

        return "memberPages/memberLogin";
    }

    @GetMapping("/memberLogin")
    public String memberLoginPage(@RequestParam(value = "redirectURL", defaultValue = "/mainPage") String redirectURL,
                                  Model model) {
        model.addAttribute("redirectURL",redirectURL);
        return "memberPages/memberLogin";
    }
//    로그인

   // 목록
    @GetMapping("/memberList")
    public String memberList(Model model){
        List<MemberDTO> result = memberService.memberList();
        model.addAttribute("members",result);

        return "memberPages/memberList";
    }

    //상세조회
    @GetMapping("/memberDetail/{id}")
    public String memberDetail(@PathVariable Long id,
                               Model model) {
        MemberDTO result = memberService.memberDetail(id);
        model.addAttribute("member",result);
        return "memberPages/memberDetail";
    }

    @PostMapping("/memberLogin")
    public String memberLogin(@ModelAttribute MemberDTO memberDTO, Model model,
                              @RequestParam(value = "redirectURL", defaultValue = "/mainPage") String redirectURL,
                              HttpSession session) {
        System.out.println(memberDTO);
        MemberDTO result = memberService.memberLogin(memberDTO);
        if(result != null){
            session.setAttribute("loginEmail",memberDTO.getMemberEmail());
            model.addAttribute("loginMember",result);
            //인터셉터에 결려서 로그인한 사용자가 직전에 요청한 페이지로 보내주기 위해서 redirect:/직전요청주소
            //인터셉터 걸리지 않고 로그인을 하는 사용자는 defaultValue에 의해서 main으로
            return "redirect:"+redirectURL;
        }else{
            return "/memberPages/memberLogin";
        }

    }

    @GetMapping("/memberLogout")
    public String memberLogout(HttpSession session) {
        session.invalidate();
        return "memberPages/memberLogin";
    }

    @GetMapping("/mainPage1")
    public String mainPage1(){
        return "mainPage";
    }

    @GetMapping("/memberUpdate")
    public String memberUpdatePage(Model model,HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO= memberService.findByMemberEmail(loginEmail);
        model.addAttribute("member",memberDTO);
        return "memberPages/memberUpdate";
    }

    @PostMapping("/memberUpdate")
    public String memberUpdate(@ModelAttribute MemberDTO memberDTO) {
        memberService.memberUpdate(memberDTO);
        return "mainPage";
    }
    @GetMapping("/memberDelete/{id}")
    public String delete(@PathVariable Long id){
        memberService.delete(id);
        return "redirect:/memberDetail";
    }

    @PostMapping("/emailCk")
    public @ResponseBody String emailCk(@RequestParam("memberEmail") String memberEmail) {
//    public ResponseEntity emailCk(@RequestParam("memberEmail") String memberEmail) {
        MemberDTO memberDTO = memberService.emailCk(memberEmail);
        if(memberDTO != null) {
            return "no";
        }else{
            return "yes";
        }
//        if(memberDTO !=null) {
//            return new ResponseEntity<>("사용할 수 없습니다.", HttpStatus.CONFLICT);
//
//        }else{
//            return new ResponseEntity<>( "사용해도 됩니다.",HttpStatus.OK);
//        }
    }
    @GetMapping("/memberAjax/{id}")
    public ResponseEntity findByIdAjax(@PathVariable Long id){
        System.out.println("id:"+id);
        MemberDTO memberDTO = memberService.memberDetail(id);
        if(memberDTO !=null) {
            return  new ResponseEntity<>(memberDTO, HttpStatus.OK);
        }else{
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    /*
    * get: /member/{id}
    * post: /member/{id}
    * delete: /member/{id}
    * put: /member/{id}
    * */

   @DeleteMapping("/{id}")
    public ResponseEntity deleteMemberAjax(@PathVariable Long id) {
        memberService.delete(id);
       return  new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/member/{id}")
    public ResponseEntity updateAjax(@PathVariable Long id,
                                     @RequestBody MemberDTO memberDTO){
        System.out.println("id = " + id + ", memberDTO = " + memberDTO);
       memberService.memberUpdate(memberDTO);
       return new ResponseEntity<>(HttpStatus.OK);
    }


}

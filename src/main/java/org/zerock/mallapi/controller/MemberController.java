package org.zerock.mallapi.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.mallapi.dto.MemberDTO;
import org.zerock.mallapi.dto.MemberModifyDTO;
import org.zerock.mallapi.service.MemberService;
import org.zerock.mallapi.util.JWTUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@Log4j2
@RequiredArgsConstructor
public class MemberController {
  private final MemberService memberService;

  @PostMapping("/api/member/join")
  public Map<String, String> register(@RequestBody MemberDTO memberDTO) {
    log.info("MemberDTO: " + memberDTO);
    String email = memberService.register(memberDTO);

    return Map.of("email", email);
  }

  @GetMapping("/api/member/kakao")
  public Map<String, Object> getMemberFromKakao(String accessToken) {
    log.info("access Token ");
    log.info(accessToken);

    MemberDTO memberDTO = memberService.getKakaoMember(accessToken);

    Map<String, Object> claims = memberDTO.getClaims();

    String jwtAccessToken = JWTUtil.generateToken(claims, 10);
    String jwtRefreshToken = JWTUtil.generateToken(claims, 60 * 24);

    claims.put("accessToken", jwtAccessToken);
    claims.put("refreshToken", jwtRefreshToken);

    return claims;
  }

  @PutMapping("/api/member/modify")
  public Map<String, String> modify(@RequestBody MemberModifyDTO memberModifyDTO) {
    log.info("member modify: " + memberModifyDTO);

    memberService.modifyMember(memberModifyDTO);
    return Map.of("result", "modified");
  }
}

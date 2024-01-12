package org.zerock.mallapi.util;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;

public class JWTUtil {
  private static String key = "JVymOOOY3JGJgzPNluBW0BxD2bVHtFNluBW0BxD2bVHtFBm80BUU1LfQ7axb";

  public static String generateToken(Map<String, Object> valueMap, int min) {
    SecretKey key = null;

    try {
      key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
    String jwtStr = Jwts.builder().setHeader(Map.of("type", "JWT"))
        .setClaims(valueMap)
        .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
        .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
        .signWith(key).compact();

    return jwtStr;
  }

  public static Map<String, Object> validateToken(String token) {
    Map<String, Object> claim = null;

    try {
      SecretKey key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
      claim = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody(); // 파싱 및 검증, 실패 시 에러
    } catch (MalformedJwtException e) {
      throw new CustomJWTException("MalFormed");
    } catch (ExpiredJwtException e) {
      throw new CustomJWTException("Expired");
    } catch (InvalidClaimException e) {
      throw new CustomJWTException("Invalid");
    } catch (JwtException e) {
      throw new CustomJWTException("JWTError");
    } catch (Exception e) {
      throw new CustomJWTException("Error");
    }
    return claim;
  }
}

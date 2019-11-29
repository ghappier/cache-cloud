package com.lizq.cache.commons.utils;

import com.lizq.cache.commons.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
@PropertySource("classpath:jwt-${spring.profiles.active}.properties")
public class JwtUtils {
    /**
     * 密钥
     */
    private String secret;
    /**
     * 有效期限
     */
    private int expire;
    /**
     * 存储 token
     */
    private String header;

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public Claims parseJWT(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token).getBody();

            return claims;
        } catch (Exception ex) {
            return null;
        }
    }

    public JwtUser parseUser(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token).getBody();
            JwtUser user = new JwtUser();
            user.setId(claims.getSubject());
            user.setName(claims.get("name", String.class));
            user.setNickname(claims.get("nickname", String.class));
            user.setAvatar(claims.get("avatar", String.class));
            return user;
        } catch (Exception ex) {
            return null;
        }
    }

    public JwtUser getUserFromRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader("token");//header方式
        if (StringUtils.isBlank(token)) {
            throw new ServiceException("获取token失败");
        }
        return parseUser(token);
    }

    /**
     * 生成jwt token
     *
     * @param user 用户
     * @return token
     */
    public String createJWT(JwtUser user) {
        Date currDate = new Date();
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setSubject(user.getId() + "")
                .claim("name", user.getName())
                .claim("nickname", user.getNickname())
                .claim("avatar", user.getAvatar())
                .setIssuedAt(currDate)
                .setExpiration(DateUtils.addDays(currDate, expire))
                .signWith(SignatureAlgorithm.HS512, secret);

        return builder.compact();
    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}

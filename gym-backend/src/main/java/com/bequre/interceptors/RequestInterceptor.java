package com.bequre.interceptors;

import com.bequre.utils.JwtUtil;
import com.bequre.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RequestInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;

    @Override        //在每次请求前执行该方法的逻辑
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        //从请求中获得token
        String token = request.getHeader("Authorization");
        //验证token
        try {
            //从Redis中获取token
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(token);

            if (redisToken == null){    //说明redis中没找到对应的token
                throw new RuntimeException();   //直接抛异常，执行catch里的代码
            }
            //能往下执行，说明在redis中找到了对应的token
            //解析token
            Map<String, Object> claims = JwtUtil.parseToken(token);
            //将请求头中携带的用户数据 存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
            //放行
            return true;

        }catch (Exception e){
            //设置状态码401
            response.setStatus(401);
            //进行拦截
            return false;
        }

    }

    @Override       //在每次请求完成后执行该方法的逻辑
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        //在每次请求完成之后，就不再需要用ThreadLocal的数据了。
        //此时应当清除，否则易导致内存泄露
        ThreadLocalUtil.remove();
    }

}

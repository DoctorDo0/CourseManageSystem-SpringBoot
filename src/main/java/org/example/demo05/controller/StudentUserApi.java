package org.example.demo05.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo05.entity.Student;
import org.example.demo05.entity.StudentAccount;
import org.example.demo05.service.StudentUserService;
import org.example.demo05.utils.JsonResp;
import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/Student-users", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentUserApi {
    private static final PasswordEncryptor pe = new StrongPasswordEncryptor();
    private RedisTemplate<Object, Object> redisTemplate;
    private StudentUserService studentUserService;
    //表示从配置文件中取配置项的值
    @Value("${jwt.secret}")
    private String secret;


    @Autowired
    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setUserService(StudentUserService studentUserService) {
        this.studentUserService = studentUserService;
    }

    //登录
    @PostMapping("/login")
    public JsonResp login(@RequestBody @Validated StudentAccount studentAccount, HttpServletResponse resp) {

        Student student = this.studentUserService.findByStudentId(studentAccount.getUsername());
        if (student == null) {
            return JsonResp.error("用户名或密码错误");
        }

        boolean success = pe.checkPassword(studentAccount.getPassword(), student.getStudentPassword());

        if (success) {
            //密码正确，颁发令牌
            String jwt = JWT.create()
                    .withAudience(studentAccount.getUsername())
                    .withExpiresAt(Instant.now().plusSeconds(1800))
                    .withSubject("login jwt")
                    .withIssuer("中享思途")
                    .withIssuedAt(Instant.now())
                    .withJWTId(UUID.randomUUID().toString())
                    .withClaim("username", studentAccount.getUsername())
                    .sign(Algorithm.HMAC256(secret));

            //将jwt放到响应头中
            resp.setHeader("x-auth-token", jwt);

            Map<String, Object> data = new HashMap<>();
            data.put("token", jwt);
            data.put("studentId", student.getStudentId());
            data.put("name", student.getName());

//            return JsonResp.success(jwt);
            return JsonResp.success(data);
        } else {
            return JsonResp.error("用户名或密码错误");
        }
    }


    @PostMapping
    public JsonResp save(@RequestBody Student student) {
        boolean success = this.studentUserService.save(student);
        if (success) {
            return JsonResp.success(student);
        } else {
            return JsonResp.error("保存用户失败");
        }
    }

    @PutMapping
    public JsonResp update(@RequestBody Student student) {
        boolean success = this.studentUserService.updateById(student);
        if (success) {
            return JsonResp.success(student);
        } else {
            return JsonResp.error("修改用户失败");
        }
    }

    @DeleteMapping
    public JsonResp deleteByIds(@RequestBody Integer[] ids) {
        boolean success = this.studentUserService.removeByIds(List.of(ids));
        if (success) {
            return JsonResp.success("删除操作成功");
        } else {
            return JsonResp.error("删除操作失败");
        }
    }

    /**
     * 用户登出（前端清除Token即可，后端无操作）
     */
    @PostMapping("/logout")
    public ResponseEntity<JsonResp> logout() {
        return ResponseEntity.ok(JsonResp.success("登出成功"));
    }

}

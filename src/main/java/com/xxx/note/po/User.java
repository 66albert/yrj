package com.xxx.note.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ：刘彬
 * @date ：Created in 2023/2/26 17:10
 * @description：用户对象
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer userId; // 用户ID
    private String uname;   // 用户姓名
    private String upwd;    // 用户密码
    private String nick;    // 用户昵称
    private String head;    // 用户头像
    private String mood;    // 用户签名
}

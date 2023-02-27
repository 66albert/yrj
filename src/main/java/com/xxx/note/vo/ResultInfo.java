package com.xxx.note.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ：刘彬
 * @date ：Created in 2023/2/26 17:20
 * @description：封装返回结果的类
 *              状态码
 *                  成功=1，失败=0
 *              提示信息
 *              返回对象（Object）
 */
@Getter
@Setter
public class ResultInfo<T> {
    private Integer code;   // 状态码 成功=1，失败=0
    private String msg;     // 提示信息
    private T result;       // 返回对象
}

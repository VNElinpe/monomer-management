package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.vnelinpe.management.constant.ResultCode;

/**
 * 返回数据的统一模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/12 10:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("统一返回模型")
public class ResultVO<T> {
    // 返回代码
    @ApiModelProperty(value = "操作结果状态码")
    private Integer code;

    // 返回代码的含义
    @ApiModelProperty(value = "操作结果状态提示")
    private String msg;

    // 具体的返回数据
    @ApiModelProperty(value = "返回的具体数据")
    private T data;

    // token
    @ApiModelProperty(value = "token令牌")
    private String token;

    public static <T> ResultVO<T> success() {
        return getResultDTO(ResultCode.SUCCESS, null, null);
    }

    public static <T> ResultVO<T> success(T data) {
        return getResultDTO(ResultCode.SUCCESS, data, null);
    }

    public static <T> ResultVO<T> fail() {
        return getResultDTO(ResultCode.FAIL, null, null);
    }

    public static <T> ResultVO<T> fail(ResultCode resultCode) {
        return getResultDTO(resultCode, null, null);
    }

    public static <T> ResultVO<T> fail(ResultCode resultCode, String msg) {
        ResultVO fail = fail(resultCode);
        fail.setMsg(msg);
        return fail;
    }

    public static <T> ResultVO<T> getResultDTO(ResultCode resultCode, T data, String token) {
        return new ResultVO<>(resultCode.getCode(), resultCode.getMsg(), data, null);
    }

}

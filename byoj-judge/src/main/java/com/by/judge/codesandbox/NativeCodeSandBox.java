package com.by.judge.codesandbox;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.by.common.constant.CommonConstants;
import com.by.model.entity.ExecuteCodeRequest;
import com.by.model.entity.ExecuteCodeResponse;
import com.google.gson.Gson;

/**
 * 本地代码沙箱
 *
 * @author lzh
 */
public class NativeCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        // JSON转换
        Gson gson = new Gson();
        String requestJson = gson.toJson(executeCodeRequest);

        // 构建请求
        HttpRequest httpRequest = HttpUtil.createPost("http://localhost:9000/codesandbox/execute")
                .header(CommonConstants.AUTH_REQUEST_HEADER, CommonConstants.AUTH_REQUEST_SECRET)
                .body(requestJson, "application/json");

        // 发送请求获取返回值
        String body = httpRequest.execute().body();
        return gson.fromJson(body, ExecuteCodeResponse.class);
    }
}

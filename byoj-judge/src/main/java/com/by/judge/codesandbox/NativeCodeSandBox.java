package com.by.judge.codesandbox;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.by.common.constant.CommonConstants;
import com.by.model.entity.ExecuteCodeRequest;
import com.by.model.entity.ExecuteCodeResponse;

/**
 * 本地代码沙箱
 *
 * @author lzh
 */
public class NativeCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        // JSON转换
        String url = "http://localhost:9000/codesandbox/execute";
        String jsonStr = JSONUtil.toJsonStr(executeCodeRequest);

        // 构建请求
        HttpRequest httpRequest = HttpUtil.createPost(url)
                .header(CommonConstants.AUTH_REQUEST_HEADER, CommonConstants.AUTH_REQUEST_SECRET)
                .body(jsonStr);

        // 发送请求获取返回值
        String body = httpRequest.execute().body();
        return JSONUtil.toBean(body, ExecuteCodeResponse.class);
    }
}

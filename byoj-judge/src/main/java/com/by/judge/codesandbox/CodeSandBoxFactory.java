package com.by.judge.codesandbox;

/**
 * 代码沙箱工厂
 *
 * @author lzh
 */
public class CodeSandBoxFactory {

    public static CodeSandBox newInstance(String type) {
        switch (type) {
            case "remote":
                return new RemoteCodeSandBox();
            case "native":
            default:
                return new NativeCodeSandBox();
        }
    }
}

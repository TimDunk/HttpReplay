package com.c3stones.utils;

public enum ComparedResult {
    STR_EQUALS( "字符串比对相等"),
    STR_NOT_EQUALS("字符串比对不相等"),
    NULL_OBJECT( "响应结果为null对象"),
    NOT_JSON("字符串比对不相等，也无法转为JSON进行比对"),
    JSON_EQUALS("Json对比相等"),
    DIFF_NOT_CERTAIN( "JsonDiff 结果不明确，请人工对比"),
    DIFF_EXCEPTION("JsonDiff 出现异常，请人工对比"),
    JSON_MOVE("Json数组内元素有移动"),
    JSON_LAST_EQUALS("后者去掉tenantId,shopId,createId再进行Json对比,结果：相等"),
    JSON_LAST_NOT_EQUALS("后者去掉tenantId,shopId,createId再进行Json对比,结果：不相等");

    private final String message;

    private ComparedResult(String chinese) {
        this.message = chinese;
    }

    @Override
    public String toString() {
        return this.message;
    }
}

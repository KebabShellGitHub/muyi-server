package cn.kebabshell.muyi.handler.result;

public enum MyMsg {
    SUCCESS("成功"),
    ;

    private String msg;

    MyMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

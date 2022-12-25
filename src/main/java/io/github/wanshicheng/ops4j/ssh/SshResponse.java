package io.github.wanshicheng.ops4j.ssh;

public class SshResponse {
    private String out;
    private String err;

    private int exitStatus;

    public SshResponse(String out, String err, int exitStatus) {
        this.out = out;
        this.err = err;
        this.exitStatus = exitStatus;
    }

    public boolean isOk() {
        return 0 == exitStatus;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }
}

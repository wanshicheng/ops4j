package io.github.wanshicheng.ops4j.util;

import io.github.wanshicheng.ops4j.ssh.SshResponse;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelExec;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.future.AuthFuture;
import org.apache.sshd.client.session.ClientSession;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.EnumSet;
import java.util.Set;

public class SshUtil {
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(60);

    public static ClientSession createSession(String host, int port, String username, String password) {
        SshClient sshClient = SshClient.setUpDefaultClient();
        sshClient.start();
        ClientSession session = null;
        try {
            session = sshClient.connect(username, host, port).verify(DEFAULT_TIMEOUT).getClientSession();
            session.addPasswordIdentity(password);
            AuthFuture auth = session.auth().verify(DEFAULT_TIMEOUT);
            if (auth.isFailure()) {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
        return session;
    }

    public void closeSession(ClientSession session) {
        try {
            if (null != session) {
                session.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static SshResponse exec(ClientSession session, String statement) {
        ChannelExec channel = null;
        ByteArrayOutputStream out = null;
        ByteArrayOutputStream err = null;
        try {
            channel = session.createExecChannel(statement);
            out = new ByteArrayOutputStream();
            err = new ByteArrayOutputStream();
            channel.setOut(out);
            channel.setErr(err);
            channel.open().await(Duration.ofSeconds(10));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), Duration.ofSeconds(10));
        try {
            channel.close();
        } catch (IOException ignored) {

        }
        byte[] outBytes = out.toByteArray();
        byte[] errBytes = err.toByteArray();

        return new SshResponse(new String(outBytes, StandardCharsets.UTF_8),
                new String(errBytes, StandardCharsets.UTF_8),
                channel.getExitStatus());

    }
}

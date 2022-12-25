package io.github.wanshicheng.ops4j.util;

import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.scp.client.ScpClient;
import org.apache.sshd.scp.client.ScpClientCreator;

import java.io.IOException;

public class ScpUtil {
    public static void upload(ClientSession session, String local, String remote) throws IOException {
        ScpClient client = ScpClientCreator.instance().createScpClient(session);
        client.upload(local, remote, ScpClient.Option.Recursive);
    }

    public static void download(ClientSession session, String local, String remote) throws IOException {
        ScpClient client = ScpClientCreator.instance().createScpClient(session);
        client.download(remote, local, ScpClient.Option.Recursive);
    }
}

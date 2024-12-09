package de.awtools.git.command;

import java.io.File;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.ssh.jsch.JschConfigSessionFactory;
import org.eclipse.jgit.util.FS;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;

public class GitCommand {

    public void clone(final String localPath, final String remotePath) {
        /*
        SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
        	@Override
        	protected JSch createDefaultJSch(final FS fs) throws JSchException {
        		JSch defaultJSch = super.createDefaultJSch(fs);
        		defaultJSch.removeAllIdentity();
        		defaultJSch.addIdentity("~/doc/privatekey.ppk");
        		return defaultJSch;
        	}
        
        	@Override
        	protected void configure(final Host hc, final Session session) {
        		session.setPassword(password);
        		session.setConfig("StrictHostKeyChecking", "no");
        	};
        };
         */

        CloneCommand cloneCommand = Git.cloneRepository();
        cloneCommand.setURI(remotePath);
        cloneCommand.setTransportConfigCallback(new SshTransportConfigCallback());

        cloneCommand.setDirectory(new File(localPath));
        try {
            cloneCommand.call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    public void pull(final String localPath) {
        try {
            Repository localRepo = new FileRepository(localPath + "/.git");
            Git git = new Git(localRepo);
            git.pull().setTransportConfigCallback(new SshTransportConfigCallback()).call();
            git.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static class SshTransportConfigCallback implements TransportConfigCallback {

        @Override
        public void configure(final Transport transport) {
            SshTransport sshTransport = (SshTransport) transport;
            sshTransport.setSshSessionFactory(sshSessionFactory);
        }

        private final SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {

            @Override
            protected JSch createDefaultJSch(FS fs) throws JSchException {
                JSch defaultJSch = super.createDefaultJSch(fs);
                defaultJSch.removeAllIdentity();
                defaultJSch.addIdentity("~/.ssh/id_rsa_github");
                return defaultJSch;
            }
        };
    }
}
package com.example;

import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.List;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.OpenSSHConfig;
import com.jcraft.jsch.Session;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.Transport;
// import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.ssh.jsch.JschConfigSessionFactory;
import org.eclipse.jgit.transport.ssh.jsch.OpenSshConfig.Host;
import org.eclipse.jgit.util.FS;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
		Main main = new Main();
			// "E:\\development\\projects\\tmp",
			// "git@github.com:gluehloch/java-examples.wiki.git"
			 // "https://github.com/gluehloch/java-examples.wiki.git"
			main.clone( "E:\\development\\projects\\tmp\\wiki.git", "git@github.com:gluehloch/java-examples.wiki.git");
    }

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
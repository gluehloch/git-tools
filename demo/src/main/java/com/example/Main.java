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
import org.eclipse.jgit.transport.*;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.util.FS;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

	public Main(final String localPath, final String remotePath) {	}

	public void loadRepo(final String localPath, final String remotePath, final String password) {
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

		CloneCommand cloneCommand = Git.cloneRepository();
		cloneCommand.setURI(remotePath);
		cloneCommand.setTransportConfigCallback(new TransportConfigCallback() {
			@Override
			public void configure(final Transport transport) {
				SshTransport sshTransport = (SshTransport) transport;
				sshTransport.setSshSessionFactory(sshSessionFactory);
			}
		});

		cloneCommand.setDirectory(new File(localPath));
		try {
			cloneCommand.call();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}

	private static class SshTransportConfigCallback implements TransportConfigCallback {
		private final SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
			@Override
			protected void configure(OpenSSHConfig.Host hc, Session session) {
				session.setConfig("StrictHostKeyChecking", "no");
			}

			@Override
			protected JSch createDefaultJSch(FS fs) throws JSchException {
				JSch jSch = super.createDefaultJSch(fs);
				byte[] privateKeyPEM = null;

				try {
					KeyFactory keyFactory = KeyFactory.getInstance("RSA");

					List<String> lines = Files.readAllLines(Paths.get("<my_key>.pem"), StandardCharsets.US_ASCII);
					PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(
							Base64.getDecoder().decode(String.join("", lines)));
					RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(privSpec);

					PKCS8Generator pkcs8 = new PKCS8Generator(privKey);

					StringWriter writer = new StringWriter();
					PemWriter pemWriter = new PemWriter(writer);
					pemWriter.writeObject(pkcs8);

					privateKeyPEM = writer.toString().getBytes("US-ASCII");

				} catch (Exception e) {
					e.printStackTrace();
				}

				jSch.addIdentity("git", privateKeyPEM, null, null);

				return jSch;
			}
		};
	}
}
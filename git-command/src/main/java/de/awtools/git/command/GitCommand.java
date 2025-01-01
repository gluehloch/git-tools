package de.awtools.git.command;

import java.io.File;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.ssh.jsch.JschConfigSessionFactory;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.util.FS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;

@Component
public class GitCommand {

    private static final Logger LOG = LoggerFactory.getLogger(GitCommand.class);

    private final GitRepositoryConfiguration gitRepositoryConfiguration;

    public GitCommand(GitRepositoryConfiguration gitRepositoryConfiguration) {
        this.gitRepositoryConfiguration = gitRepositoryConfiguration;
    }

    public void clone(final String localPath, final String remotePath) {
        CloneCommand cloneCommand = Git.cloneRepository();
        cloneCommand.setURI(remotePath);
        cloneCommand.setTransportConfigCallback(new SshTransportConfigCallback());
        cloneCommand.setDirectory(new File(localPath));

        try (Git git = cloneCommand.call()) {
            cloneCommand.call();
        } catch (GitAPIException ex) {
            LOG.error("Error cloning repository", ex);
            throw new RuntimeException(ex);
        }
    }

    public void pull() {
        try (Git git = Git.open(gitRepositoryConfiguration.getRepositoryPath().toFile())) {
            git.pull().setTransportConfigCallback(new SshTransportConfigCallback()).call();
        } catch (Exception ex) {
            LOG.error("Error pulling repository", ex);
            throw new RuntimeException(ex);
        }
    }

    public static void treeFinder(final Repository repository) throws Exception {
        // find the HEAD
        ObjectId lastCommitId = repository.resolve(Constants.HEAD);

        // a RevWalk allows to walk over commits based on some filtering that is defined
        try (RevWalk revWalk = new RevWalk(repository)) {
            RevCommit commit = revWalk.parseCommit(lastCommitId);
            // and using commit's tree find the path
            RevTree tree = commit.getTree();
            System.out.println("Having tree: " + tree);

            // now try to find a specific file
            try (TreeWalk treeWalk = new TreeWalk(repository)) {
                treeWalk.addTree(tree);
                treeWalk.setRecursive(true);
                treeWalk.setFilter(PathFilter.create("README.md"));
                if (!treeWalk.next()) {
                    throw new IllegalStateException("Did not find expected file 'README.md'");
                }

                ObjectId objectId = treeWalk.getObjectId(0);
                ObjectLoader loader = repository.open(objectId);

                // and then one can the loader to read the file
                loader.copyTo(System.out);
            }

            revWalk.dispose();
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
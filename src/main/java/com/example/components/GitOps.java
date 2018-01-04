package com.example.components;

import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

@JSComponent(name="git")
public class GitOps{

    private Git git;

    private String username = "";
    private String password = "";

    @JSRunnable
    public void login(String username, String password) throws GitAPIException {
        this.username = username;
        this.password = password;
    }

    @JSRunnable
    public void clone(String dir, String g) throws GitAPIException {
        git = Git.cloneRepository().setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password)).setURI(g).setDirectory(new File(dir)).call();
    }

    @JSRunnable
    public void setDirectory(String dir) throws GitAPIException, IOException {
        git = Git.open(new File(dir));
    }

    @JSRunnable
    public void checkout(String branch) throws GitAPIException {
        git.checkout().addPath(branch).call();
    }

    @JSRunnable
    public void status() throws GitAPIException {
        for(String s: git.status().call().getAdded())
            System.out.println("-added-> " + s);
        for(String s: git.status().call().getChanged())
            System.out.println("-changed-> " + s);
        for(String s: git.status().call().getUncommittedChanges())
            System.out.println("-uncommitted-> " + s);
        for(String s: git.status().call().getRemoved())
            System.out.println("-removed-> " + s);
        for(String s: git.status().call().getModified())
            System.out.println("-modified-> " + s);
    }

    @JSRunnable
    public void reset() throws GitAPIException {
        git.reset().call();
    }

    @JSRunnable
    public void rm(String fname) throws GitAPIException {
        git.rm().addFilepattern(fname).call();
    }

    @JSRunnable
    public void add(String fname) throws GitAPIException {
        git.add().addFilepattern(fname).call();
    }

    @JSRunnable
    public void commit(String comment) throws GitAPIException {
        git.commit().setMessage(comment).call();
    }

    @JSRunnable
    public void push() throws GitAPIException {
        git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password)).call();//.setRemote(origin).call();
    }

    @JSRunnable
    public void pull(String origin) throws GitAPIException {
        git.pull().setRemote(origin).setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password)).call();
    }

}

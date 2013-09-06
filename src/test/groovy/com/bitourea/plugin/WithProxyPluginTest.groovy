package com.bitourea.plugin

import org.junit.Test
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project

import static junit.framework.Assert.assertNotNull
import static junit.framework.Assert.assertTrue
import static junit.framework.Assert.assertEquals
import static org.junit.Assert.assertNull

class WithProxyPluginTest {
    @Test
    void testWithProxyTaskAdded(){
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'withproxy'
        assertNotNull(project.tasks.withProxy)
    }

    @Test
    void testProxyAuthenticatorAddedForRequestorTypePROXY(){
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'withproxy'
        System.setProperty("http.proxyHost", "localhost")
        System.setProperty("http.proxyPort", "80")
        System.setProperty("http.proxyUser", "user1")
        System.setProperty("http.proxyPass", "password")

        project.tasks.withProxy.execute()

        final authentication = Authenticator.requestPasswordAuthentication("localhost", InetAddress.getLocalHost(), 80, "http", "TestRealm", "BASIC", null, Authenticator.RequestorType.PROXY)
        assertNotNull(authentication)
        assertEquals "user1", authentication.getUserName()
        assertEquals "password", new String(authentication.getPassword())
    }

    @Test
    void testProxyAuthenticatorFailsForRequestorTypeNotPROXY(){
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'withproxy'
        System.setProperty("http.proxyHost", "localhost")
        System.setProperty("http.proxyPort", "80")
        System.setProperty("http.proxyUser", "user1")
        System.setProperty("http.proxyPass", "password")

        project.tasks.withProxy.execute()

        final authentication = Authenticator.requestPasswordAuthentication("localhost", InetAddress.getLocalHost(), 80, "http", "TestRealm", "BASIC", null, Authenticator.RequestorType.SERVER)
        assertNull(authentication)
    }
}

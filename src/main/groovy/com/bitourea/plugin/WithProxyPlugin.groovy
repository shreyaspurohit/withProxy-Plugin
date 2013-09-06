package com.bitourea.plugin

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.logging.LogLevel

/**
 * The plugin sets the JDK Authenticator to use the provided system configuration.
 * This plugin is useful when a certain other plugin's task tries to access URL behind a
 * proxy network and does not support proxy Authentication.
 *
 * To use the plugin,
 * apply plugin: 'withproxy'
 *
 * Add dependency,
 *
   dependencies {
         classpath group: 'com.bitourea',
                   name: 'withproxy',
                   version: '1.0'
   }
 */
class WithProxyPlugin implements Plugin<Project>{
    @Override
    void apply(Project project) {
        project.logging.captureStandardOutput LogLevel.INFO

        project.task('withProxy') << {
            Authenticator.setDefault(new ProxyAuthenticator());
        }
    }
}

WithProxy Gradle Plugin
=======================
The plugin sets the JDK Authenticator to use the provided system configuration.
This plugin is useful when a certain other plugin's task tries to access URL behind a proxy network and
does not support proxy Authentication. I encountered this problem when the other plugin, lets say "B", was downloading
a binary zip from an external URL but was unable to as it was not using the proxy settings when executing its
tasks. Specifically, I needed a command line option so that the continuous integration system (CI) can invoke the gradle task
from plugin B and make it use the set proxy configuration using -D options on web console of CI.

The plugin adds a new task "withProxy".

Usage
-----
To use the plugin,

    apply plugin: 'withproxy'

Add dependency,

    dependencies {
         classpath group: 'com.bitourea',
                   name: 'withproxy',
                   version: '1.0'
    }

Add maven repo URL,

    repositories {
        mavenRepo urls: 'https://github.com/shreyaspurohit/mvn-repo/raw/master/releases'
    }

Settings
--------
The following properties must be set:

    "http.proxyHost"
    "http.proxyPort"
    "http.proxyUser"
    "http.proxyPass"

The properties can be set either using -D option in command line while executing the withProxy task or in a separate task that uses System.setProperty

Invocation
----------
The plugin adds a new withProxy task that will configure next tasks in line to use proxy settings that was set using the above properties. Invoke the plugin as below.

    gradle withProxy <another_task_1> <another_task_2> -Dhttp.proxyHost=proxyurl -Dhttp.proxyPort=80 -Dhttp.proxyUser=user -Dhttp.proxyPass=password

This will invoke the withProxy task that will do the necessary setup to user Proxy settings provided with -D options when <another_task_1> and <another_task_2> from
another plugin is invoked. If you are using windows NTLM LDAP access then provide the domain in proxyUser, for eg., -Dhttp.proxyUser=DOMAIN\user.

Dependencies
------------
The plugin has been tested with Gradle 1.4 and 1.7.

Build/Publish Jar
-----------------

	gradle uploadArchives
	
	
Licensing
---------
Released under MIT license, go ahead and use, modify, distribute as you wish. The license is provided in LICENSE.txt. The license of other libraries used must be used as defined by them.

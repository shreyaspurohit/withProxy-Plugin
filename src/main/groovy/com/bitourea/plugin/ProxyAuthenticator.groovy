package com.bitourea.plugin
/**
 * The Authenticator implementation that reads the set system properties:
         "http.proxyHost"
         "http.proxyPort"
         "http.proxyUser"
         "http.proxyPass"
  The properties can be set either using -D option in command line while executing the withProxy task or
 in a separate task that uses System.setProperty
 */
class ProxyAuthenticator extends Authenticator{
    def withAuthProperties = { closure ->
        def protocol = getRequestingProtocol().toLowerCase()
        def propertyWithProtocol = { prop ->
            System.getProperty(protocol + "." + prop, "")
        }

        closure(protocol, propertyWithProtocol("proxyHost"),
                propertyWithProtocol("proxyPort"), propertyWithProtocol("proxyUser"),
                propertyWithProtocol("proxyPass"))
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        if (getRequestorType() != Authenticator.RequestorType.PROXY) {
            return null
        }
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        withAuthProperties { protocol, host, port, user, password ->
            if (getRequestingHost().toLowerCase().equals(host.toLowerCase())) {
                if (Integer.parseInt(port) == getRequestingPort()) {
                    println "Using Proxy: ${protocol + "://" + user + ":" + "xxxxxx" + "@" + host + ":" + port + "/"}"
                    return new PasswordAuthentication(user, password.toCharArray())
                }
            }
        }
    }
}

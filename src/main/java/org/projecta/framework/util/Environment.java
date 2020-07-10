package org.projecta.framework.util;

public enum Environment {
    WEB_URL("web.url"),
    WEB_BROWSER("web.browser"),
    WEB_DEFAULT_TIMEOUT("web.defaultTimeout"),
    GITHUB_URL("github.base.url"),
    REPOSITORY_ENDPOINT("repos.endpoint"),
    WEB_DATA_DIR("web.dataDir");

    private final String key;

    Environment(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}

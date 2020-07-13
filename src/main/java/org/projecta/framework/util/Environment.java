package org.projecta.framework.util;

public enum Environment {
    WEB_URL("web.url"),
    WEB_BROWSER("web.browser"),
    CHROME_VERSION("web.chrome.version"),
    FIREFOX_VERSION("web.firefox.version"),
    WEB_DEFAULT_TIMEOUT("web.defaultTimeout"),
    GITHUB_URL("github.base.url"),
    REPOSITORY_ENDPOINT("repos.endpoint"),
    WEB_IS_GRID_ENABLED("web.isGridEnabled"),
    WEB_SELENIUM_GRID("web.seleniumGrid");

    private final String key;

    Environment(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}

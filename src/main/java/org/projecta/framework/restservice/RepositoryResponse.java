package org.projecta.framework.restservice;

import com.google.gson.annotations.Expose;

/**
 * POJO for de-serializing Rest response
 */
public class RepositoryResponse {
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getHtml_url() {
        return html_url;
    }

    @Expose(deserialize = true)
    public String name;

    @Expose(deserialize = true)
    public String description;

    @Expose(deserialize = true)
    public String html_url;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }
}

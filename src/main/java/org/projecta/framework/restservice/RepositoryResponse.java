package org.projecta.framework.restservice;

import com.google.gson.annotations.Expose;

public class RepositoryResponse {
    @Expose(deserialize = true)
    public String name;

    @Expose(deserialize = true)
    public String description;

    @Expose(deserialize = true)
    public String html_url;
}

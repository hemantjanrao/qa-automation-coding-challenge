package org.projecta.framework.restservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.List;

public class TestHelper {

    /**
     * Method to de-serialize the Rest response object to specific POJO object
     *
     * @param res Rest response object
     * @return List<RepositoryResponse>
     */
    public static List<RepositoryResponse> deserializeJson(Response res) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        JSONArray obj = (JSONArray) JSONValue.parse(res.body().asString());
        List<RepositoryResponse> responseList = new ArrayList();

        for (int i = 0; i <= obj.size() - 1; i++) {
            RepositoryResponse rp = gson.fromJson(obj.get(i).toString(), RepositoryResponse.class);
            responseList.add(rp);
        }

        return responseList;
    }
}

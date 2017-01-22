package io.pello.android.androidsyncadapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 * Uses WebRequest to make reguests to the backend
 * Created by PELLO_ALTADILL on 22/01/2017.
 */
public class BackendAccess {
    private WebRequest webRequest;
    private static final String GET_LAST_URL = "http://localhost/2dam-project-multisite/web/app_dev.php/admin/api/task/";
    private static final String CREATE_URL = "";

    public BackendAccess () {
        webRequest = new WebRequest();
    }

    /**
     * getLast tasks from backend, parse JSON
     * @param idBackend
     * @return List of tasks
     */
    public List<Task> getLast (int idBackend) {
        List<Task> tasks = new ArrayList<Task>();
        DateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        if (webRequest.get(GET_LAST_URL+idBackend)) {
            System.out.println("OK Total: " + webRequest.getResponseString());
        } else {
            System.err.println("Error: " + webRequest.getExceptionMessage());
        }

        try {
            //JSONObject jsonResponse = new JSONObject(webRequest.getResponseString());
            JSONArray jsonTasks = new JSONArray(webRequest.getResponseString());
            for (int i = 0; i < jsonTasks.length(); i++) {
                Task task = new Task();
                System.out.println(jsonTasks.getJSONObject(i).getString("task"));
                task.setBackendId(jsonTasks.getJSONObject(i).getInt("id"));
                task.setTask(jsonTasks.getJSONObject(i).getString("task"));
                task.setLastUpdate(isoFormat.parse(jsonTasks.getJSONObject(i).getString("last_update")));

                tasks.add(task);
            }
        } catch (Exception e) {
            System.err.println("Exception parsing data: " + e.getMessage());
        } finally {
            return tasks;
        }

    }

    /**
     * inserts a new Task in backend
     * @param task
     * @return
     */
    public Integer insertTasks (Task task) {

        String json = "{\"task\":{\"id\":1,\"task\":\""+task.getTask()+"\",\"id_frontend\":\""+task.getId()+"\",\"latitude\":6,\"longitude\":1,\"open\":1}}";
        try {
            if (webRequest.postJson(CREATE_URL, json)) {
                System.out.println("OK POST: " + webRequest.getResponseString() + "\n" + webRequest.getResponseCode());
            } else {
                System.err.println("Error: " + webRequest.getExceptionMessage() + "\n" + webRequest.getResponseCode());
            }
        } catch (Exception e) {
            System.err.println("Error inserting Task: " + e.getMessage());
        }
        return 0;
    }
}

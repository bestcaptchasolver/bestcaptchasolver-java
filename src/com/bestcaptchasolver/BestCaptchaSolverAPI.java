package com.bestcaptchasolver;

import org.json.JSONObject;

import java.io.File;

public class BestCaptchaSolverAPI {
    private static String BASE_URL = "https://bcsapi.xyz/api";

    private String _access_token;

    public BestCaptchaSolverAPI(String access_token){
        this._access_token = access_token;
    }

    /**
     * Get account balance
     * @return
     * @throws Exception
     */
    public String account_balance() throws Exception{
        String url = String.format("%s/user/balance?access_token=%s", this.BASE_URL, this._access_token);
        return String.format("$%s", Utils.GET(url).getString("balance"));
    }

    /**
     * Submit image path (without case sensitivity)
     * @param image_path
     * @return
     */
    public int submit_image_captcha(String image_path) throws Exception{
        return this.submit_image_captcha(image_path, false);
    }

    /**
     * Submit image path (with case sensitivity param)
     * @param absolute_path
     * @param case_sensitive
     * @return
     */
    public int submit_image_captcha(String absolute_path, Boolean case_sensitive) throws Exception{
        String url = String.format("%s/captcha/image", this.BASE_URL);
        JSONObject req_json = new JSONObject();
        File f = new File(absolute_path);
        // if file exists, read it as b64 encoded string
        if(f.exists() && !f.isDirectory())
            absolute_path = Utils.read_file_b64(absolute_path);

        // add params to for request body to json object
        req_json.put("access_token", this._access_token);
        req_json.put("b64image", absolute_path);
        if(case_sensitive) req_json.put("case_sensitive", 1);      // add sensitivity, if set
        JSONObject resp_json = Utils.POST(url, req_json);
        return Integer.parseInt(resp_json.get("id").toString());
    }

    /**
     * Submit recaptcha and get back captcha ID (without proxy)
     * @param page_url
     * @param site_key
     * @return
     * @throws Exception
     */
    public int submit_recaptcha(String page_url, String site_key) throws Exception{
        return this.submit_recaptcha(page_url, site_key, null);       // no proxy
    }

    /**
     * Submit recaptcha and get back captcha ID (with proxy)
     * @param page_url
     * @param site_key
     * @return
     * @throws Exception
     */
    public int submit_recaptcha(String page_url, String site_key, String proxy) throws Exception{
        String url = String.format("%s/captcha/recaptcha", this.BASE_URL);
        JSONObject req_json = new JSONObject();
        // add params to for request body to json object
        req_json.put("access_token", this._access_token);
        req_json.put("page_url", page_url);
        req_json.put("site_key", site_key);
        // check for proxy
        if(proxy != null){
            req_json.put("proxy", proxy);
            req_json.put("proxy_type", "HTTP");     // only HTTP for now
        }
        JSONObject resp_json = Utils.POST(url, req_json);
        return Integer.parseInt(resp_json.get("id").toString());
    }

    /**
     * Retrieve captcha [gresponse|text] based on captcha ID
     * @param id
     * @return
     * @throws Exception
     */
    public JSONObject retrieve(int id) throws Exception{
        String url = String.format("%s/captcha/%d?access_token=%s", BASE_URL, id, this._access_token);
        JSONObject resp_json = Utils.GET(url);
        JSONObject jsPending = new JSONObject();
        jsPending.put("gresponse", "");
        jsPending.put("text", "");
        if(resp_json.getString("status").equals("pending")) return jsPending;     // still pending
        return resp_json;
    }

    // Set captcha bad
    public String set_captcha_bad(int captcha_id) throws Exception {
        String url = String.format("%s/captcha/bad/%d", this.BASE_URL, captcha_id);
        JSONObject req_json = new JSONObject();
        // add params to for request body to json object
        req_json.put("access_token", this._access_token);
        JSONObject resp_json = Utils.POST(url, req_json);
        return resp_json.getString("status");
    }
}

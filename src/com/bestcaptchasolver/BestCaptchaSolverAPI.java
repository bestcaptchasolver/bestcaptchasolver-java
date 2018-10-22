package com.bestcaptchasolver;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;

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
     * Submit image path (with case sensitivity param)
     * @param opts
     * @return
     */
    public int submit_image_captcha(Map<String, String> opts) throws Exception{
        String url = String.format("%s/captcha/image", this.BASE_URL);
        JSONObject req_json = new JSONObject();
        String absolute_path = opts.get("image");
        File f = new File(absolute_path);
        // if file exists, read it as b64 encoded string
        if(f.exists() && !f.isDirectory())
            absolute_path = Utils.read_file_b64(absolute_path);

        // add params to for request body to json object
        req_json.put("access_token", this._access_token);
        req_json.put("b64image", absolute_path);
        // case sensitive
        if(opts.containsKey("case_sensitive")) {
            if (opts.get("case_sensitive").equals("true")) req_json.put("case_sensitive", 1);      // add sensitivity, if set
        }
        // affiliate id
        if(opts.containsKey("affiliate_id")) req_json.put("affiliate_id", opts.get("affiliate_id"));
        JSONObject resp_json = Utils.POST(url, req_json);
        return Integer.parseInt(resp_json.get("id").toString());
    }

    /**
     * Submit recaptcha and get back captcha ID (with proxy)
     * @param opts
     * @return
     * @throws Exception
     */
    public int submit_recaptcha(Map<String, String> opts) throws Exception{
        String url = String.format("%s/captcha/recaptcha", this.BASE_URL);
        JSONObject req_json = new JSONObject();
        // add params to for request body to json object
        req_json.put("access_token", this._access_token);
        req_json.put("page_url", opts.get("page_url"));
        req_json.put("site_key", opts.get("site_key"));
        // check for proxy
        if(opts.containsKey("proxy"))
        {
            req_json.put("proxy", opts.get("proxy"));
            req_json.put("proxy_type", "HTTP");     // only HTTP for now
        }
        // optional params
        if (opts.containsKey("type")) req_json.put("type", opts.get("type"));
        if (opts.containsKey("v3_action")) req_json.put("v3_action", opts.get("v3_action"));
        if (opts.containsKey("v3_min_score")) req_json.put("v3_min_score", opts.get("v3_min_score"));
        if (opts.containsKey("user_agent")) req_json.put("user_agent", opts.get("user_agent"));
        if (opts.containsKey("affiliate_id")) req_json.put("affiliate_id", opts.get("affiliate_id"));
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

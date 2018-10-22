package com.example;
import com.bestcaptchasolver.BestCaptchaSolverAPI;

import java.util.HashMap;
import java.util.Map;

public class Program {
    // get access token from: https://bestcaptchasolver.com/account
    private final static String ACCESS_TOKEN = "your_access_token";
    private final static String PAGE_URL = "page_url_here";
    private final static String SITE_KEY = "site_key_here";

    private static void test_api() throws Exception{
        BestCaptchaSolverAPI bcs = new BestCaptchaSolverAPI(ACCESS_TOKEN);      // init API with your access token
        // get balance
        String balance = bcs.account_balance();
        System.out.println(String.format("Balance: %s", balance));

        // submit image captcha
        System.out.println("Solving image captcha ...");
        Map<String, String> d = new HashMap<String, String>();
        d.put("image", "/home/me/Desktop/captcha.jpg");
        //d.put("case_sensitive", "true");        // sets solving to case sensitive
        //d.put("affiliate_id", "your_affiliate_id");      // get it from /account
        // give it an ABSOLUTE path or b64encoded string
        int id = bcs.submit_image_captcha(d);     // works with 2nd parameter as well, case sensitivty
        String image_text = "";
        while(image_text.equals("")){          // while still in completion / solving process
            image_text = bcs.retrieve(id).getString("text");    // try to get the
            Thread.sleep(2000);
        }
        System.out.println(String.format("Captcha text: %s", image_text));

        // solve recaptcha
        System.out.println("Solving recaptcha ...");
        Map<String,String> rd = new HashMap<String, String>();
        rd.put("page_url", PAGE_URL);
        rd.put("site_key", SITE_KEY);
        // optional parameters
        //rd.put("type", "1");        // 1 - regular, 2 - invisible, 3 - v3, default: 1
        //rd.put("v3_action", "home");    // action used when solving v3 reCaptcha
        //rd.put("v3_min_score", "0.3");  // min score to target when solving v3
        //rd.put("proxy", "user:pass@191.123.43.34");     // proxy with/out authentication
        //rd.put("affiliate_id", "your_affiliate_id");      // get it from /account
        id = bcs.submit_recaptcha(rd);     // works with proxy as well, check bottom of page file for examples
        String gresponse = "";
        while(gresponse.equals("")){          // while still in completion / solving process
            gresponse = bcs.retrieve(id).getString("gresponse");    // try to get the
            Thread.sleep(2000);
        }
        System.out.println(String.format("Recaptcha gresponse: %s", gresponse));
        String proxy_status = bcs.retrieve(id).getString("proxy_status");
        // bcs.set_captcha_bad(111);       // set captcha bad using captcha ID
    }
    // main/run method
    public static void main(String[] args) {
        try {
            test_api();
        }
        catch(Exception ex){
            System.out.println(String.format("[!] Error: %s", ex.getMessage()));
        }
    }
}

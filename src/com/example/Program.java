package com.example;
import com.bestcaptchasolver.BestCaptchaSolverAPI;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Program {
    // get access token from: https://bestcaptchasolver.com/account
    private final static String ACCESS_TOKEN = "ACCESS_TOKEN_HERE";
    static BestCaptchaSolverAPI bcs = new BestCaptchaSolverAPI(ACCESS_TOKEN);      // init API with your access token

    private static void test_image() throws Exception{
        // get balance
        String balance = bcs.account_balance();
        System.out.println(String.format("Balance: %s", balance));

        System.out.println("Solving image captcha ...");
        Map<String, String> d = new HashMap<String, String>();
        d.put("image", "/home/me/Desktop/captcha.png");     // give it an ABSOLUTE path or b64encoded string
        // d.put("is_case", "true");        // if case sensitive set to true, default: false, optional
        // d.put("is_phrase", "true");      // if phrase, set to true, default: false, optional
        // d.put("is_math", "true");        // true if captcha is math, default: false, optional
        // d.put("alphanumeric", "1");      // 1 (digits only) or 2 (letters only), default: all characters, optional
        // d.put("minlength", "2");         // minimum length of captcha text, default: any, optional
        // d.put("maxlength", "4");         // maximum length of captcha text, default: any, optional
        // d.put("affiliate_id", "your_affiliate_id");      // get it from /account
        int id = bcs.submit_image_captcha(d);     // works with 2nd parameter as well, case sensitivty
        String image_text = "";
        while(image_text.equals("")){          // while still in completion / solving process
            image_text = bcs.retrieve(id).getString("text");    // try to get the
            Thread.sleep(2000);
        }
        System.out.println(String.format("Captcha text: %s", image_text));
        // bcs.set_captcha_bad(id);       // set captcha bad using captcha ID
    }
    private static void test_recaptcha() throws Exception{
        String PAGE_URL = "PAGE_URL_HERE";
        String SITE_KEY = "SITE_KEY_HERE";
        // get balance
        String balance = bcs.account_balance();
        System.out.println(String.format("Balance: %s", balance));

        System.out.println("Solving recaptcha ...");
        Map<String,String> rd = new HashMap<String, String>();
        rd.put("page_url", PAGE_URL);
        rd.put("site_key", SITE_KEY);
        // other parameters
        // ----------------------------------------------------------------------
        // reCAPTCHA type(s) - optional, defaults to 1
        // ---------------------------------------------
        // 1 - v2
        // 2 - invisible
        // 3 - v3
        // 4 - enterprise v2
        // 5 - enterprise v3
        //
        // rd.put("type", "1");
        //
        // rd.put("v3_action", "home");    // action used when solving v3 reCaptcha
        // rd.put("v3_min_score", "0.3");  // min score to target when solving v3
        // rd.put("data_s", "recaptcha data-s parameter used in loading reCAPTCHA");  // optional
        // rd.put("cookie_input", "a=b;c=d");  // used in solving reCAPTCHA, optional
        // rd.put("proxy", "user:pass@191.123.43.34");     // proxy with/out authentication
        // rd.put("affiliate_id", "your_affiliate_id");      // get it from /account
        int id = bcs.submit_recaptcha(rd);     // works with proxy as well, check bottom of page file for examples
        String gresponse = "";
        while(gresponse.equals("")){          // while still in completion / solving process
            gresponse = bcs.retrieve(id).getString("gresponse");    // try to get the
            Thread.sleep(2000);
        }
        System.out.println(String.format("Recaptcha gresponse: %s", gresponse));
        // String proxy_status = bcs.retrieve(id).getString("proxy_status");        // if submitted with proxy
        // bcs.set_captcha_bad(id);       // set captcha bad using captcha ID
    }
    private static void test_geetest() throws Exception{
        // get balance
        String balance = bcs.account_balance();
        System.out.println(String.format("Balance: %s", balance));

        System.out.println("Solving geetest ...");
        Map<String,String> rd = new HashMap<String, String>();
        rd.put("domain", "DOMAIN_HERE");
        rd.put("gt", "GT_HERE");
        rd.put("challenge", "CHALLENGE_HERE");
        // rd.put("affiliate_id", "your_affiliate_id");      // get it from /account
        int id = bcs.submit_geetest(rd);
        String solution = "";
        while(solution.equals("")){          // while still in completion / solving process
            solution = bcs.retrieve(id).getString("solution");
            Thread.sleep(2000);
        }
        System.out.println(String.format("Geetest solution: %s", solution));
        // bcs.set_captcha_bad(id);       // set captcha bad using captcha ID
    }
    private static void test_geetestv4() throws Exception{
        // get balance
        String balance = bcs.account_balance();
        System.out.println(String.format("Balance: %s", balance));

        System.out.println("Solving geetestv4 ...");
        Map<String,String> rd = new HashMap<String, String>();
        rd.put("domain", "https://example.com");
        rd.put("captchaid", "647f5ed2ed8acb4be36784e01556bb71");
        // rd.put("affiliate_id", "your_affiliate_id");      // get it from /account
        int id = bcs.submit_geetest_v4(rd);
        String solution = "";
        while(solution.equals("")){          // while still in completion / solving process
            solution = bcs.retrieve(id).getString("solution");
            Thread.sleep(2000);
        }
        System.out.println(String.format("Geetestv4 solution: %s", solution));
        // bcs.set_captcha_bad(id);       // set captcha bad using captcha ID
    }
    private static void test_capy() throws Exception{
        // get balance
        String balance = bcs.account_balance();
        System.out.println(String.format("Balance: %s", balance));

        System.out.println("Solving capy ...");
        Map<String,String> rd = new HashMap<String, String>();
        rd.put("page_url", "PAGE_URL_HERE");
        rd.put("site_key", "SITE_KEY_HERE");
        // rd.put("affiliate_id", "your_affiliate_id");      // get it from /account
        int id = bcs.submit_capy(rd);
        String solution = "";
        while(solution.equals("")){          // while still in completion / solving process
            solution = bcs.retrieve(id).getString("solution");
            Thread.sleep(2000);
        }
        System.out.println(String.format("Capy solution: %s", solution));
        // bcs.set_captcha_bad(id);       // set captcha bad using captcha ID
    }
    private static void test_hcaptcha() throws Exception{
        // get balance
        String balance = bcs.account_balance();
        System.out.println(String.format("Balance: %s", balance));

        System.out.println("Solving hCaptcha ...");
        Map<String,String> rd = new HashMap<String, String>();
        rd.put("page_url", "PAGE_URL_HERE");
        rd.put("site_key", "SITE_KEY_HERE");
        // rd.put("invisible", "1");
        // rd.put("payload", "{\"rqdata\": \"from web requests\"}");
        // rd.put("user_agent", "your UA");
        // rd.put("proxy", "12.34.56.78:1234");
        // rd.put("affiliate_id", "your_affiliate_id");      // get it from /account
        int id = bcs.submit_hcaptcha(rd);
        String solution = "";
        while(solution.equals("")){          // while still in completion / solving process
            solution = bcs.retrieve(id).getString("solution");
            Thread.sleep(2000);
        }
        System.out.println(String.format("Solution: %s", solution));
        // bcs.set_captcha_bad(id);       // set captcha bad using captcha ID
    }
    private static void test_funcaptcha() throws Exception{
        // get balance
        String balance = bcs.account_balance();
        System.out.println(String.format("Balance: %s", balance));

        System.out.println("Solving funcaptcha ...");
        Map<String,String> rd = new HashMap<String, String>();
        rd.put("page_url", "https://abc.com");
        rd.put("s_url", "https://api.arkoselabs.com");
        rd.put("site_key", "11111111-1111-1111-1111-111111111111");
        // rd.put("data", "{\"x\":\"y\"}");      // optional
        // rd.put("affiliate_id", "your_affiliate_id");      // optional, get it from /account
        int id = bcs.submit_funcaptcha(rd);
        String solution = "";
        while(solution.equals("")){          // while still in completion / solving process
            solution = bcs.retrieve(id).getString("solution");
            Thread.sleep(2000);
        }
        System.out.println(String.format("Solution: %s", solution));
        // bcs.set_captcha_bad(id);       // set captcha bad using captcha ID
    }
    private static void test_task() throws Exception{
        // get balance
        String balance = bcs.account_balance();
        System.out.println(String.format("Balance: %s", balance));

        System.out.println("Solving task ...");
        Map<String,String> rd = new HashMap<String, String>();
        rd.put("template_name", "Login test page");
        rd.put("page_url", "https://bestcaptchasolver.com/automation/login");
        rd.put("variables", "{\"username\": \"xyz\", \"password\": \"0000\"}");
        // rd.put("user_agent", "your UA");
        // rd.put("proxy", "12.34.56.78:1234");
        // rd.put("affiliate_id", "your_affiliate_id");      // optional, get it from /account
        int id = bcs.submit_task(rd);

        // submit pushVariables while task is being solved by the worker
        // very helpful, for e.g. in cases of 2FA authentication
        // bcs.task_push_variables(id, "{\"tfa_code\": \"3495\"}");

        String solution = "";
        while(solution.equals("")){          // while still in completion / solving process
            solution = bcs.retrieve(id).getString("solution");
            Thread.sleep(2000);
        }
        System.out.println(String.format("Solution: %s", solution));
        // bcs.set_captcha_bad(id);       // set captcha bad using captcha ID
    }
    // main/run method
    public static void main(String[] args) {
        try {
            // test_image();
            test_recaptcha();
            // test_geetest();
            // test_geetestv4();
            // test_capy();
            // test_hcaptcha();
            // test_funcaptcha();
            // test_task();
        }
        catch(Exception ex){
            System.out.println(String.format("[!] Error: %s", ex.getMessage()));
        }
    }
}

package com.example;
import com.bestcaptchasolver.BestCaptchaSolverAPI;

public class Program {
    // get access token from: https://bestcaptchasolver.com/account
    private final static String ACCESS_TOKEN = "your_access_token";
    private final static String PAGE_URL = "recaptcha_page_url";
    private final static String SITE_KEY = "recaptcha_site_key";

    private static void test_api() throws Exception{
        BestCaptchaSolverAPI bcs = new BestCaptchaSolverAPI(ACCESS_TOKEN);      // init API with your access token
        // get balance
        String balance = bcs.account_balance();
        System.out.println(String.format("Balance: %s", balance));

        // submit image captcha
        System.out.println("Solving image captcha ...");
        // give it an ABSOLUTE path or b64encoded string
        int id = bcs.submit_image_captcha("/home/me/Desktop/captcha.jpg");     // works with 2nd parameter as well, case sensitivty
        String image_text = "";
        while(image_text.equals("")){          // while still in completion / solving process
            image_text = bcs.retrieve(id).getString("text");    // try to get the
            Thread.sleep(2000);
        }
        System.out.println(String.format("Captcha text: %s", image_text));

        // solve recaptcha
        System.out.println("Solving recaptcha ...");
        id = bcs.submit_recaptcha(PAGE_URL, SITE_KEY);     // works with proxy as well, check bottom of page file for examples
        String gresponse = "";
        while(gresponse.equals("")){          // while still in completion / solving process
            gresponse = bcs.retrieve(id).getString("gresponse");    // try to get the
            Thread.sleep(2000);
        }
        System.out.println(String.format("Recaptcha gresponse: %s", gresponse));
        String proxy_status = bcs.retrieve(id).getString("proxy_status");
        // bcs.submit_image_captcha("/home/user/Desktop/captcha.jpg", true);   // case sensitive image captcha
        // bcs.submit_recaptcha(PAGE_URL, SITE_KEY, "12.34.56.78:1234");       // use proxy
        // bcs.submit_recaptcha(PAGE_URL, SITE_KEY, "user:password@12.34.56.78:1234");       // use proxy, with auth
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

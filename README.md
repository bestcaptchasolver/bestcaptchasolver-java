BestCaptchaSolver Java API wrapper
=========================================
bestcaptchasolverapi is a super easy to use bypass captcha Java API wrapper for bestcaptchasolver.com captcha service

## Installation
    
    git clone https://github.com/bestcaptchasolver/bestcaptchasolver-java

## Dependencies

    org.apache.httpcomponents:httpclient
    org.json:json

## How to use?

Simply import the library, set the auth details and start using the captcha service:

``` java
import com.bestcaptchasolver.BestCaptchaSolverAPI;
```
Set access_token or username and password (legacy) for authentication

``` java
String access_token = "your_access_key";
BestCaptchaSolverAPI bcs = new BestCaptchaSolverAPI(access_token);
```

Once you've set your authentication details, you can start using the API

## Get balance

``` java
String balance = bcs.account_balance();
System.out.println(String.Format("Balance: %s", balance));
```

## Submit image captcha

``` java
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
```

Once you have the captchaID, you can check for it's completion
``` java
String image_text = "";
while(image_text.equals(""))
{
    image_text = bcs.retrieve(id).getString("text");
    Thread.sleep(2000);
}
```

## Submit recaptcha details

For recaptcha submission there are two things that are required and some that are optional
- page_url
- site_key
- type (optional)
- v3_action (optional)
- v3_min_score (optional)
- user_agent (optional)
- affiliate_id (optional)
- proxy (optional)

For more details about the parameters check [/api](https://bestcaptchasolver.com/api) page

``` java
Map<String,String> rd = new HashMap<String, String>();
rd.put("page_url", PAGE_URL);
rd.put("site_key", SITE_KEY);
id = bcs.submit_recaptcha(rd);     // works with proxy as well, check bottom of page file for examples
```
Same as before, this returns an ID which is used to regulary check for completion


## Submit Geetest
- domain
- gt
- challenge

```java
Map<String,String> rd = new HashMap<String, String>();
rd.put("domain", "DOMAIN_HERE");
rd.put("gt", "GT_HERE");
rd.put("challenge", "CHALLENGE_HERE");
// rd.put("affiliate_id", "your_affiliate_id");      // get it from /account
int id = bcs.submit_geetest(rd);
```

## Submit Capy
- page_url
- site_key

```java
Map<String,String> rd = new HashMap<String, String>();
rd.put("page_url", "PAGE_URL_HERE");
rd.put("site_key", "SITE_KEY_HERE");
// rd.put("affiliate_id", "your_affiliate_id");      // get it from /account
int id = bcs.submit_capy(rd);
```

## Retrieve response (all captchas)

```java
String gresponse = "";
while (gresponse.equals(""))
{
     gresponse = bcs.retrieve(id).getString("gresponse");	// can be also `text` or `solution` instead of `gresponse`
     Thread.sleep(5000);
}
```


## If reCAPTCHA is submitted with proxy, get proxy status

```java
String proxy_status = bcs.retrieve(id).getString("proxy_status");
```

## Set captcha bad

In case a captcha was wrongly completed, you can use the `set_captcha_bad(captchaID)` method like this
```java
bcs.set_captcha_bad(45);
```


## Examples
Compile and run the **com.example** package

## Binary
If you don't want to compile your own library, you can check the binary folder for a compiled version.
**Keep in mind** that this might not be the latest version with every release

## License
API library is licensed under the MIT License

## More information
More details about the server-side API can be found [here](https://bestcaptchasolver.com)


<sup><sub>captcha, bypasscaptcha, decaptcher, decaptcha, 2captcha, deathbycaptcha, anticaptcha, 
bypassrecaptchav2, bypassnocaptcharecaptcha, bypassinvisiblerecaptcha, captchaservicesforrecaptchav2, 
recaptchav2captchasolver, googlerecaptchasolver, recaptchasolverpython, recaptchabypassscript, bestcaptchasolver</sup></sub>
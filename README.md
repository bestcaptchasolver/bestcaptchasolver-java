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
int id = bcs.submit_image_captcha("/home/me/Desktop/captcha.jpg");
```
Takes a 2nd argument, **case_sensitive** which is a bool (optional)

## Retrieve image captcha text

Once you have the captchaID, you can check for it's completion
```
int id = bcs.submit_image_captcha("/home/me/Desktop/captcha.jpg"); 
String image_text = "";
while(image_text.equals(""))
{
    image_text = bcs.retrieve(id).getString("text");
    Thread.sleep(2000);
}
```

## Submit recaptcha details

For recaptcha submission there are two things that are required.
- page_url
- site_key
- proxy (optional), works in this format `12.34.56.78:1234` or `user:password@12.34.56.78:1234`

``` java
id = bcs.submit_recaptcha(page_url, site_key);
```
Same as before, this returns an ID which is used to regulary check for completion

## Retrieve captcha response

```java
id = bcs.submit_recaptcha(page_url, site_key);
String gresponse = "";
while (gresponse.equals(""))
{
     gresponse = bcs.retrieve(id).getString("gresponse");
     Thread.sleep(5000);
}
```

## If submitted with proxy, get proxy status

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
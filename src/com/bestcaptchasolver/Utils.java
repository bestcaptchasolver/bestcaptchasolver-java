package com.bestcaptchasolver;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by icebox on 23/05/17.
 */
public class Utils {

    // post only with params and user agent
    public static JSONObject POST(String url, JSONObject json) throws Exception {
        CloseableHttpClient httpClient =  createAcceptSelfSignedCertificateClient();

        try {
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(json.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            JSONObject j = new JSONObject(EntityUtils.toString(entity, "UTF-8"));
            if(j.get("status").toString().equals("error")) throw new Exception(j.get("message").toString());
            return j;
        } finally {
            httpClient.close();
        }
    }

    public static JSONObject GET(String url) throws Exception{
        CloseableHttpClient httpClient =  createAcceptSelfSignedCertificateClient();

        try {
            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", "application/json");
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            JSONObject j = new JSONObject(EntityUtils.toString(entity, "UTF-8"));
            if(j.get("status").toString().equals("error")) throw new Exception(j.get("message").toString());
            return j;
        } finally {
            httpClient.close();
        }
    }
    private static CloseableHttpClient createAcceptSelfSignedCertificateClient()
            throws Exception {

        // use the TrustSelfSignedStrategy to allow Self Signed Certificates
        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();

        // we can optionally disable hostname verification.
        // if you don't want to further weaken the security, you don't have to include this.
        HostnameVerifier allowAllHosts = new NoopHostnameVerifier();

        // create an SSL Socket Factory to use the SSLContext with the trust self signed certificate strategy
        // and allow all hosts verifier.
        SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);

        // finally create the HttpClient using HttpClient factory methods and assign the ssl socket factory
        return HttpClients
                .custom()
                .setSSLSocketFactory(connectionFactory)
                .build();
    }
    public static String read_file_b64(String path) throws IOException {
        return DatatypeConverter.printBase64Binary(Files.readAllBytes(
                Paths.get(path)));
    }
    public static String get_absolute_path(String relative){
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path filePath = Paths.get(currentPath.toString(), relative);
        return filePath.toString();
    }
}

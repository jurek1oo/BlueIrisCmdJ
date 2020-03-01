package inventionSource.com.au;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/*
 * GNU General Public License v2.0, 2020 March Jurek Kurianski
 */
public class RequestHttp {
    private static final Logger log = LogManager.getLogger(BlueCmdRequest.class);

    public String PostRequest(String url, String jsonData) throws IOException {
        log.debug("url: " + url + " jsonData: " + jsonData);
        String responseBody = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        StringEntity stringEntity = new StringEntity(jsonData);
        httpPost.setEntity(stringEntity);
        try {
            ResponseHandler<String> responseHandler = response -> {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode >= 200 && statusCode < 300) {
                    HttpEntity entity = response.getEntity();
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader((response.getEntity().getContent())));
                    StringBuffer result = new StringBuffer();
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        log.debug("Response : \n" + result.append(line));
                    }
                    return result.toString();
                } else {
                    log.error("Error statusCode: " + statusCode);
                    throw new ClientProtocolException("Unexpected response status: " + statusCode);
                }
            };
            responseBody = httpclient.execute(httpPost, responseHandler);
            log.debug("responseBody: " + responseBody);
        } catch (Exception e) {
            log.error("Error: url: " + url + " jsonData: " + jsonData + "\n", e);
        }
        finally {
            httpclient.close();
        }
        return responseBody;
    }
}

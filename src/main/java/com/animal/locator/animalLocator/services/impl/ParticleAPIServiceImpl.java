package com.animal.locator.animalLocator.services.impl;

import com.animal.locator.animalLocator.models.GpsLocator;
import com.animal.locator.animalLocator.services.ParticleAPIService;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service("particleAPIService")
public class ParticleAPIServiceImpl implements ParticleAPIService {

    //390024001747343432313031
    //4e4734622768f1caa9a851d43e7c2005faaea7ae

    @Override
    public void sendMessageToPhoton(GpsLocator gpsLocator, String value, String function) {
        try {
            String deviceId = new String(Base64.getDecoder().decode(gpsLocator.getDeviceId()));
            String accessToken = new String(Base64.getDecoder().decode(gpsLocator.getAccessToken()));
            String url = "https://api.particle.io/v1/devices/" + deviceId + "/" + function + "?access_token=" + accessToken;
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("arg", value));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = client.execute(httpPost);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getLatitude(GpsLocator gpsLocator) {
        return getResponse("latitude", gpsLocator.getDeviceId(), gpsLocator.getAccessToken());
    }

    @Override
    public String getLongitude(GpsLocator gpsLocator) {
        return getResponse("longitude", gpsLocator.getDeviceId(),gpsLocator.getAccessToken());
    }

    public String getResponse(String variable, String device, String token) {
        try {
            String deviceId = new String(Base64.getDecoder().decode(device));
            String accessToken = new String(Base64.getDecoder().decode(token));
            String url = "https://api.particle.io/v1/devices/" + deviceId + "/" + variable + "?access_token="+ accessToken;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject jsonObject = new JSONObject(response.toString());
            String result = jsonObject.getString("result");
            return result;
        }catch (Exception e) {
            System.out.println("Error with device");
            return "";
        }
    }

}
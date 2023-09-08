package com.ssafy.yesrae.crawling.api.song.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.yesrae.crawling.db.entity.Song;
import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.parser.JSONParser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SongController {

    StringBuilder sb;
    BufferedReader br;
    private static final String code = "";
    private static String Atoken = "";
    private static String Rtoken = "";
    private static final String authorization = "Basic Y2RhMmVmZGYzOTVkNDczODg2MzUyZmI4NWM5MjZmZDI6ZDNhOTU1ZjVhZmQyNDg2ZWI2ZGM0NjE3OTIwNDRkMzU=";

    JSONParser parser = new JSONParser();

    public void getToken(){
        try {
            URL TokenURL = new URL("https://accounts.spotify.com/api/token");
            String line;
            sb = new StringBuilder();
            HttpURLConnection conn = (HttpURLConnection) TokenURL.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Authorization", authorization);
            conn.setDoOutput(true);
            String PostData = "{\"code:\""+code+",\"redirect_url\":https://i9a304.p.ssafy.io/,\"grant_type\":authorization_code}";
            byte[] postDataBytes = PostData.getBytes(StandardCharsets.UTF_8);
            conn.getOutputStream().write(postDataBytes);

            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                    StandardCharsets.UTF_8));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(),
                    StandardCharsets.UTF_8));
            }
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            conn.disconnect();
            String text = sb.toString();
            JsonElement element = JsonParser.parseString(text);
            JsonObject object = element.getAsJsonObject();

            Atoken = object.get("access_token").toString();
            Rtoken = object.get("refresh_token").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 * * * *")
    public void changeToken(){
        try {
            URL RefreshURL = new URL("https://accounts.spotify.com/api/token");
            String line;
            sb = new StringBuilder();
            HttpURLConnection conn = (HttpURLConnection) RefreshURL.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Authorization", authorization);
            conn.setDoOutput(true);
            String PostData = "{\"refresh_token:\""+Rtoken+",\"grant_type\":refresh_token}";
            byte[] postDataBytes = PostData.getBytes(StandardCharsets.UTF_8);
            conn.getOutputStream().write(postDataBytes);
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                    StandardCharsets.UTF_8));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(),
                    StandardCharsets.UTF_8));
            }
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            conn.disconnect();
            String text = sb.toString();
            JsonElement element = JsonParser.parseString(text);
            JsonObject object = element.getAsJsonObject();
            Atoken = object.get("access_token").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
        protected void registSong(){
        getToken();
        try {
            FileInputStream file = new FileInputStream("C:\\dev\\A304\\crawling\\singer.xlsx");
            IOUtils.setByteArrayMaxOverride(Integer.MAX_VALUE);
            XSSFSheet sheet = new XSSFWorkbook(file).getSheetAt(0);
            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    String artistId = String.valueOf(cellIterator.next().getNumericCellValue());
                    URL ArtistURL = new URL("https://api.spotify.com/v1/artists/"+artistId+"/albums?include_groups=single%2Calbum&market=KR&limit=50");
                    /* 가수 ID 검색으로 받아야 할 것
                     *: 가수 이름, 앨범 ID, 앨범 이름, 발매 시기
                     * 이 후 앨범 ID로 다시 track을 검색한다. */
                    String line;
                    sb = new StringBuilder();
                    HttpURLConnection conn = (HttpURLConnection) ArtistURL.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Authorization", "Bearer "+Atoken);
                    if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                        br = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                            StandardCharsets.UTF_8));
                    }else if (conn.getResponseCode() == 429){
                        // 너무 자주 api 보냈을 때
                    }
                    else {
                        System.out.println("아티스트 ID 부터 오류남");
                        break;
                    }
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    br.close();
                    conn.disconnect();
                    String text = sb.toString();
                    JsonObject albumObject = (JsonObject)parser.parse(text);
                    JsonArray albums = albumObject.getAsJsonArray("items");
                    for(JsonElement album : albums){
                        JsonObject ObjectAlbum = album.getAsJsonObject();
                        JsonObject artists = ObjectAlbum.get("artists").getAsJsonArray().get(0).getAsJsonObject();
                        String artistName = artists.get("name").getAsString();
                        String albumName = ObjectAlbum.get("id").getAsString();
                        String albumId = ObjectAlbum.get("name").getAsString();
                        LocalDate releaseDate = LocalDate.parse(ObjectAlbum.get("release_date").getAsString(), DateTimeFormatter.ISO_DATE);
                        String imgUrl = ObjectAlbum.get("images").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString();
                        URL AlbumURL = new URL("https://api.spotify.com/v1/albums/"+albumId+"?market=KR");
                            /* 앨범 ID 검색으로 받아야 할 것
                             * : 각 트랙의 ID */
                        sb = new StringBuilder();
                        conn = (HttpURLConnection) ArtistURL.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setRequestProperty("Authorization", "Bearer "+Atoken);
                        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                            br = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                                StandardCharsets.UTF_8));
                        }else if (conn.getResponseCode() == 429){
                            // 너무 자주 api 보냈을 때
                        }
                        else {
                            System.out.println("앨범 ID에서 오류 남");
                            break;
                        }
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        br.close();
                        conn.disconnect();
                        text = sb.toString();
                        JsonObject trackObject = (JsonObject) parser.parse(text);
                        JsonArray tracks = trackObject.getAsJsonArray("items");
                        for(JsonElement track : tracks){
                            JsonObject ObjectTrack = track.getAsJsonObject();
                            String trackId = ObjectTrack.get("id").getAsString();
                            String trackName = ObjectTrack.get("name").getAsString();
                            Integer duration = ObjectTrack.get("duration_ms").getAsInt();
                            String previewURL;
                            Object preview = ObjectTrack.get("preview_url");
                            if(preview == null){
                                previewURL = null;
                            }else{
                                previewURL = preview.toString();
                            }
                            URL FeatureURL = new URL("https://api.spotify.com/v1/artists/"+artistId+"/albums?include_groups=single%2Calbum&market=KR&limit=50");
                            /* 노래분석으로 받아야 할 것

                             * */
                            sb = new StringBuilder();
                            conn = (HttpURLConnection) ArtistURL.openConnection();
                            conn.setRequestMethod("GET");
                            conn.setRequestProperty("Authorization", "Bearer "+Atoken);
                            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                                br = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                                    StandardCharsets.UTF_8));
                            }else if (conn.getResponseCode() == 429){
                                // 너무 자주 api 보냈을 때
                            }
                            else {
                                System.out.println("노래 분석 오류남");
                                break;
                            }
                            while ((line = br.readLine()) != null) {
                                sb.append(line);
                            }
                            br.close();
                            conn.disconnect();
                            text = sb.toString();
                            JsonObject featureObject = (JsonObject)parser.parse(text);
                            JsonArray features = featureObject.getAsJsonArray("items");
                        }
                    }
                }
                Song song = new Song();
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

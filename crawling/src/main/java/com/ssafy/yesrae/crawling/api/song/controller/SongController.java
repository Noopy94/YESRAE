package com.ssafy.yesrae.crawling.api.song.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.yesrae.crawling.api.song.request.SongRegistPostReq;
import com.ssafy.yesrae.crawling.api.song.service.SongService;
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
    private static final String code = "AQCfArQ5HPsqMoOCHcFRBNOep6fWguwnxjsO8niNwGsALvmT2Ke6VaSXRGwU4W7CcwHeeGlX-9RfCjV_1edsQH3NMtqoqcTQzjOP_Ml3Dwb4uxnWP9kmoFmMvRTh6sXqc9UbP-lJIZuPB8RguUVhyqZHFSJkfVfok0FPe2p4";
    private static String Atoken = "";
    private static String Rtoken = "";
    private static final String authorization = "Basic Y2RhMmVmZGYzOTVkNDczODg2MzUyZmI4NWM5MjZmZDI6ZDNhOTU1ZjVhZmQyNDg2ZWI2ZGM0NjE3OTIwNDRkMzU=";

    JSONParser parser = new JSONParser();

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }


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
            String PostData = "code="+code+"&redirect_url=https://i9a304.p.ssafy.io/&grant_type=authorization_code";
            byte[] postDataBytes = PostData.getBytes(StandardCharsets.UTF_8);
            conn.getOutputStream().write(postDataBytes,0,postDataBytes.length);

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
            System.out.println(object.toString());
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
            String PostData = "{\"refresh_token\":"+Rtoken+",\"grant_type\":\"refresh_token\"}";
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
                    }else if (conn.getResponseCode() == 401){
                        // 토큰 문제
                        changeToken();
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
                             * : 각 트랙의 ID, 각 트랙의 이름, 노래길이, 미리보기 URL */
                        sb = new StringBuilder();
                        conn = (HttpURLConnection) AlbumURL.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setRequestProperty("Authorization", "Bearer "+Atoken);
                        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                            br = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                                StandardCharsets.UTF_8));
                        }else if (conn.getResponseCode() == 429){
                            // 너무 자주 api 보냈을 때
                        }else if (conn.getResponseCode() == 401){
                            // 토큰 문제
                            changeToken();
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
                        JsonObject trackObject = ((JsonObject) parser.parse(text));
                        JsonArray genres = trackObject.get("genre").getAsJsonArray();
                        String genre = null;
                        if(!genres.isEmpty()){
                            genre = genres.get(0).getAsString();
                        }
                        JsonArray tracks = trackObject.get("tracks").getAsJsonObject().getAsJsonArray("items");
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
                            /* 여기서부터는 인기도 받아오기위한 getTrack */
                            URL TrackURL = new URL("https://api.spotify.com/v1/tracks/"+trackId);
                            sb = new StringBuilder();
                            conn = (HttpURLConnection) TrackURL.openConnection();
                            conn.setRequestMethod("GET");
                            conn.setRequestProperty("Authorization", "Bearer "+Atoken);
                            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                                br = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                                    StandardCharsets.UTF_8));
                            }else if (conn.getResponseCode() == 429){
                                // 너무 자주 api 보냈을 때
                            }else if (conn.getResponseCode() == 401){
                                // 토큰 문제
                                changeToken();
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
                            JsonObject TrackObject = (JsonObject)parser.parse(text);
                            Integer popularity = trackObject.get("popularity").getAsInt();

                            /* 여기서부터는 노래 분석 */
                            URL FeatureURL = new URL("https://api.spotify.com/v1/audio-features?ids="+trackId);
                                  /* 노래분석으로 받아야 할 것
                                   * audio feature 정보들 */
                            sb = new StringBuilder();
                            conn = (HttpURLConnection) FeatureURL.openConnection();
                            conn.setRequestMethod("GET");
                            conn.setRequestProperty("Authorization", "Bearer "+Atoken);
                            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                                br = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                                    StandardCharsets.UTF_8));
                            }else if (conn.getResponseCode() == 429){
                                // 너무 자주 api 보냈을 때
                            }else if (conn.getResponseCode() == 401){
                                // 토큰 문제
                                changeToken();
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
                            Float acousticness = featureObject.get("acousticness").getAsFloat();
                            Float danceability = featureObject.get("danceability").getAsFloat();
                            Float energy = featureObject.get("energy").getAsFloat();
                            Float instrumentalness = featureObject.get("instrumentalness").getAsFloat();
                            Integer tune = featureObject.get("key").getAsInt();
                            Float liveness = featureObject.get("liveness").getAsFloat();
                            Float loudness = featureObject.get("loudness").getAsFloat();
                            Integer mode = featureObject.get("mode").getAsInt();
                            Float speechiness = featureObject.get("speechiness").getAsFloat();
                            Float tempo = featureObject.get("tempo").getAsFloat();
                            Integer timeSignature = featureObject.get("time_signature").getAsInt();
                            Float valence = featureObject.get("valence").getAsFloat();

                            SongRegistPostReq registInfo = new SongRegistPostReq();
                            registInfo.setId(trackId);
                            registInfo.setName(trackName);
                            registInfo.setAlbumId(albumId);
                            registInfo.setAlbumName(albumName);
                            registInfo.setArtistId(artistId);
                            registInfo.setArtistName(artistName);
                            registInfo.setGenre(genre);
                            registInfo.setImgUrl(imgUrl);
                            registInfo.setPreviewUrl(previewURL);
                            registInfo.setReleaseDate(releaseDate);
                            registInfo.setDuration(duration);
                            registInfo.setPopularity(popularity);
                            registInfo.setAcousticness(acousticness);
                            registInfo.setDanceability(danceability);
                            registInfo.setEnergy(energy);
                            registInfo.setInstrumentalness(instrumentalness);
                            registInfo.setTune(tune);
                            registInfo.setLiveness(liveness);
                            registInfo.setLoudness(loudness);
                            registInfo.setMode(mode);
                            registInfo.setSpeechiness(speechiness);
                            registInfo.setTempo(tempo);
                            registInfo.setTimeSignature(timeSignature);
                            registInfo.setValence(valence);

                            songService.registSong(registInfo);
                        }
                    }
                }
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

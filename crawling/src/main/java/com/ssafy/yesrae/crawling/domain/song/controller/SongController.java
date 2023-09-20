package com.ssafy.yesrae.crawling.domain.song.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.yesrae.crawling.domain.song.dto.request.SongRegistPostReq;
import com.ssafy.yesrae.crawling.domain.song.dto.response.SongFindRes;
import com.ssafy.yesrae.crawling.domain.song.service.SongService;
import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SongController {

    private static final String code = "";
    private static final String authorization = "";
    private static final int startIndex = 2058;

    StringBuilder sb;
    BufferedReader br;
    private static String Atoken = "";
    private static String Rtoken = "";
    XSSFWorkbook workbook;
    private static int sheetSize;
    JsonParser parser = new JsonParser();
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    HashMap<String, Boolean> artistsMap = new HashMap<>();
    Deque<String> artistQueue = new ArrayDeque<>();


    public void getToken() {
        try {
            URL TokenURL = new URL("https://accounts.spotify.com/api/token");
            String line;
            sb = new StringBuilder();
            HttpURLConnection conn = (HttpURLConnection) TokenURL.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Authorization", authorization);
            conn.setDoOutput(true);
            String PostData = "code=" + code
                + "&redirect_uri=https%3A%2F%2Fi9a304.p.ssafy.io%2F&grant_type=authorization_code";
            byte[] postDataBytes = PostData.getBytes(StandardCharsets.UTF_8);
            conn.getOutputStream().write(postDataBytes, 0, postDataBytes.length);

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
            JsonObject object = JsonParser.parseString(text).getAsJsonObject();
            System.out.println(object.toString());
            Atoken = object.get("access_token").getAsString();
            Rtoken = object.get("refresh_token").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeToken() {
        try {
            URL RefreshURL = new URL("https://accounts.spotify.com/api/token");
            String line;
            sb = new StringBuilder();
            HttpURLConnection conn = (HttpURLConnection) RefreshURL.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Authorization", authorization);
            conn.setDoOutput(true);
            String PostData = "refresh_token=" + Rtoken + "&grant_type=refresh_token";
            byte[] postDataBytes = PostData.getBytes(StandardCharsets.UTF_8);
            conn.getOutputStream().write(postDataBytes, 0, postDataBytes.length);

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
            JsonObject object = JsonParser.parseString(text).getAsJsonObject();
            System.out.println(object.toString());
            Atoken = object.get("access_token").getAsString();
            if (object.has("refresh_token")) {
                Rtoken = object.get("refresh_token").getAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    @PostConstruct
    protected void registSong() {
        getToken();
        try {
            FileInputStream file = new FileInputStream("C:\\dev\\A304\\crawling\\singer.xlsx");
            IOUtils.setByteArrayMaxOverride(Integer.MAX_VALUE);
            workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            sheetSize = sheet.getLastRowNum() + 1;
            for (int sheetIndex = 0; sheetIndex < sheetSize; sheetIndex++) {
                String artistId = sheet.getRow(sheetIndex).getCell(0).getStringCellValue();
                artistsMap.put(artistId, true);
                artistQueue.add(artistId);
            }
            file.close();
            for (int i = 0; i < startIndex; i++) {
                artistQueue.poll();
            }
            System.out.println("엑셀 읽었고 지금 가수 수는" + artistQueue.size());
            while (!artistQueue.isEmpty()) {
                String artistId = artistQueue.poll();
                URL RelatedURL = new URL(
                    "https://api.spotify.com/v1/artists/" + artistId + "/related-artists");
                /* 가수 ID를 받고나서
                 * 비슷한 가수들을 queue에 집어넣는다.
                 * 물론 set에 없을 경우에만. */
                String RelatedLine;
                sb = new StringBuilder();
                HttpURLConnection relatedConn = (HttpURLConnection) RelatedURL.openConnection();
                relatedConn.setRequestMethod("GET");
                relatedConn.setRequestProperty("Authorization", "Bearer " + Atoken);
                if (relatedConn.getResponseCode() >= 200 && relatedConn.getResponseCode() <= 300) {
                    br = new BufferedReader(new InputStreamReader(relatedConn.getInputStream(),
                        StandardCharsets.UTF_8));
                } else if (relatedConn.getResponseCode() == 429) {
                    System.out.println("너무 자주했어요 잠깐쉽니다");
                    Thread.sleep(10000);
                    artistQueue.addFirst(artistId);
                    continue;
                    // 너무 자주 api 보냈을 때
                } else if (relatedConn.getResponseCode() == 401) {
                    // 토큰 문제
                    System.out.println("한시간 됐네요 토큰 갱신합니다.");
                    changeToken();
                    artistQueue.addFirst(artistId);
                    continue;
                } else {
                    System.out.println("연관 아티스트 ID 오류남" + relatedConn.getResponseMessage());
                    continue;
                }
                while ((RelatedLine = br.readLine()) != null) {
                    sb.append(RelatedLine);
                }
                br.close();
                relatedConn.disconnect();
                String RelatedText = sb.toString();
                JsonArray relatedArtists = parser.parse(RelatedText).getAsJsonObject()
                    .getAsJsonArray("artists");
                for (int relatedIndex = 0; relatedIndex < relatedArtists.size(); relatedIndex++) {
                    JsonObject relatedArtist = relatedArtists.get(relatedIndex).getAsJsonObject();
                    String relatedArtistId = relatedArtist.get("id").getAsString();
                    if (!artistsMap.containsKey(relatedArtistId)) {
                        artistQueue.add(relatedArtistId);
                        sheet.createRow(sheetSize);
                        Cell cell = sheet.getRow(sheetSize++).createCell(0);
                        cell.setCellValue(relatedArtistId);
                        artistsMap.put(relatedArtistId, true);
                        System.out.println(
                            "artist 추가되었고 추가된 아티스트는 " + relatedArtist.get("name").getAsString()
                                + "남은 인원은" + artistQueue.size());
                    }
                }
                try (FileOutputStream out = new FileOutputStream(
                    new File("C:\\dev\\A304\\crawling\\", "singer.xlsx"))) {
                    workbook.write(out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                URL ArtistURL = new URL("https://api.spotify.com/v1/artists/" + artistId
                    + "/albums?include_groups=single,album&market=KR&limit=50");
                /* 가수 ID 검색으로 받아야 할 것
                 * : 가수 이름, 앨범 ID, 앨범 이름, 발매 시기
                 * 이 후 앨범 ID로 다시 track을 검색한다. */
                String line;
                sb = new StringBuilder();
                HttpURLConnection conn = (HttpURLConnection) ArtistURL.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization", "Bearer " + Atoken);
                if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                        StandardCharsets.UTF_8));
                } else if (conn.getResponseCode() == 429) {
                    System.out.println("너무 자주했어요 잠깐쉽니다");
                    Thread.sleep(10000);
                    artistQueue.addFirst(artistId);
                    continue;
                    // 너무 자주 api 보냈을 때
                } else if (conn.getResponseCode() == 401) {
                    // 토큰 문제
                    System.out.println("한시간 됐네요 토큰 갱신합니다.");
                    changeToken();
                    artistQueue.addFirst(artistId);
                    continue;
                } else {
                    System.out.println("아티스트 ID 오류남" + conn.getResponseMessage());
                    break;
                }
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                conn.disconnect();
                String text = sb.toString();
                JsonObject albumObject = parser.parse(text).getAsJsonObject();
                JsonArray albums = albumObject.getAsJsonArray("items");
                for (int albumIndex = 0; albumIndex < albums.size(); albumIndex++) {
                    JsonObject ObjectAlbum = albums.get(albumIndex).getAsJsonObject();
                    // 여기서 artist ID가 일치하는 사람 가져옵시다
                    JsonArray artists = ObjectAlbum.get("artists").getAsJsonArray();
                    JsonObject artist = null;
                    for (int i = 0; i < artists.size(); i++) {
                        artist = artists.get(i).getAsJsonObject();
                        if (artist.get("id").getAsString().equals(artistId)) {
                            break;
                        }
                    }
                    // 한글가능성
                    String artistName = artist.get("name").getAsString();
                    // 한글가능성
                    String albumName = ObjectAlbum.get("name").getAsString();
                    String albumId = ObjectAlbum.get("id").getAsString();
                    // 여기 단순히 date가 아니라 20616 같은 경우도 있음... -> string 4글자 잘라서 int로 넣자
                    Integer releaseYear = Integer.parseInt(
                        ObjectAlbum.get("release_date").getAsString().substring(0, 4));
                    // 앨범아트가 없는 경우도 있다?
                    String imgUrl = "NULL";
                    if (!ObjectAlbum.get("images").getAsJsonArray().isEmpty()) {
                        imgUrl = ObjectAlbum.get("images").getAsJsonArray().get(0).getAsJsonObject()
                            .get("url").getAsString();
                    }
                    URL AlbumURL = new URL(
                        "https://api.spotify.com/v1/albums/" + albumId + "?market=KR");
                    /* 앨범 ID 검색으로 받아야 할 것
                     * : 각 트랙의 ID, 각 트랙의 이름, 노래길이, 미리보기 URL */
                    sb = new StringBuilder();
                    conn = (HttpURLConnection) AlbumURL.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Authorization", "Bearer " + Atoken);
                    if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                        br = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                            StandardCharsets.UTF_8));
                    } else if (conn.getResponseCode() == 429) {
                        System.out.println("너무 자주했어요 잠깐쉽니다");
                        Thread.sleep(10000);
                        albumIndex--;
                        continue;
                        // 너무 자주 api 보냈을 때
                    } else if (conn.getResponseCode() == 401) {
                        // 토큰 문제
                        System.out.println("한시간 됐네요 토큰 갱신합니다.");
                        changeToken();
                        albumIndex--;
                        continue;
                    } else {
                        System.out.println("앨범 오류남" + conn.getResponseMessage());
                        break;
                    }
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    br.close();
                    conn.disconnect();
                    text = sb.toString();
                    JsonObject trackObject = parser.parse(text).getAsJsonObject();
                    JsonArray genres = trackObject.get("genres").getAsJsonArray();
                    String genre = null;
                    if (!genres.isEmpty()) {
                        genre = genres.get(0).getAsString();
                    }
                    JsonArray tracks = trackObject.get("tracks").getAsJsonObject()
                        .getAsJsonArray("items");
                    for (int trackIndex = 0; trackIndex < tracks.size(); trackIndex++) {
                        JsonObject ObjectTrack = tracks.get(trackIndex).getAsJsonObject();
                        String trackId = ObjectTrack.get("id").getAsString();
                        // 한글가능성
                        String trackName = ObjectTrack.get("name").getAsString();
                        Integer duration = ObjectTrack.get("duration_ms").getAsInt();
                        String previewURL = null;
                        if (!ObjectTrack.get("preview_url").isJsonNull()) {
                            previewURL = ObjectTrack.get("preview_url").getAsString();
                        }
                        /* 여기서부터는 인기도 받아오기위한 getTrack */
                        URL TrackURL = new URL("https://api.spotify.com/v1/tracks/" + trackId);
                        sb = new StringBuilder();
                        conn = (HttpURLConnection) TrackURL.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setRequestProperty("Authorization", "Bearer " + Atoken);
                        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                            br = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                                StandardCharsets.UTF_8));
                        } else if (conn.getResponseCode() == 429) {
                            System.out.println("너무 자주했어요 잠깐쉽니다");
                            Thread.sleep(10000);
                            trackIndex--;
                            continue;
                            // 너무 자주 api 보냈을 때
                        } else if (conn.getResponseCode() == 401) {
                            // 토큰 문제
                            System.out.println("한시간 됐네요 토큰 갱신합니다.");
                            changeToken();
                            trackIndex--;
                            continue;
                        } else {
                            System.out.println("인기도 오류남" + conn.getResponseMessage());
                            break;
                        }
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        br.close();
                        conn.disconnect();
                        text = sb.toString();
                        JsonObject PopularityObject = parser.parse(text).getAsJsonObject();
                        Integer popularity = PopularityObject.get("popularity").getAsInt();

                        /* 여기서부터는 노래 분석 */
                        URL FeatureURL = new URL(
                            "https://api.spotify.com/v1/audio-features/" + trackId);
                        /* 노래분석으로 받아야 할 것
                         * audio feature 정보들 */
                        sb = new StringBuilder();
                        conn = (HttpURLConnection) FeatureURL.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setRequestProperty("Authorization", "Bearer " + Atoken);
                        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                            br = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                                StandardCharsets.UTF_8));
                        } else if (conn.getResponseCode() == 429) {
                            System.out.println("너무 자주했어요 잠깐쉽니다");
                            Thread.sleep(10000);
                            trackIndex--;
                            continue;
                            // 너무 자주 api 보냈을 때
                        } else if (conn.getResponseCode() == 401) {
                            // 토큰 문제
                            changeToken();
                            trackIndex--;
                            continue;
                        } else {
                            System.out.println("노래분석 오류남" + conn.getResponseCode());
                            break;
                        }
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        br.close();
                        conn.disconnect();
                        text = sb.toString();
                        JsonObject featureObject = parser.parse(text).getAsJsonObject();
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
                        registInfo.setReleaseYear(releaseYear);
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
                        registInfo.setTodaySong(false);
                        songService.registSong(registInfo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void deleteDuplicates() throws IOException {
        FileInputStream file = new FileInputStream("C:\\dev\\A304\\crawling\\singer.xlsx");
        IOUtils.setByteArrayMaxOverride(Integer.MAX_VALUE);
        workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        for (int sheetIndex = 0; sheetIndex < startIndex; sheetIndex++) {
            String artistId = sheet.getRow(sheetIndex).getCell(0).getStringCellValue();
            List<SongFindRes> songs = songService.findSongByArtistId(artistId);
            HashMap<String, Integer> map = new HashMap<>();
            for (SongFindRes song : songs) {
                if (song.getName().contains("(live)") || song.getName().contains("(Live)")
                    || song.getName().contains("(inst)") || song.getName().contains("(Inst)")
                    || song.getName().contains("inst.") || song.getName().contains("Inst.")
                    || song.getName().contains("(MR)") || song.getName().contains("instrumental")) {
                    songService.deleteSong(song.getId());
                }
                if (map.containsKey(song.getName())) {
                    if (map.get(song.getName()) > song.getPopularity()) {
                        songService.deleteSong((song.getId()));
                    } else {
                        map.put(song.getName(), song.getPopularity());
                        // 이 때 이미 map 에 들어있던 노래의 id 로 삭제해야하는데 방법은?
                        // String인 id 값과 Integer인 populartiy를 같은 value로 보관할 방법
                        // String 배열로 넣어서 Integer로 string 으로 바꾸기?
//                        songService.deleteSong();
                    }
                } else {
                    map.put(song.getName(), song.getPopularity());
                }
            }
        }
        file.close();
    }
}

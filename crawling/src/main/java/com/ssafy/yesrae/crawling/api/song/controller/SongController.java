package com.ssafy.yesrae.crawling.api.song.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

    public void getToken(){
        try {
            URL url = new URL("https://accounts.spotify.com/api/token");
            String line;
            sb = new StringBuilder();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
            URL url = new URL("https://accounts.spotify.com/api/token");
            String line;
            sb = new StringBuilder();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
    public void registSong(){
        try {
            FileInputStream file = new FileInputStream("C:\\dev\\A304\\crawling\\singer.xlsx");
            IOUtils.setByteArrayMaxOverride(Integer.MAX_VALUE);

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
                    switch (cell.getCellType()) {
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + cell.getCellType());
                    }
                }
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

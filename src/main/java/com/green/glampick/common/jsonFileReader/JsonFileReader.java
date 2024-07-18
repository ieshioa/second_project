package com.green.glampick.common.jsonFileReader;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonFileReader {
//
//    public void insert(){
//        Map r = new HashMap<>();
//        // 파일 경로 지정
//        String filePath = "C:\\Users\\Administrator\\Downloads\\camping_data_select.json";
//        String s = "경주 스카이글램핑";
//
//        String glampName = "";
//        String glampLocation = "";
//        String parking = "";
//        String basic = "";
//
//
//
//        try (FileReader reader = new FileReader(filePath)) {
//            // JSONParser 인스턴스 생성
//            JSONParser jsonParser = new JSONParser();
//            Object obj = jsonParser.parse(reader);
//
//            // JSON 데이터가 배열인지 객체인지 확인
//            if (obj instanceof JSONArray) {
//                JSONArray jsonArray = (JSONArray) obj;
//                for (Object jsonObject : jsonArray) {
//                    glampName = parseCampingDataName((JSONObject) jsonObject,glampName);
//                    glampLocation = parseCampingDataLocation((JSONObject) jsonObject,s, glampLocation);
//                    parking = parseCampingDataParking((JSONObject) jsonObject,s, parking);
//                    basic = parseCampingDataBasic((JSONObject) jsonObject,s, basic);
//                    GlampUPdate p = new GlampUPdate(glampName, glampLocation, parking, basic);
//                    if(mapper.selGlamp(glampName) != null) {
//                        mapper.updateGlamp(p);
//                    }
//                    glampName = "";
//                    glampLocation = "";
//                    parking = "";
//                    basic = "";
//                }
//            } else if (obj instanceof JSONObject) {
//                parseCampingDataName((JSONObject) obj,s);
//                System.out.println("========");
//            }
//        } catch (IOException | ParseException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(glampName + " + " + glampLocation + " + " + parking + " + " + basic);
//
//        r.put("glampName", glampName);
//        r.put("glampLocation", glampLocation);
//        r.put("parking", parking);
//        r.put("basic", basic);
//
//
//    }

    private static void parseCampingData(JSONObject jsonObject, String searchGlampName) {
        String glamp_name = (String) jsonObject.get("glamp_name");

        if (glamp_name.equals(searchGlampName)) {
            Long pk = (Long) jsonObject.get("pk");
            String glampLocation = (String) jsonObject.get("glamp_location");

            JSONObject room = (JSONObject) jsonObject.get("room");

            JSONArray infoParking = (JSONArray ) jsonObject.get("info_parking");
            JSONArray infoBasic = (JSONArray ) jsonObject.get("info_basic");
            JSONArray images = (JSONArray) room.get("images");


            System.out.println("PK: " + pk);
            System.out.println("Glamp Name: " + glamp_name);
            System.out.println("Glamp Location: " + glampLocation);


            System.out.println("Room Name: " + room.get("name"));
            System.out.println("Room Price: " + room.get("price"));
            System.out.println("Room checkIn: " + room.get("check_in_time"));
            System.out.println("Room checkOut: " + room.get("check_out_time"));
            System.out.println("Room numPeople: " + room.get("room_num_people"));
            System.out.println("Room maxPeople: " + room.get("room_max_people"));
            room.get("check_in_time");
            String setInfoParkInfo = "";

            for (Object parkingInfo : infoParking) {
                setInfoParkInfo += parkingInfo + "\n";
            }
            setInfoParkInfo = setInfoParkInfo.trim();
            System.out.println(setInfoParkInfo);

            String setBasicInfo = "";
            for (Object basicInfo : infoBasic) {
                setBasicInfo += basicInfo + "\n";
            }
            setBasicInfo = setBasicInfo.trim();
            System.out.println(setBasicInfo);

            for (Object item : images) {
                String image = (String)item;
                System.out.println(image);
            }

        }
        // 나머지 필드들에 대해 동일하게 처리할 수 있습니다.
    }
}

package com.green.glampick.common.jsonFileReader;


import com.green.glampick.dto.object.room.RoomInfoDto;
import com.green.glampick.mapper.JsonInputMapper;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;


import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
@RequiredArgsConstructor
@Component
public class JsonFileReader {
    private final JsonInputMapper mapper;
    public void insert(){
        // 파일 경로 지정
        String filePath = "C:\\Users\\Administrator\\Downloads\\camping_data.json";

        try (FileReader reader = new FileReader(filePath)) {
            // JSONParser 인스턴스 생성
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);

            // JSON 데이터가 배열인지 객체인지 확인
            if (obj instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) obj;
                for (Object jsonObject : jsonArray) {

                    RoomInfoDto roomInfoDto = parseCampingData((JSONObject) jsonObject);
                        mapper.insRoomData(roomInfoDto);
                }
            } else if (obj instanceof JSONObject) {
                System.out.println("========");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    private static RoomInfoDto parseCampingData(JSONObject jsonObject) {
        String glamp_name = (String) jsonObject.get("glamp_name");
        RoomInfoDto roomInfoDto = new RoomInfoDto();

            Long pk = (Long) jsonObject.get("pk");
            JSONObject room = (JSONObject) jsonObject.get("room");

            String checkIn = (String)room.get("check_in_time");
            String checkOut = (String)room.get("check_out_time");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime checkInTime = LocalTime.parse(checkIn, formatter);
            LocalTime checkOutTime = LocalTime.parse(checkOut, formatter);

            roomInfoDto.setGlampId(pk.intValue());
            roomInfoDto.setName((String)room.get("name"));
            roomInfoDto.setPrice(((Long) room.get("price")).intValue());
            roomInfoDto.setNumPeople(((Long) room.get("room_num_people")).intValue());
            roomInfoDto.setMaxPeople(((Long) room.get("room_max_people")).intValue());
            roomInfoDto.setCheckIn(checkInTime);
            roomInfoDto.setCheckOut(checkOutTime);


//            System.out.println("Room Name: " + room.get("name"));
//            System.out.println("Room Price: " + room.get("price"));
//            System.out.println("Room checkIn: " + room.get("check_in_time"));
//            System.out.println("Room checkOut: " + room.get("check_out_time"));
//            System.out.println("Room numPeople: " + room.get("room_num_people"));
//            System.out.println("Room maxPeople: " + room.get("room_max_people"));
//            room.get("check_in_time");

        return roomInfoDto;
    }
}

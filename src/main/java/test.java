
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

class test {
    public static void main(String[] args) throws IOException {

        System.out.print("Mission1 데이터베이스 접속 : ");

        String sql = "Replace into WiFi(X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, " +
                "X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, " +
                "X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, " +
                "X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


        int startNum = 1;
        int endNum = 10;
        int count = 0;

        try {

            String dbURL = "jdbc:mariadb://192.168.0.21:3306/wifi_service";
            String dbUserId = "wifi_user";
            String dbPassword = "wifi1234";


            while(true) {
                /*  URL 생성  */
                // 1. URL을 만들기 위한 StringBuilder
                StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
                // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
                urlBuilder.append("/" + URLEncoder.encode("47436a734c6b64613736786e494256","UTF-8") );  /*인증키*/
                urlBuilder.append("/" + URLEncoder.encode("json","UTF-8") );    /*요청파일타입 (xml,xmlf,xls,json) */
                urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8"));     /*서비스명 (대소문자 구분)*/
                urlBuilder.append("/" + URLEncoder.encode(String.valueOf(startNum),"UTF-8"));    /*요청시작위치 */
                urlBuilder.append("/" + URLEncoder.encode(String.valueOf(endNum),"UTF-8"));     /*요청종료위치*/

                // 3. URL 객체 생성
                URL url = new URL(urlBuilder.toString());
                // 4. 요청 URL과 통신하기 위한 Connection 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 5. 통신을 위한 메소드 SET - GET 방식
                conn.setRequestMethod("GET");
                // 6. 통신을 위한 Content-type SET - json
                conn.setRequestProperty("Content-type", "application/json");
                // 7. 통신 응답 코드 확인.
                System.out.println("Response code: " + conn.getResponseCode()); /* 연결자체에 대한 확인 */


                // 8. 전달받은 데이터를 BufferedReader 객체로 저장.
                BufferedReader rd;

                // 서비스코드가 정상이면 200~300사이의 숫자가 나온다.
                if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }


                // 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }

                // 10. 객체 해제.
                rd.close();
                conn.disconnect();

                // JSON parser를 생성해 문자열 데이터를 JSON 객체화
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(sb.toString());  // JSON 객체는 JSONObject 클래스를 사용해서 저장
                JSONObject TbPublicWifiInfo = (JSONObject) jsonObject.get("TbPublicWifiInfo");
//                Long list_total_count = (Long) TbPublicWifiInfo.get("list_total_count");
                int list_total_count = Integer.valueOf(TbPublicWifiInfo.get("list_total_count").toString());
                JSONArray arry = (JSONArray) TbPublicWifiInfo.get("row");

                System.out.println("전체 데이터: " + list_total_count + "개");


                for (int i = 0; i < arry.size(); i++) {
                    JSONObject wifi = (JSONObject) arry.get(i);
                    String X_SWIFI_MGR_NO = (String) wifi.get("X_SWIFI_MGR_NO");            // 관리번호
                    String X_SWIFI_WRDOFC = (String) wifi.get("X_SWIFI_WRDOFC");            // 자치구
                    String X_SWIFI_MAIN_NM = (String) wifi.get("X_SWIFI_MAIN_NM");          // 와이파이명
                    String X_SWIFI_ADRES1 = (String) wifi.get("X_SWIFI_ADRES1");            // 도로명주소
                    String X_SWIFI_ADRES2 = (String) wifi.get("X_SWIFI_ADRES2");            // 상세주소
                    String X_SWIFI_INSTL_FLOOR = (String) wifi.get("X_SWIFI_INSTL_FLOOR");  // 설치위치(층)
                    String X_SWIFI_INSTL_TY = (String) wifi.get("X_SWIFI_INSTL_TY");       // 설치유형
                    String X_SWIFI_INSTL_MBY = (String) wifi.get("X_SWIFI_INSTL_MBY");     // 설치기관
                    String X_SWIFI_SVC_SE = (String) wifi.get("X_SWIFI_SVC_SE");            // 서비스구분
                    String X_SWIFI_CMCWR = (String) wifi.get("X_SWIFI_CMCWR");              // 망종류
                    String X_SWIFI_CNSTC_YEAR = (String) wifi.get("X_SWIFI_CNSTC_YEAR");    // 설치년도
                    String X_SWIFI_INOUT_DOOR = (String) wifi.get("X_SWIFI_INOUT_DOOR");    // 실내외구분
                    String X_SWIFI_REMARS3 = (String) wifi.get("X_SWIFI_REMARS3");          // wifi 접속환경
                    String LAT = (String) wifi.get("LAT");                                  // Y 좌표
                    String LNT = (String) wifi.get("LNT");                                  // X 좌표
                    String WORK_DTTM = (String) wifi.get("WORK_DTTM");                      // 작업일자

                    //StringBuffer를 통해서 원하는 양식에 맞게 출력
                    StringBuffer sbp = new StringBuffer();
                    sbp.append("관리번호 : " + X_SWIFI_MGR_NO + ", 자치구 : " + X_SWIFI_WRDOFC + ", 와이파이명 : " + X_SWIFI_MAIN_NM
                            + ", 도로명주소 : " + X_SWIFI_ADRES1 + ", 상세주소 : " + X_SWIFI_ADRES2 + ", 설치위치(층) : " + X_SWIFI_INSTL_FLOOR
                            + ", 설치유형 : " + X_SWIFI_INSTL_TY + ", 설치기관 : " + X_SWIFI_INSTL_MBY + ", 서비스구분 : " + X_SWIFI_SVC_SE
                            + ", 망종류 : " + X_SWIFI_CMCWR + ", 설치년도 : " + X_SWIFI_CNSTC_YEAR + ", 실내외구분 : " + X_SWIFI_INOUT_DOOR
                            + ", wifi 접속환경 : " + X_SWIFI_REMARS3 + ", X좌표 : " + LAT + ", Y좌표 : " + LNT + ", 작업일자 : " + WORK_DTTM);


                    System.out.println(sbp);

//                    pstmt.setString(1, x_swifi_mgr_no);
//                    pstmt.setString(2,x_swifi_wrdofc);
//                    pstmt.setString(3, x_swifi_main_mn);
//                    pstmt.setString(4, x_swifi_adres1);
//                    pstmt.setString(5, x_swifi_adres2);
//                    pstmt.setString(6, x_swifi_instl_floor);
//                    pstmt.setString(7, x_swifi_instrl_ty);
//                    pstmt.setString(8, x_swifi_instrl_mby);
//                    pstmt.setString(9, x_swifi_svc_se);
//                    pstmt.setString(10, x_swifi_cmcwr);
//                    pstmt.setString(11, x_swifi_cnstc_year);
//                    pstmt.setString(12, x_swifi_inout_door);
//                    pstmt.setString(13, x_swifi_remars3);
//                    pstmt.setString(14, lat);
//                    pstmt.setString(15, lnt);
//                    pstmt.setString(16, work_dttm);
//
//                    pstmt.executeUpdate();
                    count++;
                }

                startNum += 1000;
                endNum += 1000;
                if (count == list_total_count) {
                    break;
                }

            }

        }  catch (Exception e) {
            e.printStackTrace();
        }



    }
}
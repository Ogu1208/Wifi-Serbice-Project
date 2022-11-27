public class WifiService {

    public List<WifiInfo> list() {
// 5개
        // 1. ip (domain)
        // 2. port
        // 3. 계정
        // 4. 패스워드
        // 5. 인스턴스

        List<Member> memberList = new ArrayList<>();


        String url = "jdbc:mariadb://192.168.0.21:3306/testdb3";
        String dbUserId = "Ogu1208";
        String dbPassword = "zerobase";

        // 1. 드라이버 로드
        // 2. 커넥션 객체 생성
        // 3. 스테이트먼트 객체 생성
        // 4. 쿼리 실행
        // 5. 결과 수행
        // 6. 객체 연결 해제 (close)

        // 1. 드라이버 로드
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet rs = null;

        // email, kakao, facebook
        // sql injection 공격
        String memberTypeValue = "email";

        try {
            // 2. 커넥션 객체 생성
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            // 3. 스테이트먼트 객체 생성

            // 4. 쿼리 실행
            String sql = "select member_type, user_id, password, name " +
                    " from member2 " +
                    " where member_type = ? ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, memberTypeValue);   // parameter는 1부터 시작

            rs = preparedStatement.executeQuery();

            // 5. 결과 수행
            while (rs.next()) {
                String memberType = rs.getString("member_type");
                String userId = rs.getString("user_id");
                String password = rs.getString("password");
                String name = rs.getString("name");


                Member member = new Member();
                member.setMemberType(memberType);
                member.setUserId(userId);
                member.setPassword(password);
                member.setName(name);

                memberList.add(member);

                System.out.println(memberType + ", " + userId + ", " + password + ", " + name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 6. 객체 연결 해제 (close)
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                    ;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return memberList;

    }
}

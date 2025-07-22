import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Posts_Chapter07 {
    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement insertStatement = null;

        String[][] postList = {
            { "1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13" },
            { "1002", "2023-02-08", "お疲れ様です！", "12" },
            { "1003", "2023-02-09", "今日も頑張ります！", "18" },
            { "1001", "2023-02-09", "無理は禁物ですよ！", "17" },
            { "1002", "2023-02-10", "明日から連休ですね！", "20" }
        };

        try {
            // データベースに接続
            con = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1/java_db",
                    "root",
                    "");

            System.out.println("データベース接続成功");

            // INSERTクエリの準備
            String insertSQL = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?);";
            insertStatement = con.prepareStatement(insertSQL);

            // データを追加
            for (int i = 0; i < postList.length; i++) {
                insertStatement.setString(1, postList[i][0]);
                insertStatement.setString(2, postList[i][1]);
                insertStatement.setString(3, postList[i][2]);
                insertStatement.setString(4, postList[i][3]);

                int rowCnt = insertStatement.executeUpdate();
                System.out.println(rowCnt + "件のレコードが追加されました");

                // レコードを追加した後に条件に応じて検索する
                if (i == postList.length - 1) {
                    searchUsers(con); // データベースに接続した状態で別のメソッドで検索を行う
                }
            }

        } catch (SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException ignore) {}
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ignore) {}
            }
        }
    }

    // データベースからユーザーを検索するメソッド
    private static void searchUsers(Connection con) {
        PreparedStatement selectStatement = null;
        try {
            // SELECTクエリの準備
            String selectSQL = "SELECT * FROM users WHERE age >= 25;";
            selectStatement = con.prepareStatement(selectSQL);

            // 検索の実行
            ResultSet result = selectStatement.executeQuery();

            // 結果の表示
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int age = result.getInt("age");
                System.out.println(result.getRow() + "件目：id=" + id
                                   + "／name=" + name + "／age=" + age );
            }
        } catch (SQLException e) {
            System.out.println("検索エラー発生：" + e.getMessage());
        } finally {
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException ignore) {}
            }
        }
    }
}
package text.section_05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbInsert_1 {
    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement statement = null;

        // ユーザーリスト
        String[][] userList = {
            { "侍一郎", "28" },
            { "侍花子", "24" },
            { "侍二郎", "26" },
            { "侍寺子", "37" },
            { "侍三郎", "21" }
        };

        try {
            // データベースに接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/java_db",
                "root",
                ""
            );

            System.out.println("データベース接続成功");

            // SQLクエリを準備
            String sql = "INSERT INTO users (name, age) VALUES (?, ?);";
            statement = con.prepareStatement(sql);

            // リストの1行目から順番に読み込む
            int rowCnt = 0;
            for( int i = 0; i < userList.length; i++ ) {
                // SQLクエリの「?」部分をリストのデータに置き換え
                statement.setString(1, userList[i][0]); // 名前
                statement.setString(2, userList[i][1]); // 年齢

                // SQLクエリを実行（DBMSに送信）
                System.out.println("レコード追加:" + statement.toString() );
                rowCnt = statement.executeUpdate();
                System.out.println( rowCnt + "件のレコードが追加されました");
            }
        } catch(SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            if( statement != null ) {
                try { statement.close(); } catch(SQLException ignore) {}
            }
            if( con != null ) {
                try { con.close(); } catch(SQLException ignore) {}
            }
        }
    }
}
package text.section_04;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DbTable_1 {
    public static void main(String[] args) {

        Connection con = null;
        Statement statement = null;

        try {
            // データベースに接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/java_db",
                "root",
                ""
            );

            System.out.println("データベース接続成功");

            // SQLクエリを準備
            statement = con.createStatement();
            String sql = """
                         CREATE TABLE users (
                           id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(60) NOT NULL,
                           age INT(11)
                         );
                         """;

            //　SQLクエリを実行（DBMSに送信）
            int rowCnt = statement.executeUpdate(sql);
            System.out.println("テーブルを作成:rowCnt=" + rowCnt );
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
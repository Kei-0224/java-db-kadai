package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {
	public static void main(String[] args) {

		Connection con = null;
		Statement statement = null;

		try {
			// データベース接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/java_db",
					"root",
					"");

			System.out.println("データベース接続成功");

			statement = con.createStatement();

			String updateSql = "UPDATE scores SET score_math = 80, score_english = 95 WHERE id = 5";

			int rowCnt = statement.executeUpdate(updateSql);
			System.out.println(rowCnt + "件のレコードが更新されました");

			String selectSql = "SELECT * FROM scores ORDER BY score_math DESC, score_english DESC";
			ResultSet rs = statement.executeQuery(selectSql);

			System.out.println("=== 並び替え結果 ===");
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int math = rs.getInt("score_math");
				int english = rs.getInt("score_english");
				System.out.println(id + " | " + name + " | 数学:" + math + " | 英語:" + english);
			}
			rs.close();

		} catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
			// 使用したオブジェクトを解放
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
	}
}
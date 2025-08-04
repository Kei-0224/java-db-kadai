package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Posts_Chapter07 {
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement insertStatement = null;
		PreparedStatement selectStatement = null;

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
					"jdbc:mysql://127.0.0.1/challenge_java",
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

			}
			// SELECTクエリの準備
			String selectSQL = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = ? ORDER BY posted_at ASC;";
			selectStatement = con.prepareStatement(selectSQL);
			selectStatement.setString(1, "1002");

			ResultSet result = selectStatement.executeQuery();

			System.out.println("ユーザーIDが1002のレコードを検索しました");

			int count = 1;
			while (result.next()) {
				String postedAt = result.getString("posted_at");
				String content = result.getString("post_content");
				int likes = result.getInt("likes");

				System.out.println(count + "件目：投稿日時=" + postedAt +
						"／投稿内容=" + content +
						"／いいね数=" + likes);
				count++;
			}
		} catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
			if (insertStatement != null)
				try {
					insertStatement.close();
				} catch (SQLException ignore) {
				}
			if (selectStatement != null)
				try {
					selectStatement.close();
				} catch (SQLException ignore) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ignore) {
				}
		}
	}
}

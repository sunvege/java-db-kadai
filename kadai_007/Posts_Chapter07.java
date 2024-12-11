package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_Chapter07 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Connection con = null;
        Statement statement = null;
        PreparedStatement selectStatement = null;
        
        try {
            // データベースに接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "Coco6-----"
            );

            System.out.println("データベース接続成功：" + con);

			//SQLクエリINSERTの実行
            statement = con.createStatement();
            String insertSql = 
            		"""
            		INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES 
            		(1003, '2023-02-08', '昨日の夜は徹夜でした・・・', 13),
        		    (1002, '2023-02-08', 'お疲れ様です！', 12),
        		    (1003, '2023-02-09', '今日も頑張ります！', 18),
        		    (1001, '2023-02-09', '無理は禁物ですよ！', 17),
        		    (1002, '2023-02-10', '明日から連休ですね！', 20);
            		
            		""";
            
            System.out.println("レコード追加を実行します");
            int rowCnt = statement.executeUpdate(insertSql);
            System.out.println( rowCnt + "件のレコードが追加されました");
            
            //SQLクエリSELECTの実行
			System.out.println("ユーザーIDが1002のレコードを検索しました");
            String selectSql = "SELECT * FROM posts WHERE user_id = 1002;";
            selectStatement = con.prepareStatement(selectSql);
            ResultSet result = selectStatement.executeQuery(selectSql);

            
            //レコード番号の出力
            int rowNumber = 1;
            while(result.next()) {
            	Date time = result.getDate("posted_at");
            	String content = result.getString("post_content");
            	int likes = result.getInt("likes");
            	

            System.out.println( rowNumber + "件目：投稿日時=" + time + "/投稿内容=" + content + "/いいね数=" + likes );
            rowNumber ++;
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

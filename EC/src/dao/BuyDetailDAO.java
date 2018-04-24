package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import base.DBManager;
import beans.BuyDetailDataBeans;
import beans.ItemDataBeans;

public class BuyDetailDAO {
	//インスタンスオブジェクトを返却させてコードの簡略化
	public static BuyDetailDAO getInstance() {
		return new BuyDetailDAO();
	}

	/**
	 * 購入詳細登録処理
	 * @param bddb BuyDetailDataBeans
	 * @throws SQLException
	 * 			呼び出し元にスローさせるため
	 */
	public static void insertBuyDetail(BuyDetailDataBeans bddb) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement(
					"INSERT INTO t_buy_detail(buy_id,item_id) VALUES(?,?)");
			st.setInt(1, bddb.getBuyId());
			st.setInt(2, bddb.getItemId());
			st.executeUpdate();
			System.out.println("inserting BuyDetail has been completed");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	/**
	 * 購入IDによる購入情報検索
	 * @param buyId
	 * @return {BuyDataDetailBeans}
	 * @throws SQLException
	 */
	public ArrayList<BuyDetailDataBeans> getBuyDataBeansListByBuyId(int buyId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();

			st = con.prepareStatement("SELECT * FROM t_buy_detail WHERE buy_id = ?");
			st.setInt(1, buyId);

			ResultSet rs = st.executeQuery();
			ArrayList<BuyDetailDataBeans> buyDetailList = new ArrayList<BuyDetailDataBeans>();

			while (rs.next()) {
				BuyDetailDataBeans bddb = new BuyDetailDataBeans();
				bddb.setId(rs.getInt("id"));
				bddb.setBuyId(rs.getInt("buy_id"));
				bddb.setItemId(rs.getInt("item_id"));
				buyDetailList.add(bddb);
			}

			System.out.println("searching BuyDataBeansList by BuyID has been completed");
			return buyDetailList;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	 /**
     * 購入IDによる購入詳細情報検索
     * @param buyId
     * @return buyDetailItemList ArrayList<ItemDataBeans>
     *             購入詳細情報のデータを持つJavaBeansのリスト
     * @throws SQLException
     */
	public static ArrayList<ItemDataBeans> getItemDataBeansListByBuyId(int buyId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();

			st = con.prepareStatement(
					"SELECT m_item.id,"
					+ " m_item.name,"
					+ " m_item.price"
					+ " FROM t_buy_detail"
					+ " JOIN m_item"
					+ " ON t_buy_detail.item_id = m_item.id"
					+ " WHERE t_buy_detail.buy_id = ?");
			st.setInt(1, buyId);

			ResultSet rs = st.executeQuery();
			ArrayList<ItemDataBeans> buyDetailItemList = new ArrayList<ItemDataBeans>();

			while (rs.next()) {
				ItemDataBeans idb = new ItemDataBeans();
				idb.setId(rs.getInt("id"));
				idb.setName(rs.getString("name"));
				idb.setPrice(rs.getInt("price"));


				buyDetailItemList.add(idb);
			}

			System.out.println("searching ItemDataBeansList by BuyID has been completed");
			return buyDetailItemList;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

//	 /**
//     * 購入IDによる購入詳細情報検索 購入詳細上部分		4/23作成中
//     * @param buyId
//     * @return buyHistoryBeans
//     * @throws SQLException
//     */
//	public BuyDataBeans getBuyDataBeansByBuyId(String buyId) throws SQLException {
//		Connection con = null;
//		PreparedStatement st = null;
//		try {
//			con = DBManager.getConnection();
//
//			st = con.prepareStatement(
//				"SELECT * FROM t_buy"
//				+ " JOIN m_delivery_method"
//				+ " ON t_buy.delivery_method_id = m_delivery_method.id"
//				+ " WHERE t_buy.id = ?");
//					st.setString(1, buyId);
//			
//					ResultSet rs = st.executeQuery();
//
//
//			if(!rs.next()) {
//				return null;
//			}
//			 Date buyDateData = rs.getDate("create_date");
//	         String deliveryMethodNameData = rs.getString("name");
//	         int totalPriceData = rs.getInt("total_price");
//	         return new BuyHistoryBeans(buyDateData,deliveryMethodNameData,totalPriceData);
//
//			} catch (SQLException e) {
//			System.out.println(e.getMessage());
//			throw new SQLException(e);
//			} finally {
//				if (con != null) {
//					con.close();
//				}
//			}
//		}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
	//	 /**
//     * 購入IDによる購入履歴詳細情報検索
//     * @param buyId
//     * @return buyDetailItemList ArrayList<ItemDataBeans>
//     *             購入詳細情報のデータを持つJavaBeansのリスト
//     * @throws SQLException
//     */
//	public ArrayList<BuyHistoryBeans> getBuyHistoryBeansListByBuyId(String buyId) throws SQLException {
//		Connection con = null;
//		PreparedStatement st = null;
//		try {
//			con = DBManager.getConnection();
//
//			st = con.prepareStatement(
//					"SELECT m_item.name,"
//					+ "m_item.price,"
//					+"t_buy.create_date,"
//					+"t_buy.total_price,"
//					+"m_delivery_method.name,"
//					+ "m_delivery_method.price"
//					+ " FROM t_buy_detail"
//					+ " JOIN m_item"
//					+ " ON t_buy_detail.item_id = m_item.id"
//					+ " JOIN t_buy"
//					+ " ON t_buy_detail.buy_id = t_buy.id"
//					+ " JOIN m_delivery_method"
//					+ " ON m_delivery_method.id = t_buy.delivery_method_id"
//					+ " WHERE t_buy_detail.buy_id = ?");
//			
//			st.setString(1, buyId);
//
//			ResultSet rs = st.executeQuery();
//			ArrayList<BuyHistoryBeans> buyHistoryDetailList = new ArrayList<BuyHistoryBeans>();
//
//			while (rs.next()) {
//				BuyHistoryBeans bhb = new BuyHistoryBeans();
//				bhb.setTotalPrice(rs.getInt("totalPrice"));
//				bhb.setBuyDate(rs.getDate("buyDate"));
//				bhb.setItemName(rs.getString("itemName"));
//				bhb.setItemPrice(rs.getString("itemPrice"));
//				bhb.setDeliveryMethodName(rs.getString("deliveryMethodName"));
//				bhb.setDeliveryMethodPrice(rs.getInt("deliveryMethodPrice"));
//
//
//				buyHistoryDetailList.add(bhb);
//			}
//
//			System.out.println("searching BuyHistoryDetailList by BuyID has been completed");
//			return buyHistoryDetailList;
//		
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//			throw new SQLException(e);
//		} finally {
//			if (con != null) {
//				con.close();
//			}
//		}
//	}
	
}

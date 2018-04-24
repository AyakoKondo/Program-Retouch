package ec;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BuyDataBeans;
import beans.ItemDataBeans;
import dao.BuyDAO;
import dao.BuyDetailDAO;

/**
 * 購入履歴画面
 * @author d-yamaguchi
 *
 */
@WebServlet("/UserBuyHistoryDetail")
public class UserBuyHistoryDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// セッション開始
		HttpSession session = request.getSession();
		
		try {
			// URLからGETパラメータとしてbuyIdを受け取る
			int buyId = Integer.parseInt(request.getParameter("buy_id"));
			
			//buyIdを引数にわたしてBuyDataBeansを取得
			BuyDataBeans historyBDB = BuyDAO.getBuyDataBeansByBuyId(buyId);
			
			ArrayList<ItemDataBeans> historyIDBList = BuyDetailDAO.getItemDataBeansListByBuyId(buyId);
			
			
			//購入情報をリクエストスコープにセットしてjspにフォワード
			session.setAttribute("historyBDB", historyBDB);
			session.setAttribute("historyIDBList", historyIDBList);

			
			request.getRequestDispatcher(EcHelper.USER_BUY_HISTORY_DETAIL_PAGE).forward(request, response);

		}catch (Exception e) {
		e.printStackTrace();
		session.setAttribute("errorMessage", e.toString());
		response.sendRedirect("Error");
		}
	}
}

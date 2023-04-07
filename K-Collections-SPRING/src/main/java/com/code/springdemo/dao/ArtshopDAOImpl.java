package com.code.springdemo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.code.springdemo.entity.Artwork;
import com.code.springdemo.entity.Authorities;
import com.code.springdemo.entity.Cart;
import com.code.springdemo.entity.Category;
import com.code.springdemo.entity.Sales;
import com.code.springdemo.entity.Users;
import com.code.springdemo.entity.Wishlist;

@Repository
public class ArtshopDAOImpl implements ArtshopDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private DataSource dataSource;
	
	//LoginController ====================================================================================
	//권한가져 오기
	@Override
	public String getAuth(String username) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<String> theQuery = currentSession
				.createQuery("Select authority from Authorities where userName = '" + username + "'");
		List<String> authorities = theQuery.getResultList();

		if (authorities.contains("ROLE_ADMIN")) {
			return "관리자";
		} else if (authorities.contains("ROLE_VVIP")) {
			return "VVIP";
		} else if (authorities.contains("ROLE_VIP")) {
			return "VIP";
		} else {
			return "일반";
		}
	}
	// 회원 가입----------------------------------------------------------
	@Override
	public void register(Users theuser, Authorities theauthorities) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(theuser);
		currentSession.saveOrUpdate(theauthorities);
	}
	// 회원 탈퇴----------------------------------------------------------
	@Override
	public void unregister(int theId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Users theQuery = currentSession.get(Users.class, theId);
		currentSession.delete(theQuery);
	}
	// 시큐리티에서 가져온 아이디 정보--------------------------------------------
	@Override
	public int getIntUsers(String userName) {
		System.out.println(userName);
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "from Users where userName = '" + userName + "'";
		Query<Users> theQuery = currentSession.createQuery(hql, Users.class);

		List<Users> theusers = theQuery.getResultList();

		System.out.println(theusers);
		System.out.println(theusers.get(0).getUserNo());

		return theusers.get(0).getUserNo();
	}
	// 회원으로 로그인해서 로그인한 userNo를 가져와서 입력한 정보로 업데이트 하기--------------
	@Override
	public void updateUsers(int theId, Users theusers) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery = currentSession.createQuery(
				"Update Users Set userRealName = :UserRealName, password = :password Where userNo = :UserNo");
		theQuery.setParameter("UserRealName", theusers.getUserRealName());
		theQuery.setParameter("password", theusers.getPassword());
		theQuery.setParameter("UserNo", theId);

		theQuery.executeUpdate();
	}
	//아이디 중복확인
	@Override
	public String duplicationConfirm(String loginId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Users> theQuery = currentSession.createQuery("from users where username = '" + loginId + "'",
				Users.class);
		List<Users> users = theQuery.getResultList();
		Users username = users.get(0);
		String username2 = username.getUserName();

		return username2;
	}
	
	//DemoController =====================================================================================
	// 유저 관리---------------------------------------------------------------------------------------------
	//유저 정보 전부 불러오기
	@Override
	public List<Users> getUsers() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Users> theQuery = currentSession.createQuery("from Users", Users.class);
		List<Users> users = theQuery.getResultList();

		return users;
	}
	//유저no를 통해 선택한 유저 정보만 가져오기
	@Override
	public Users getUsers(int theId) {

		Session currentSession = sessionFactory.getCurrentSession();
		Users theQuery = currentSession.get(Users.class, theId);

		return theQuery;
	}
	//유저정보를 가져와서 유저 수정하기
	@Override
	public void UpdateUser(Users theUser) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.update(theUser);
		String userBeforeName = getUsers(theUser.getUserNo()).getUserName();
		
		Query theQuery = 
				currentSession.createQuery("Update Authorities "
						+ "Set userName = :UserAfterName "
						+ "Where userName = :UserBeforeName");
		theQuery.setParameter("UserAfterName", theUser.getUserName());
		theQuery.setParameter("UserBeforeName", userBeforeName);
		theQuery.executeUpdate();	
	}
	//유저no를 가져와서 유저에 관한 모든 테이블 정보 삭제
	@Override
	public void deleteUser(int theUserId) {
		Session currentSession = sessionFactory.getCurrentSession();

		String userName = getUsers(theUserId).getUserName();

		Query theQuery = currentSession
				.createQuery("Update Sales " + "Set salesUserNo = 1" + "Where salesUserNo = :SalesUserNo");
		theQuery.setParameter("SalesUserNo", theUserId);
		theQuery.executeUpdate();

		theQuery = currentSession.createQuery("Delete from Cart " + "Where cartUserNo = :UserId");
		theQuery.setParameter("UserId", theUserId);
		theQuery.executeUpdate();

		theQuery = currentSession.createQuery("Delete from Wishlist " + "Where wishlistUserNo = :UserId");
		theQuery.setParameter("UserId", theUserId);
		theQuery.executeUpdate();

		theQuery = currentSession.createQuery("from Artwork " + "Where artworkArtistNo = :UserId");
		theQuery.setParameter("UserId", theUserId);

		theQuery = currentSession
				.createQuery("Select artworkNo from Artwork " + "Where artworkArtistNo = " + theUserId);

		List<Integer> theArtworkIds = theQuery.getResultList();
		for (Integer theArtworkId : theArtworkIds)
			deleteArtwork(theArtworkId);

		theQuery = currentSession.createQuery("Delete from Authorities " + "Where userName = :UserName");
		theQuery.setParameter("UserName", userName);
		theQuery.executeUpdate();

		Users User = currentSession.get(Users.class, theUserId);
		currentSession.delete(User);
	}
	//유저 아이디를 통해 검색하기
	@Override
	public List<Users> searchUser(String searchText) {
		Session currentSession = sessionFactory.getCurrentSession();

		Query<Users> theQuery = currentSession.createQuery("from Users where userName like :name", Users.class);

		theQuery.setParameter("name", "%" + searchText + "%");

		List<Users> Users = theQuery.getResultList();

		return Users;
	}
	//유저 카테고리가 1초과인것만 가져오고 이름순서대로 정렬 - 작가별 페이지
	@Override
	public List<Users> getArtists() {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "FROM Users WHERE userCategoryNo > 1 ORDER BY userRealName";

		Query<Users> theQuery = currentSession.createQuery(hql, Users.class);
		List<Users> artists = theQuery.getResultList();

		return artists;
	}
	//users테이블에서 username만 가져오기
	@Override
	public List<String> getUsersName() {

		Session currentSession = sessionFactory.getCurrentSession();
		Query<String> theQuery = currentSession.createQuery("Select userName from Users");

		List<String> Users = theQuery.getResultList();

		return Users;
	}
	
	// 권한 관리---------------------------------------------------------------------------------------------
	//권한 전부 불러오기
	@Override
	public List<Authorities> getAuthorities() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Authorities> theQuery = currentSession.createQuery("from Authorities", Authorities.class);
		List<Authorities> Authorities = theQuery.getResultList();

		return Authorities;
	}
	
	// 아트워크 관리-------------------------------------------------------------------------------------------
	//그림 정보 전부 불러오기
	@Override
	public List<Artwork> getArtworks() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Artwork> theQuery = currentSession.createQuery("from Artwork", Artwork.class);
		List<Artwork> artworks = theQuery.getResultList();

		return artworks;
	}
	//작가에 no를 통해서 정보 불러오기
	@Override
	public Artwork getArtwork(int theId) {
		Session currentSession = sessionFactory.getCurrentSession();

		return currentSession.get(Artwork.class, theId);
	}
	//그림 정보 업데이트 하기
	@Override
	public void updateArtwork(Artwork theArtwork) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(theArtwork);
	}
	//그림와 관련된 테이블 정보 삭제하기
	@Override
	public void deleteArtwork(int theArtworkId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery = currentSession
				.createQuery("Update Sales " + "Set salesArtworkNo = 1 " + "Where salesArtworkNo = :ArtworkNo");
		theQuery.setParameter("ArtworkNo", theArtworkId);
		theQuery.executeUpdate();

		theQuery = currentSession.createQuery("Delete from Wishlist " + "Where wishlistArtworkNo = :ArtworkNo");
		theQuery.setParameter("ArtworkNo", theArtworkId);
		theQuery.executeUpdate();

		theQuery = currentSession.createQuery("Delete from Cart " + "Where cartArtworkNo = :ArtworkNo");
		theQuery.setParameter("ArtworkNo", theArtworkId);
		theQuery.executeUpdate();

		Artwork Artwork = currentSession.get(Artwork.class, theArtworkId);
		currentSession.delete(Artwork);
	}
	//그림 이름으로 검색하기
	@Override
	public List<Artwork> searchArtworks(String searchText) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Artwork> theQuery = currentSession.createQuery("from Artwork where artworkName like :name",
				Artwork.class);

		theQuery.setParameter("name", "%" + searchText + "%");

		List<Artwork> Artworks = theQuery.getResultList();

		return Artworks;
	}
	//작가에 그림중 판매된 그림만 불러오기
	@Override
	public List<Artwork> getArtworksSold(int userNo, boolean artworkSold) {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "FROM Artwork WHERE artworkArtistNo =" + userNo + "and artworkSold =" + artworkSold;

		Query<Artwork> theQuery = currentSession.createQuery(hql, Artwork.class);
		List<Artwork> artworks = theQuery.getResultList();

		return artworks;
	}
	//작가에 그림중 판매 하고 있는 그림중에 그림레벨이 normal인것만 불러오기
	@Override
	public List<Artwork> getArtworksSaleNormal(int userNo, boolean artworkSold, String normal) {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "FROM Artwork WHERE artworkArtistNo =" + userNo + " and artworkSold =" + artworkSold
				+ " and artworkLevel = '" + normal + "'";

		Query<Artwork> theQuery = currentSession.createQuery(hql, Artwork.class);
		List<Artwork> artworks = theQuery.getResultList();

		return artworks;
	}
	//작가에 그림중 판매 하고 있는 그림중에 그림레벨이 vip인것만 불러오기
	@Override
	public List<Artwork> getArtworksSaleVip(int userNo, boolean artworkSold, String vip) {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "FROM Artwork WHERE artworkArtistNo =" + userNo + " and artworkSold =" + artworkSold
				+ " and artworkLevel like '%" + vip + "'";

		Query<Artwork> theQuery = currentSession.createQuery(hql, Artwork.class);
		List<Artwork> artworks = theQuery.getResultList();

		return artworks;
	}
	//판매중인 그림중에 그림레벨이 vip인것만 불러오기
	@Override
	public List<Artwork> getArtworksVip() {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "FROM Artwork WHERE artworkLevel = 'vip' and artworkSold = 0";

		Query<Artwork> theQuery = currentSession.createQuery(hql, Artwork.class);

		List<Artwork> artworks = theQuery.getResultList();
		return artworks;
	}
	//판매중인 그림중에 그림레벨이 vvip인것만 불러오기
	@Override
	public List<Artwork> getArtworksVvip() {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "FROM Artwork WHERE artworkLevel = 'vvip' and artworkSold = 0";
		Query<Artwork> theQuery = currentSession.createQuery(hql, Artwork.class);

		List<Artwork> artworks = theQuery.getResultList();
		return artworks;
	}
	//판매중인 그림들 불러오기
	@Override
	public List<Artwork> getArtworkSoldlist() {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "FROM Artwork WHERE artworkSold=0 and artworkLevel != 'none' and artworkLevel='normal'";
		Query<Artwork> theQuery = currentSession.createQuery(hql, Artwork.class);
		List<Artwork> artworks = theQuery.getResultList();

		return artworks;
	}
	//제일 최근에 업로드된 순서대로 그림 불러오기
	@Override
	public List<Artwork> getNewArtworks() {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "FROM Artwork ORDER BY artworkRegdate DESC";

		Query<Artwork> theQuery = currentSession.createQuery(hql, Artwork.class);
		theQuery.setMaxResults(3);
		List<Artwork> newArtworks = theQuery.getResultList();

		return newArtworks;
	}
	//그림을 구매하고 나서 그 그림과 관련된 테이블 항목 지우기
	@Override
	public void buyArtwork(Sales theSales, Users user) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(theSales);

		Query theQuery = currentSession
				.createQuery("Delete from Cart " + "Where cartArtworkNo = " + theSales.getSalesArtworkNo());
		theQuery.executeUpdate();

		theQuery = currentSession
				.createQuery("Delete from Wishlist " + "Where wishlistArtworkNo = " + theSales.getSalesArtworkNo());
		theQuery.executeUpdate();

		theQuery = currentSession.createQuery(
				"Update Artwork " + "Set artworkSold = 1 " + "Where artworkNo = " + theSales.getSalesArtworkNo());
		theQuery.executeUpdate();

		int price = user.getUserPurchaseAmount() + theSales.getSalesArtworkPrice();
		theQuery = currentSession.createQuery(
				"Update Users " + "Set userPurchaseAmount = " + price + " Where userNo = " + theSales.getSalesUserNo());
		theQuery.executeUpdate();

		theQuery = currentSession.createQuery("Select authority " + "From Authorities " + "Where userName = :username");
		theQuery.setParameter("username", user.getUserName());
		List<String> theauthorities = theQuery.getResultList();

		if (price >= 3000000)
			if (!(theauthorities.contains("ROLE_VIP"))) {
				Authorities theAuthority = new Authorities();
				theAuthority.setuserName(user.getUserName());
				theAuthority.setAuthority("ROLE_VIP");
				currentSession.save(theAuthority);
			}

		if (price >= 6000000)
			if (!(theauthorities.contains("ROLE_VVIP"))) {
				Authorities theAuthority = new Authorities();
				theAuthority.setuserName(user.getUserName());
				theAuthority.setAuthority("ROLE_VVIP");
				currentSession.save(theAuthority);
			}
	}
	//카테고리 ----------------------------------------------------------------------------------------------
	//카테고리별 페이지 - 카테고리 notfound제외 전체 불러오기
	@Override
	public List<Category> getCategory() {
		Session currentSession = sessionFactory.getCurrentSession();
		String hql = "FROM Category WHERE categoryNo > 1";
		Query<Category> theQuery = currentSession.createQuery(hql, Category.class);
		List<Category> categorys = theQuery.getResultList();

		return categorys;
	}

	// 판매 내역 관리------------------------------------------------------------------------------------------
	//판매 내역 전부 불러오기
	@Override
	public List<Sales> getSales() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Sales> theQuery = currentSession.createQuery("from Sales", Sales.class);
		List<Sales> Sales = theQuery.getResultList();

		return Sales;
	}
	//유저no랑 그림no를 통해서 판매 내역 검색하기
	@Override
	public List<Sales> searchSales(String searchText) {
		Session currentSession = sessionFactory.getCurrentSession();
		List<Users> theUsersQuery = searchUser(searchText);
		List<Artwork> theArtworkQuery = searchArtworks(searchText);

		StringBuilder SelectQuery = new StringBuilder("from Sales ");
		boolean i = false;
		for (Users UserQuery : theUsersQuery) {
			if (i == false) {
				SelectQuery.append("where salesUserNo = " + UserQuery.getUserNo());
				i = true;
			} else
				SelectQuery.append(" or salesUserNo = " + UserQuery.getUserNo());
		}

		for (Artwork ArtworkQuery : theArtworkQuery) {
			if (i == false) {
				SelectQuery.append("where salesArtworkNo = " + ArtworkQuery.getArtworkNo());
				i = true;
			} else
				SelectQuery.append(" or salesArtworkNo = " + ArtworkQuery.getArtworkNo());

		}

		Query<Sales> theQuery = currentSession.createQuery(String.valueOf(SelectQuery), Sales.class);
		List<Sales> Sales = theQuery.getResultList();

		return Sales;
	}

	// 장바구니 ---------------------------------------------------------------------------------------------
	// 장바구니 중복 방지
	@Override
	public List<Integer> myCart(int theId) {
		Session currentSession = sessionFactory.getCurrentSession();

		Query<Integer> SQL = currentSession.createQuery("Select cartArtworkNo from Cart Where cartUserNo = " + theId);

		List<Integer> myCart = SQL.getResultList();

		return myCart;
	}
	//장바구니 추가
	@Override
	public void addCart(Cart theCart) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(theCart);
	}
	//장바구니 리스트 출력
	@Override
	public List<Cart> Cartslists() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Cart> theQuery = currentSession.createQuery("from Cart", Cart.class);
		List<Cart> Carts = theQuery.getResultList();

		return Carts;
	}
	//장바구니 삭제
	@Override
	public void deleteCart(int CartId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Cart> theQuery = currentSession.createQuery("delete from Cart where cartNo = " + CartId);

		theQuery.executeUpdate();
	}
	// 장바구니 전체삭제
	@Override
	public void deleteAllcart(int userId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Cart> theQuery = currentSession.createQuery("delete from Cart where cartUserNo = " + userId);

		theQuery.executeUpdate();
	}

	//찜 목록 ----------------------------------------------------------------------------------------------
	//찜 목록 중복 방지
	@Override
	public List<Integer> myWish(int theId) {
		Session currentSession = sessionFactory.getCurrentSession();

		Query<Integer> SQL = currentSession
				.createQuery("Select wishlistArtworkNo from Wishlist Where wishlistUserNo = " + theId);

		List<Integer> myWish = SQL.getResultList();

		return myWish;
	}
	//찜 목록 추가
	@Override
	public void addWish(Wishlist wishlist) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(wishlist);
	}
	//찜 목록 리스트 출력
	@Override
	public List<Wishlist> wishlists() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Wishlist> theQuery = currentSession.createQuery("from Wishlist", Wishlist.class);
		List<Wishlist> wishes = theQuery.getResultList();

		return wishes;
	}
	//찜 목록 삭제
	@Override
	public void deleteWish(int wishId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Wishlist> theQuery = currentSession.createQuery("delete from Wishlist where wishlistNo = " + wishId);

		theQuery.executeUpdate();
	}
	// 찜 목록 전체삭제
	@Override
	public void deleteAllWish(int userId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Wishlist> theQuery = currentSession.createQuery("delete from Wishlist where wishlistUserNo = " + userId);

		theQuery.executeUpdate();
	}
	//찜 목록에 제일 많이 등록된 작가번호 순서 정렬해서 불러오기 - 베스트 위시
	public void close(Connection conn, Statement myst, ResultSet myrs) {
		try {
			if (myrs != null) {
				myrs.close();
			}
			if (myst != null) {
				myst.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<Wishlist> getBestWish() throws SQLException {
		List<Wishlist> Bestwish = new ArrayList<Wishlist>();

		Connection conn = null;
		Statement myst = null;
		ResultSet myrs = null;

		try {
			conn = dataSource.getConnection();
			String sql = "SELECT wishlist_artwork_no FROM wishlist GROUP BY wishlist_artwork_no order by count(*) DESC limit 3";
			myst = conn.createStatement();
			myrs = myst.executeQuery(sql);

			while (myrs.next()) {
				int wishlist_artwork_no = myrs.getInt("wishlist_artwork_no");

				Wishlist beshwish = new Wishlist(wishlist_artwork_no);
				Bestwish.add(beshwish);
			}

			return Bestwish;
		} finally {
			close(conn, myst, myrs);
		}
	}
}

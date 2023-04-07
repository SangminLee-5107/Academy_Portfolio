package com.code.springdemo.service;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.code.springdemo.dao.ArtshopDAO;
import com.code.springdemo.entity.Artwork;
import com.code.springdemo.entity.Authorities;
import com.code.springdemo.entity.Cart;
import com.code.springdemo.entity.Category;
import com.code.springdemo.entity.Sales;
import com.code.springdemo.entity.Users;
import com.code.springdemo.entity.Wishlist;

@Service
public class ArtshopServiceImpl implements ArtshopService {
	
	@Autowired
	private ArtshopDAO artshopDAO;

	//LoginController ================================================================
	//권한가져 오기
	@Override
	@Transactional
	public String getAuth(String username) {
		return artshopDAO.getAuth(username);
	}
	//회원 가입
	@Override
	@Transactional
	public void register(Users theuser, Authorities theauthorities) {
		artshopDAO.register(theuser, theauthorities);
	}
	//회원 탈퇴
	@Override
	@Transactional
	public void unregister(int theId) {
		artshopDAO.unregister(theId);
	}
	//시큐리티에서 가져온 아이디 정보
	@Override
	@Transactional
	public int getIntUsers(String userName) {
		return artshopDAO.getIntUsers(userName);
	}
	//회원으로 로그인해서 로그인한 userNo를 가져와서 입력한 정보로 업데이트 하기
	@Override
	@Transactional
	public void updateUsers(int theId, Users theusers) {
		artshopDAO.updateUsers(theId,theusers);
	}
	//아이디 중복확인
	@Override
	@Transactional
	public String duplicationConfirm(String loginId) {
		return artshopDAO.duplicationConfirm(loginId);
	}
	//DemoController =================================================================
	//유저 관리--------------------------------------------------------------------------
	//유저 정보 전부 불러오기
	@Override
	@Transactional
	public List<Users> getUsers() {
		 return artshopDAO.getUsers();
	}
	//유저no를 통해 선택한 유저 정보만 가져오기
	@Override
	@Transactional
	public Users getUsers(int theId) {
		return artshopDAO.getUsers(theId);
	}
	//유저정보를 가져와서 유저 아이디만 수정하기
	@Override
	@Transactional
	public void UpdateUser(Users theUser) {
		artshopDAO.UpdateUser(theUser);
	}
	//유저no를 가져와서 유저에 관한 모든 테이블 정보 삭제
	@Override
	@Transactional
	public void deleteUser(int theId) {
		artshopDAO.deleteUser(theId);
	}
	//유저 아이디를 통해 검색하기
	@Override
	@Transactional
	public List<Users> searchUser(String searchText) {
		return artshopDAO.searchUser(searchText);
	}
	//유저 카테고리가 1초과인것만 가져오고 이름순서대로 정렬 - 작가별 페이지
	@Override
	@Transactional
	public List<Users> getArtists() {
		return artshopDAO.getArtists();
	}
	//users테이블에서 username만 가져오기
	@Override
	@Transactional
	public List<String> getUsersName() {
		return artshopDAO.getUsersName();
	}
	
	//권한 관리------------------------------------------------------------------
	//권한 전부 불러오기
	@Override
	@Transactional
	public List<Authorities> getAuthorities() {
		return artshopDAO.getAuthorities();
	}
	
	//아트워크 관리---------------------------------------------------------
	//그림 정보 전부 불러오기
	@Override
	@Transactional
	public List<Artwork> getArtworks() {
		return artshopDAO.getArtworks();
	}
	//작가에 no를 통해서 정보 불러오기
	@Override
	@Transactional
	public Artwork getArtwork(int theId) {
		return artshopDAO.getArtwork(theId);
	}
	//그림 정보 업데이트 하기
	@Override
	@Transactional
	public void updateArtwork(Artwork theArtwork) {
		artshopDAO.updateArtwork(theArtwork);	
	}
	//그림와 관련된 테이블 정보 삭제하기
	@Override
	@Transactional
	public void deleteArtwork(int theId) {
		artshopDAO.deleteArtwork(theId);
	}
	//그림 이름으로 검색하기
	@Override
	@Transactional
	public List<Artwork> searchArtworks(String searchText) {
		return artshopDAO.searchArtworks(searchText);
	}
	//작가에 그림중 판매된 그림만 불러오기
	@Override
	@Transactional
	public List<Artwork> getArtworksSold(int userNo, boolean artworkSold) {
		return artshopDAO.getArtworksSold(userNo, artworkSold);
	}
	//작가에 그림중 판매 하고 있는 그림중에 그림레벨이 normal인것만 불러오기
	@Override
	@Transactional
	public List<Artwork> getArtworksSaleNormal(int userNo, boolean artworkSold, String normal) {
		return artshopDAO.getArtworksSaleNormal(userNo, artworkSold, normal);
	}
	//작가에 그림중 판매 하고 있는 그림중에 그림레벨이 vip인것만 불러오기
	@Override
	@Transactional
	public List<Artwork> getArtworksSaleVip(int userNo, boolean artworkSold, String vip) {
		return artshopDAO.getArtworksSaleVip(userNo, artworkSold, vip);
	}
	//판매중인 그림중에 그림레벨이 vip인것만 불러오기
	@Override
	@Transactional
	public List<Artwork> getArtworksVip() {
		return artshopDAO.getArtworksVip();
	}
	//판매중인 그림중에 그림레벨이 vvip인것만 불러오기
	@Override
	@Transactional
	public List<Artwork> getArtworksVvip() {
		return artshopDAO.getArtworksVvip();
	}
	//판매중인 그림들 불러오기
	@Override
	@Transactional
	public List<Artwork> getArtworkSoldlist() {
		return artshopDAO.getArtworkSoldlist();
	}
	//제일 최근에 업로드된 순서대로 그림 불러오기
	@Override
	@Transactional
	public List<Artwork> getNewArtworks() {
		return artshopDAO.getNewArtworks();
	}
	//그림을 구매하고 나서 그 그림과 관련된 테이블 항목 지우기
	@Override
	@Transactional
	public void buyArtwork(Sales theSales, Users users) {
		artshopDAO.buyArtwork(theSales, users);
	}
	
	//카테고리 ----------------------------------------------------------------------
	//카테고리별 페이지 - 카테고리 notfound제외 전체 불러오기
	@Override
	@Transactional
	public List<Category> getCategory() {
		return artshopDAO.getCategory();
	}
	
	//판매 내역 관리------------------------------------------------------------------
	//판매 내역 전부 불러오기
	@Override
	@Transactional
	public List<Sales> getSales() {
		return artshopDAO.getSales();
	}
	//유저no랑 그림no를 통해서 판매 내역 검색하기
	@Override
	@Transactional
	public List<Sales> searchSales(String searchText) {
		return artshopDAO.searchSales(searchText);
	}

	//장바구니 ---------------------------------------------------------------------
	//장바구니 중복 방지
	@Override
	@Transactional
	public List<Integer> myCart(int theId) {
		return artshopDAO.myCart(theId);
	}
	//장바구니 추가
	@Override
	@Transactional
	public void addCart(Cart theCart) {
		artshopDAO.addCart(theCart);
	}
	//장바구니 리스트 출력
	@Override
	@Transactional
	public List<Cart> Cartslists() {
	  	return artshopDAO.Cartslists();
	}
	//장바구니 삭제	
	@Override
	@Transactional
	public void deleteCart(int CartId) {
		artshopDAO.deleteCart(CartId);
	}
	//장바구니 전체
	@Override
	@Transactional
	public void deleteAllcart(int userId) {
		artshopDAO.deleteAllcart(userId);
	}
	
	//찜 목록 ------------------------------------------------------------
	//찜 중복 방지
  	@Override
  	@Transactional
  	public List<Integer> myWish(int theId) {
  		return artshopDAO.myWish(theId);
  	}
  	//찜 목록 리스트 출력
  	@Override
  	@Transactional
  	public List<Wishlist> wishlists() {
  		return artshopDAO.wishlists();
  	}
  	//찜 목록 추가
  	@Override
  	@Transactional
  	public void addWish(Wishlist theWish) {
  	    artshopDAO.addWish(theWish);
  	}
  	//찜 목록 삭제
  	@Override
  	@Transactional
	public void deleteWish(int wishId) {
  		artshopDAO.deleteWish(wishId);
	}
  	// 찜 목록 전체삭제
  	@Override
  	@Transactional
  	public void deleteAllWish(int userId) {
  		artshopDAO.deleteAllWish(userId);
  	}
  	//찜 목록에 제일 많이 등록된 작가번호 순서 정렬해서 불러오기 - 베스트 위시
	@Override
	@Transactional
	public List<Wishlist> getBestWish() throws SQLException {
		return artshopDAO.getBestWish();
	}	
}

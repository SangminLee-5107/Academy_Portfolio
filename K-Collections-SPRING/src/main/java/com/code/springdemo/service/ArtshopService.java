package com.code.springdemo.service;

import java.sql.SQLException;
import java.util.List;

import com.code.springdemo.entity.Artwork;
import com.code.springdemo.entity.Authorities;
import com.code.springdemo.entity.Cart;
import com.code.springdemo.entity.Category;
import com.code.springdemo.entity.Sales;
import com.code.springdemo.entity.Users;
import com.code.springdemo.entity.Wishlist;

public interface ArtshopService {
	//LoginController ================================================================
	//권한가져 오기
	public String getAuth(String username);
	//회원 가입
	public void register(Users theuser, Authorities theauthorities);
	//회원 탈퇴
	public void unregister(int theId);
	//시큐리티에서 가져온 아이디 정보
	public int getIntUsers(String userName);
	//회원으로 로그인해서 로그인한 userNo를 가져와서 입력한 정보로 업데이트 하기
	public void updateUsers(int theId, Users theusers);
	//아이디 중복확인
	public String duplicationConfirm(String loginId);

	//DemoController =================================================================
	//유저 관리-----------------------------------------------------------
	//유저 정보 전부 불러오기
	public List<Users> getUsers();
	//유저no를 통해 선택한 유저 정보만 가져오기
	public Users getUsers(int theId);
	//유저정보를 가져와서 유저 아이디만 수정하기
	public void UpdateUser(Users theUser);
	//유저no를 가져와서 유저에 관한 모든 테이블 정보 삭제
	public void deleteUser(int theId);
	//유저 아이디를 통해 검색하기
	public List<Users> searchUser(String searchText);
	//유저 카테고리가 1초과인것만 가져오고 이름순서대로 정렬 - 작가별 페이지
	public List<Users> getArtists();
	//users테이블에서 username만 가져오기
	public List<String> getUsersName();
	
	//권한 관리------------------------------------------------------------
	//권한 전부 불러오기
	public List<Authorities> getAuthorities();
	
	//아트워크 관리---------------------------------------------------------
	//그림 정보 전부 불러오기
	public List<Artwork> getArtworks();
	//작가에 no를 통해서 정보 불러오기
	public Artwork getArtwork(int theId);
	//그림 정보 업데이트 하기
	public void updateArtwork(Artwork theArtwork);
	//그림와 관련된 테이블 정보 삭제하기
	public void deleteArtwork(int theId);
	//그림 이름으로 검색하기
	public List<Artwork> searchArtworks(String searchText);
	//작가에 그림중 판매된 그림만 불러오기
	public List<Artwork> getArtworksSold(int userNo, boolean artworkSold);
	//작가에 그림중 판매 하고 있는 그림중에 그림레벨이 normal인것만 불러오기
	public List<Artwork> getArtworksSaleNormal(int userNo, boolean artworkSold, String normal);
	//작가에 그림중 판매 하고 있는 그림중에 그림레벨이 vip인것만 불러오기
	public List<Artwork> getArtworksSaleVip(int userNo, boolean artworkSold, String vip);
	//판매중인 그림중에 그림레벨이 vip인것만 불러오기
	public List<Artwork> getArtworksVip();
	//판매중인 그림중에 그림레벨이 vvip인것만 불러오기
	public List<Artwork> getArtworksVvip();
	//판매중인 그림들 불러오기
	public List<Artwork> getArtworkSoldlist();
	//제일 최근에 업로드된 순서대로 그림 불러오기
	public List<Artwork> getNewArtworks();
	//그림을 구매하고 나서 그 그림과 관련된 테이블 항목 지우기
	public void buyArtwork(Sales theSales, Users users);
	
	//카테고리 ----------------------------------------------------------------------
	//카테고리별 페이지 - 카테고리 notfound제외 전체 불러오기
	public List<Category> getCategory();
	
	//판매 내역 관리--------------------------------------------------------
	//판매 내역 전부 불러오기
	public List<Sales> getSales();
	//유저no랑 그림no를 통해서 판매 내역 검색하기
	public List<Sales> searchSales(String searchText);

	//장바구니 ------------------------------------------------------
    //장바구니 중복 방지
    public List<Integer> myCart(int theId);
    //장바구니 추가
	public void addCart(Cart theCart);
	//장바구니 리스트 출력
	public List<Cart> Cartslists();
	//장바구니 삭제
	public void deleteCart(int CartId);
	// 장바구니 목록 전체삭제
	public void deleteAllcart(int userId);
	
	//찜 목록 -------------------------------------------------------
	//찜 목록 중복 방지
    public List<Integer> myWish(int theId);
    //찜 목록 리스트 출력
	public List<Wishlist> wishlists();
	//찜 목록 추가
	public void addWish(Wishlist theWish);
	//찜 목록 삭제
	public void deleteWish(int wishId);
	// 찜 목록 전체삭제
	public void deleteAllWish(int userId);
	//찜 목록에 제일 많이 등록된 작가번호 순서 정렬해서 불러오기 - 베스트 위시
	public List<Wishlist> getBestWish() throws SQLException;
}

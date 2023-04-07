package com.code.springdemo.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.code.springdemo.entity.Artwork;
import com.code.springdemo.entity.Authorities;
import com.code.springdemo.entity.Cart;
import com.code.springdemo.entity.Category;
import com.code.springdemo.entity.Sales;
import com.code.springdemo.entity.Users;
import com.code.springdemo.entity.Wishlist;
import com.code.springdemo.service.ArtshopService;

@Controller
public class DemoController {
	
	@Autowired
	private ArtshopService artshopservice;
	
	//홈 =========================================================================================================
	@GetMapping("/")
	public String showHome(Model theModel) throws SQLException  {
		//새로 들어온 작품(입고일자 기반) - 캐로셀 슬라이드
        List<Artwork> newArtworks = artshopservice.getNewArtworks();
        theModel.addAttribute("newArtworks", newArtworks);
        
        //아티스트 정보
        List<Users> Users = artshopservice.getUsers();
        theModel.addAttribute("Users", Users);

        //작품 정보
        List<Artwork> theArtworks = artshopservice.getArtworkSoldlist();
        theModel.addAttribute("artworks", theArtworks);
        
        //베스트 위시 작품(찜 개수 기반)
        List<Wishlist> bestWish = artshopservice.getBestWish();
        theModel.addAttribute("bestWish", bestWish);
        
		return "home";
	}
	
	//홈 - 카테고리별 페이지
	@GetMapping("/byCategory")
	public String showByCategory(Model theModel) {
		List<Category> category = artshopservice.getCategory();
	    theModel.addAttribute("category", category);
	      
	    List<Artwork> theArtworks = artshopservice.getArtworkSoldlist();
	    theModel.addAttribute("artworks", theArtworks);
	    
	    List<Users> users = artshopservice.getUsers();
		theModel.addAttribute("users", users);
	    
	            
		return "by-category";
	}
	
	//홈 - 작가별 소개 페이지
	@GetMapping("/byArtist")
	public String showByArtist(Model theModel) {
		List<Users> artists = artshopservice.getArtists();
		theModel.addAttribute("artists", artists);
		      
		List<Artwork> theArtworks = artshopservice.getArtworkSoldlist();
		theModel.addAttribute("artworks", theArtworks);
		            
		return "by-artist";
	}
	
	//홈 - Vip 페이지
	@GetMapping("/vipPage")
	public String showVip(Authentication auth, Model theModel) {
		String username = auth.getName();
		int theId = artshopservice.getIntUsers(username);
		Users user = artshopservice.getUsers(theId);
		theModel.addAttribute("User", user);
					
		String authority = artshopservice.getAuth(username);
		theModel.addAttribute("Role", authority);
		theModel.addAttribute("Users", artshopservice.getUsers());
					
		List<Artwork> artworksVip = artshopservice.getArtworksVip();
		theModel.addAttribute("artworksVip", artworksVip);
					
		List<Artwork> artworksVvip = artshopservice.getArtworksVvip();
		theModel.addAttribute("artworksVvip", artworksVvip);

		return "list-vip";
	}
	
	//홈 - 찜목록 페이지
	@GetMapping("/showWish")
	public String showwish(Authentication auth, Model theModel) {
		List<Wishlist> wishes = artshopservice.wishlists();
		List<Artwork> theArtworks = artshopservice.getArtworkSoldlist();
		String username = auth.getName();
		int theId = artshopservice.getIntUsers(username);
		Users users = artshopservice.getUsers(theId);
			
		theModel.addAttribute("Users", artshopservice.getUsers());
		theModel.addAttribute("Artworks", theArtworks);
		theModel.addAttribute("Wishes", wishes);
		theModel.addAttribute("User", users);
		
		return "wish-list";
	}
	
	//홈 - 장바구니 페이지
	@GetMapping("/showcart")
	public String showCart(Authentication auth, Model theModel) {
		List<Cart> carts = artshopservice.Cartslists();
		List<Artwork> theArtworks = artshopservice.getArtworkSoldlist();
		String username = auth.getName();
		int theId = artshopservice.getIntUsers(username);
		Users users = artshopservice.getUsers(theId);
		
		theModel.addAttribute("Carts", carts);
		theModel.addAttribute("User", users);
		theModel.addAttribute("Users", artshopservice.getUsers());
		theModel.addAttribute("Artworks", theArtworks);
			
		return "cart-list";
	}
	
	//유저 마이페이지 =========================================================================================================
	@GetMapping("/userPage")
	public String showUser(Authentication auth, Model theModel) {
		String username = auth.getName();
		int theId = artshopservice.getIntUsers(username);
		Users users = artshopservice.getUsers(theId);
		theModel.addAttribute("Users", users);
			
		String authority = artshopservice.getAuth(username);
		theModel.addAttribute("Role", authority);
			
		return "user-mypage";
	}
	
	//유저 마이페이지 - 주문내역
	@GetMapping("/order-list")
	public String orderList(Authentication auth, Model theModel) {

        String username = auth.getName();
        int theId = artshopservice.getIntUsers(username);
        Users user = artshopservice.getUsers(theId);
        theModel.addAttribute("user", user);
        theModel.addAttribute("Users", artshopservice.getUsers());
        theModel.addAttribute("Artworks", artshopservice.getArtworks());
        theModel.addAttribute("Sales", artshopservice.getSales());
        
		return "user-order-list";
	}
	
	//작가 마이페이지 ====================================================================================================
	@GetMapping("/artistPage/artist")
	public String showArtist(Authentication auth, Model theModel) {
		
		theModel.addAttribute("thisPage", "ArtworkShow");
		
		 String username = auth.getName();
		 int theId = artshopservice.getIntUsers(username);
		 Users users = artshopservice.getUsers(theId);
		 theModel.addAttribute("Users", users);
		 
		 int userNo = users.getUserNo();	
		 
		 List<Artwork> artworksSold = artshopservice.getArtworksSold(userNo, true);
		 theModel.addAttribute("artworksSold", artworksSold); //true =1 판매완료
		 
		 List<Artwork> artworksSaleNormal = artshopservice.getArtworksSaleNormal(userNo, false, "normal");
		 theModel.addAttribute("artworksSaleNormal", artworksSaleNormal);//0 일반 작품 판매중
		 
		 List<Artwork> artworksSaleVip = artshopservice.getArtworksSaleVip(userNo, false, "vip");
		 theModel.addAttribute("artworksSaleVip", artworksSaleVip);//0 vip 혹은 vvip 작품 판매중
		
		return "artist-mypage";
	}
	
	//작가 마이페이지 - update & delete
	@GetMapping("/artistPage/showForUpdate")
	public String showUpdateArtworkList(@RequestParam("artworkNo") int theId, Authentication auth, Model theModel) {
		theModel.addAttribute("saveOrUpdate", "ArtworkUpdate");
		theModel.addAttribute("thisPage","ArtworkUpdate");
		theModel.addAttribute("Artwork", artshopservice.getArtwork(theId));
			 
		String username = auth.getName();
		int theId2 = artshopservice.getIntUsers(username);
		Users users = artshopservice.getUsers(theId2);
		theModel.addAttribute("Users", users);
			
		return "artist-mypage";
	}
	
	//작가 마이페이지 - 작품 업로드
	@GetMapping("/artistPage/showForUpload")
	public String showUploadArtworkList(Authentication auth, Model theModel) {
			
		String username = auth.getName();
		int theId = artshopservice.getIntUsers(username);
		Users users = artshopservice.getUsers(theId);
		theModel.addAttribute("Users", users);
			
		theModel.addAttribute("saveOrUpdate", "ArtworkUpload");
		theModel.addAttribute("thisPage","ArtworkUpdate");
			
		Artwork artwork = new Artwork();
		artwork.setArtworkLevel("none");
		artwork.setArtworkArtistNo(users.getUserNo());
		artwork.setArtworkSold(false);
		artwork.setArtworkRegdate(LocalDateTime.now());
		theModel.addAttribute("Artwork", artwork);
		
		return "artist-mypage";
	}
	
	//작가 마이페이지 - 작품정보 수정
	@GetMapping("/artistPage/UpdateArtwork")
	public String saveOrUpdateArtwork(@ModelAttribute("Artwork") Artwork theArtwork, Model theModel) {
		artshopservice.updateArtwork(theArtwork);
		
		return "redirect:/artistPage/artist";
	}
	
	//작가 마이페이지 - 작품 삭제
	@GetMapping("/artistPage/Delete")
	public String deleteArtistArtwork(@RequestParam("artworkNo") int theId) {
		artshopservice.deleteArtwork(theId);
			
		return "redirect:/artistPage/artist";
	}
	
	//어드민 마이페이지 ===============================================================================================
	@GetMapping("/adminPage/admin")
	public String showAdmin() {
		return "admin-mypage";
	}
	
	//어드민 마이페이지 - 유저 관리 =================================================================================================
	//어드민 마이페이지 - 유저 관리 - 유저 리스트 출력
	@GetMapping("/adminPage/UserList")
	public String showsystemsUserList(Model theModel) {
		
		theModel.addAttribute("thisPage", "UserList");
		theModel.addAttribute("Users", artshopservice.getUsers());
		theModel.addAttribute("Authorities", artshopservice.getAuthorities());
		
		return "admin-mypage";
	}
	
	//어드민 마이페이지 - 유저 관리 - 유저 정보를 수정하기위한 페이지 출력
	@GetMapping("/adminPage/UpdateUser")
	public String UserUpdatePage(@RequestParam("userNo") int theId, Model theModel) {
			
		theModel.addAttribute("thisPage","UserUpdate");
		theModel.addAttribute("User",artshopservice.getUsers(theId));
			
		return "admin-mypage";
	}
	
	//어드민 마이페이지 - 유저 관리 - 유저정보 수정하는 메서드
	@GetMapping("/adminPage/UpdateUser/Update")
	public String saveUser(@ModelAttribute("User") Users theUser,@RequestParam("beforeName") String beforeName, Model theModel) {
		List<String> theUsers = artshopservice.getUsersName();
		if(!(theUsers.contains(theUser.getUserName())) || beforeName.equals(theUser.getUserName())) {
			theUser.setPassword(theUser.getPassword().replace("{noop}", ""));
			artshopservice.UpdateUser(theUser);
					
			return "redirect:/adminPage/UserList";
		}
		else 
			return UserUpdatePage(theUser.getUserNo(),theModel);
	}
	
	//어드민 마이페이지 - 유저 관리 - 유저를 삭제하는 메서드
	@GetMapping("/adminPage/DeleteUser")
	public String deleteUser(@RequestParam("userNo") int theId) {

		artshopservice.deleteUser(theId);
			
		return "redirect:/adminPage/UserList";
	}
	
	//어드민 마이페이지 - 유저 관리 - 유저 검색 메서드
	@GetMapping("/adminPage/SearchUser")
	public String searchUser(@RequestParam("searchText") String searchText, Model theModel) {

		String trimname = searchText.replace(" ","");
		List<Users> Users = null;
		System.out.println(trimname);
		if(trimname != null) 
			Users = artshopservice.searchUser(trimname);
		else 
			Users = artshopservice.getUsers();
		
	    theModel.addAttribute("Users", Users);
		theModel.addAttribute("thisPage","UserList");
		theModel.addAttribute("Authorities",artshopservice.getAuthorities());
		
	    return "admin-mypage";
	}
	
	//어드민 마이페이지 - 아트워크 관리 ========================================================================================
	//어드민 마이페이지 - 아트워크 관리 - 아트워크 리스트 출력
	@GetMapping("/adminPage/ArtworkList")
	public String ArtworkListUpdatePage(Model theModel) {
		
		theModel.addAttribute("thisPage","ArtworkList");
		theModel.addAttribute("Artworks",artshopservice.getArtworks());
		theModel.addAttribute("Users",artshopservice.getUsers());
		
		return "admin-mypage";
	}
	
	//어드민 마이페이지 - 아트워크 관리 - 아트워크 업데이트 페이지 출력
	@GetMapping("/adminPage/ArtworkUpdate")
	public String showsystemsArtworkList(@RequestParam("ArtworkNo") int theId,Model theModel) {
		
		theModel.addAttribute("thisPage","ArtworkUpdate");
		theModel.addAttribute("Artwork",artshopservice.getArtwork(theId));
		theModel.addAttribute("Users",artshopservice.getUsers());
		
		return "admin-mypage";
	}
	
	//어드민 마이페이지 - 아트워크 관리 - 아트워크 업데이트
	@GetMapping("/adminPage/ArtworkUpdate/UpdateArtwork")
	public String UpdateArtworkList(@ModelAttribute("Artwork") Artwork theArtwork,Model theModel) {
	
		artshopservice.updateArtwork(theArtwork);

	    return "redirect:/adminPage/ArtworkList";
	}
	//어드민 마이페이지 - 아트워크 관리 - 아트워크 삭제
	@GetMapping("/adminPage/ArtworkDelete")
	public String deleteArtwork(@RequestParam("ArtworkNo") int theId) {

		artshopservice.deleteArtwork(theId);
		
	    return "redirect:/adminPage/ArtworkList";
	}
	
	//어드민 마이페이지 - 아트워크 관리 - 아트워크 검색
	@GetMapping("/adminPage/SearchArtwork")
	public String searchArtwork(@RequestParam("searchText") String searchText, Model theModel) {

		String trimname = searchText.replace(" ","");
		List<Artwork> Artworks = null;
		System.out.println(trimname);
		if(trimname != null) 
			Artworks = artshopservice.searchArtworks(trimname);
		else 
			Artworks = artshopservice.getArtworks();
		
	    theModel.addAttribute("Artworks", Artworks);
		theModel.addAttribute("Users",artshopservice.getUsers());
		theModel.addAttribute("thisPage","ArtworkList");
		
	    return "admin-mypage";
	}
	
	//어드민 마이페이지 - 판매 내역 관리 ===========================================================================================
	//어드민 마이페이지 - 판매 내역 관리 - 판매 내역 리스트 출력
	@GetMapping("/adminPage/SalesList")
	public String showsystemsSalesList(Model theModel) {
		
		theModel.addAttribute("Sales",artshopservice.getSales());
		theModel.addAttribute("Artworks",artshopservice.getArtworks());
		theModel.addAttribute("Users",artshopservice.getUsers());
		theModel.addAttribute("thisPage","SalesList");

		return "admin-mypage";
	}
	
	//어드민 마이페이지 - 판매 내역 관리 - 판매 내역 검색
	@GetMapping("/adminPage/SearchSales")
	public String searchSales(@RequestParam("searchText") String searchText, Model theModel) {
		
		String trimname = searchText.replace(" ","");
		List<Sales> Sales = null;
		System.out.println(trimname);
		
		if(trimname != null) 
			Sales = artshopservice.searchSales(trimname);
		else 
			Sales = artshopservice.getSales();
		
	    theModel.addAttribute("Sales", Sales);
		theModel.addAttribute("Artworks",artshopservice.getArtworks());
		theModel.addAttribute("Users",artshopservice.getUsers());
		theModel.addAttribute("thisPage","SalesList");
		
	    return "admin-mypage";
	}
	
	//찜 목록 ==============================================================================================================
	//찜 목록 - 찜 목록 추가
	@GetMapping("/savewish")
	public String saveWish(@ModelAttribute("artworkNum") int ArtworkNum, Authentication auth, Model theModel, HttpServletRequest request) {
		if (auth==null) 
			return  "redirect:/showMyLoginPage";
		
		String username = auth.getName();
		int userNo = artshopservice.getIntUsers(username);
		
		List<Integer> myWish = artshopservice.myWish(userNo);
		
		if(myWish.contains(ArtworkNum)) {
			return "redirect:" + request.getHeader("referer");
		}else {	
			Wishlist theWish = new Wishlist();
			theWish.setWishlistUserNo(userNo);
			theWish.setWishlistArtworkNo(ArtworkNum);
			
			artshopservice.addWish(theWish);
			
			return "redirect:" + request.getHeader("referer");
		}
	}
	
	//찜 목록 - 찜 목록 삭제
	@GetMapping("/deleteWish")
	public String deleteWish(@RequestParam("WishNo") int WishId, Model theModel) {
		artshopservice.deleteWish(WishId);
		
		return "redirect:/showWish";
	}
	
	//찜 목록 전체삭제
	@GetMapping("/deleteAllWish")
	public String deleteallwish(Authentication auth, Model theModel) {
		String userName = auth.getName();
		int userId = artshopservice.getIntUsers(userName);
			
		artshopservice.deleteAllWish(userId);
			
		return "redirect:/showWish";
	}
	
	//장바구니 ==============================================================================================================
	//장바구니 - 장바구니 추가하기
	@GetMapping("/savecart")
	public String saveCart(@ModelAttribute("artworkNO") int ArtworkNo, Authentication auth, Model theModel, HttpServletRequest request) {
		
		if (auth==null) 
			return  "redirect:/showMyLoginPage";
		
		String userName = auth.getName();
		
		int theId = artshopservice.getIntUsers(userName);
		
		List<Integer> myCart = artshopservice.myCart(theId);
		
		if(myCart.contains(ArtworkNo)) {
			return "redirect:"+request.getHeader("Referer");
		}else {
			Cart theCart = new Cart();
			theCart.setCartUserNo(theId);
			theCart.setCartArtworkNo(ArtworkNo);
			
			artshopservice.addCart(theCart);
			
			return "redirect:"+request.getHeader("Referer");
		}
	}
	
	//장바구니 - 장바구니 삭제
	@GetMapping("/deleteCart")
	public String deleteCart(@RequestParam("CartNO") int CartId, Model theModel) {
		artshopservice.deleteCart(CartId);
				
		return "redirect:/showcart";
	}
	
	//장바구니 전체삭제
	@GetMapping("/deleteAllCart")
	public String deleteAllcart(Authentication auth, Model theModel) {
		String userName = auth.getName();
		int userId = artshopservice.getIntUsers(userName);
			
		artshopservice.deleteAllcart(userId);
			
		return "redirect:/showcart";
	}
	
	//작품 상세 페이지
	@GetMapping("/detail")
	public String detailView(@ModelAttribute("ArtworkNumber") int theArtworks, Model theModel) {
		Artwork artworks = artshopservice.getArtwork(theArtworks);
		
		theModel.addAttribute("Users", artshopservice.getUsers());
		theModel.addAttribute("Cargorys", artshopservice.getCategory());
		theModel.addAttribute("Artwork", artworks);
						 
		return "detail-artwork";
	}
	
	//구매하기 ==============================================================================================================
	//구매하기 - 장바구니에서 결제창으로 정보 넘기기
	@GetMapping("/BuyArtwork")
	public String getBuyArtworkPage(@RequestParam("artworkNo") int artworkNo, Model theModel) {
		theModel.addAttribute("Artwork",artshopservice.getArtwork(artworkNo));
		theModel.addAttribute("Users",artshopservice.getUsers());
			
		return "buypage";
	}
	
	//구매하기 - 최종결재내역
	@GetMapping("/BuyArtwork/Buy")
	public String buyArtwork(@RequestParam("artworkNo") int theArtworkNo, @RequestParam("artworkPrice") int theArtworkPrice,Authentication auth, Model theModel) {
		String username = auth.getName();
		int theId = artshopservice.getIntUsers(username);
	    Users users = artshopservice.getUsers(theId);
	    
	    Sales theSales = new Sales();
	    theSales.setSalesUserNo(users.getUserNo());
	    theSales.setSalesArtworkNo(theArtworkNo);
	    theSales.setSalesArtworkPrice(theArtworkPrice);
	    theSales.setSalesPurchaseTime(LocalDateTime.now()); 
	        
	    artshopservice.buyArtwork(theSales, users);
	        
	    return "redirect:/order-list";
	}
}

package com.kh.demo.web;

import com.kh.demo.domain.entity.Product;
import com.kh.demo.domain.product.svc.ProductSVC;
import com.kh.demo.web.form.product.AddForm;
import com.kh.demo.web.form.product.UpdateForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Controller // Controller 역할을 하는 클래스
@RequestMapping("/products")    // http://localhost:9080/products
public class ProductControllerV2 {

  private ProductSVC productSVC;
  ProductControllerV2(ProductSVC productSVC){
    this.productSVC = productSVC;
  }

  //상품등록양식
  @GetMapping("/add")         // Get, http://localhost:9080/products/add
  public String addForm(Model model) {
    model.addAttribute("addForm", new AddForm());
    return "productv2/add";     // view이름  상품등록화면
  }

  //상품등록처리
  @PostMapping("/add")        // Post, http://localhost:9080/products/add
  public String add(
          AddForm addForm,    //form객체 : 양식과 매핑되는 객체
          Model model,
          RedirectAttributes redirectAttributes
          ){

    log.info("addForm={}", addForm);
    //유효성체크
    //필드 레벨
    //1-1)상품명
    String pattern = "^[a-zA-Z0-9가-힣_-]{3,1000}$";
    if (!Pattern.matches(pattern, addForm.getTitle())) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_title","영문/숫자/한글/_-가능, 3~10자리");
      return "productv2/add";
    }
    pattern = "^[a-zA-Z0-9가-힣_-]{3,1000}$";
    if (!Pattern.matches(pattern, addForm.getUserName())) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_userName","영문/숫자/한글/_-가능, 3~10자리");
      return "productv2/add";
    }


    //상품등록
    Product product = new Product();
    product.setTitle(addForm.getTitle());
    product.setTextLog(addForm.getTextLog());
    product.setUserName(addForm.getUserName());

    Long productId = productSVC.save(product);
    log.info("게시판넘버={}", productId);

    redirectAttributes.addAttribute("pid",productId);
    return "redirect:/products/{pid}/detail"; // 상품조회화면  302 GET http://서버:9080/products/상품번호/detail
  }

  //조회
  @GetMapping("/{pid}/detail")       //GET http://localhost:9080/products/상품번호/detail
  public String findById(@PathVariable("pid") Long productId, Model model){

    Optional<Product> findedProduct = productSVC.findById(productId);
    Product product = findedProduct.orElseThrow();
    model.addAttribute("product", product);

    return "productv2/detailForm";
  }

  //단건삭제
  @GetMapping("/{pid}/del")
  public String deleteById(@PathVariable("pid") Long productId){
    log.info("deleteById={}",productId);

    //1)상품 삭제 -> 상품테이블에서 삭제
    int deletedRowCnt = productSVC.deleteById(productId);
    
    return "redirect:/products";     // GET http://localhost:9080/products/
  }

  //여러건삭제
  @PostMapping("/del")          // POST http://localhost:9080/products/del
  public String deleteByIds(@RequestParam("pids") List<Long> pids) {

    log.info("deleteByIds={}",pids);
    int deletedRowCnt = productSVC.deleteByIds(pids);

    return "redirect:/products";    // GET http://localhost:9080/products/
  }

  //수정양식
  @GetMapping("/{pid}/edit")      // GET http://locahost:9080/products/상품번호/edit
  public String updateForm(
          @PathVariable("pid") Long productId,
          Model model){

    Optional<Product> optionalProduct = productSVC.findById(productId);
    Product findedProduct = optionalProduct.orElseThrow();

    model.addAttribute("product",findedProduct);
    return "productv2/updateForm";
  }
  //수정 처리
  @PostMapping("/{pid}/edit")
  public String update(
          //경로변수 pid로부터 상품번호을 읽어온다
          @PathVariable("pid") Long productId,
          //요청메세지 바디로부터 대응되는 상품정보를 읽어온다.
          UpdateForm updateForm,
          //리다이렉트시 경로변수에 값을 설정하기위해 사용
          RedirectAttributes redirectAttributes,
          Model model){

    //유효성체크
    //필드 레벨
    //1-1)상품명
    String pattern = "^[a-zA-Z0-9가-힣_-]{3,1000}$";
    if (!Pattern.matches(pattern, updateForm.getTitle())) {
      model.addAttribute("product", updateForm);
      model.addAttribute("s_err_title","영문/숫자/한글/_-가능, 3~10자리");
      return "productv2/updateForm";
    }
    pattern = "^[a-zA-Z0-9가-힣_-]{3,10000}$";
    if (!Pattern.matches(pattern, updateForm.getUserName())) {
      model.addAttribute("product", updateForm);
      model.addAttribute("s_err_userName","영문/숫자/한글/_-가능, 3~10자리");
      return "productv2/updateForm";
    }

    //정상처리
    Product product = new Product();
    product.setTitle(updateForm.getTitle());
    product.setTextLog(updateForm.getTextLog());
    product.setUserName(updateForm.getUserName());
    int updateRowCnt = productSVC.updateById(productId, product);

    redirectAttributes.addAttribute("pid",productId);
    return "redirect:/products/{pid}/detail";
  }
  
  //목록
  @GetMapping   // GET http://localhost:9080/products
  public String findAll(Model model){

    List<Product> list = productSVC.findAll();
    model.addAttribute("list", list);

    return "productv2/all";
  }
}

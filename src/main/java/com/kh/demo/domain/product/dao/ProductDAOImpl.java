package com.kh.demo.domain.product.dao;

import com.kh.demo.domain.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository //dao역할을 하는 클래스
public class ProductDAOImpl implements ProductDAO{

  private final NamedParameterJdbcTemplate template;

  ProductDAOImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  @Override
  public Long save(Product product) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into product(product_id,title,text_log,user_name) ");
    sql.append("values(product_product_id_seq.nextval, :title, :textLog, :userName) ");

    // SQL파라미터 자동매핑
    SqlParameterSource param = new BeanPropertySqlParameterSource(product);
    KeyHolder keyHolder = new GeneratedKeyHolder();
//    template.update(sql.toString(),param,keyHolder,new String[]{"product_id"});
    template.update(sql.toString(),param,keyHolder,new String[]{"product_id","title","text_log"});
//    long productId = keyHolder.getKey().longValue(); //상품아이디
//    log.info("keyHolder={}", keyHolder.getKeys());
    Long product_id = ((BigDecimal)keyHolder.getKeys().get("product_id")).longValue(); //상품아이디
    return product_id;
  }

  //조회
  @Override
  public Optional<Product> findById(Long productId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select product_id,title,text_log,user_name,cdate,udate ");
    sql.append("  from product ");
    sql.append(" where product_id = :productId ");

    try {
      Map<String,Long> map = Map.of("productId",productId);
      Product product = template.queryForObject(sql.toString(), map, BeanPropertyRowMapper.newInstance(Product.class));
      return Optional.of(product);

    }catch (EmptyResultDataAccessException e){
      //조회결과가 없는경우
      return Optional.empty();
    }
  }

  //단건삭제
  @Override
  public int deleteById(Long productId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from product ");
    sql.append(" where product_id = :productId ");

    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("productId",productId);

    int deletedRowCnt = template.update(sql.toString(), param);

    return deletedRowCnt;
  }

  //여러건 삭제
  @Override
  public int deleteByIds(List<Long> productIds) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from product ");
    sql.append(" where product_id in (:productIds) ");

    Map<String,List<Long>> map = Map.of("productIds",productIds);
    int deletedRowCnt = template.update(sql.toString(), map);
    return deletedRowCnt;
  }

  //수정
  @Override
  public int updateById(Long productId, Product product) {
    StringBuffer sql = new StringBuffer();
    sql.append("update product ");
    sql.append("   set title = :title, ");
    sql.append("       text_log = :textLog, ");
    sql.append("       user_name = :userName, ");
    sql.append("       udate = default ");
    sql.append(" where product_id = :productId ");

    //sql 파라미터 변수에 값 매핑
    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("title", product.getTitle())
            .addValue("textLog", product.getTextLog())
            .addValue("userName", product.getUserName())
            .addValue("productId", productId);
    
    //update수행 후 변경된 행수 반환
    int updateRowCnt = template.update(sql.toString(), param);

    return updateRowCnt;
  }

  //목록
  @Override
  public List<Product> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("  select product_id, title, text_log, user_name, cdate, udate ");
    sql.append("    from product ");
    sql.append("order by product_id desc ");

    List<Product> list = template.query(sql.toString(), BeanPropertyRowMapper.newInstance(Product.class));

    return list;
  }
}

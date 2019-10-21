package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author 楠 on 2019/10/17
 */
@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key
     * @param page
     * @param rows
     * @param sortBy 排序字段
     * @param desc
     * @return
     */
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {

        //初始化Example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        //根据name模糊查询,或者根据首字母查询
        if(StringUtils.isNotBlank(key)){
            //name属性或letter属性
            criteria.andLike("name","%"+key+"%").orEqualTo("letter",key);
        }

        //添加分页条件
        PageHelper.startPage(page,rows);

        //添加排序条件
        if(StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy+" "+(desc?"desc":"asc"));
        }

        List<Brand> brands = this.brandMapper.selectByExample(example);

        //包装成pageInfo对象
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);

        //包装成分页结果集
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     */
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {

        //先新增Brand
        Boolean flag=this.brandMapper.insertSelective(brand)==1;

        //再新增中间表
        cids.forEach(cid  -> {
                this.brandMapper.insertCategoryAndBrand(cid,brand.getId());
        });

       /* for (Long cid :
                cids) {
            this.brandMapper.insertCategoryAndBrand(cid,brand.getId());
        }*/
    }

    /**
     * 根据分类的id查询品牌
     * @param cid
     * @return
     */
    public List<Brand> queryBrandsByCid(Long cid) {
        List<Brand> brands = this.brandMapper.selectBrandsByCid(cid);
        return brands;
    }
}
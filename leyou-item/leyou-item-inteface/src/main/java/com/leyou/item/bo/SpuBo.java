package com.leyou.item.bo;

import com.leyou.item.pojo.Spu;

/**
 * @author 楠 on 2019/10/19
 */
public class SpuBo extends Spu {

    private String cname;

    private String bname;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }
}
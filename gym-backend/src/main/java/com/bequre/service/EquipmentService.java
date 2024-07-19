package com.bequre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bequre.pojo.Equipment;
import com.bequre.pojo.PageDTO;
import com.bequre.pojo.query.EquipmentQuery;

public interface EquipmentService extends IService<Equipment> {

    /**
     *  器材分页条件查询
     * @param equipmentQuery
     * @return
     */
    PageDTO<Equipment> queryEquipmentPage(EquipmentQuery equipmentQuery);

    /**
     *  根据器材名称查询器材
     * @param name
     * @return
     */
    Equipment getByName(String name);


}
